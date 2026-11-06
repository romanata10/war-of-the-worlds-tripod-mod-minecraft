package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * The Construction Machine — the biggest walker the Martians field, and the only one that does not
 * exist to kill people.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Crane claw</b> — the long arm reaches out and tears up the ground in front of it,
 *     stacking what it takes straight into the two open holds on its flanks.</li>
 *     <li><b>Field works</b> — once it has enough rubble it walks up to a spot and builds a walled,
 *     roofed Martian bunker out of exactly the blocks it swallowed.</li>
 *     <li><b>One machine gun</b> — a short burst of small rounds is all the defence it carries.</li>
 * </ul>
 *
 * <p>Everything in the holds is dropped when the machine falls, so a player who brings one down
 * gets the whole quarry back.</p>
 */
public class ConstructionTripodEntity extends AbstractTripodEntity {

    /** How many stacks the two holds together can take. */
    private static final int MAX_CARGO = 54;
    /** Ticks between two grabs with the claw. */
    private static final int HARVEST_PERIOD = 60;
    /** Ticks between two attempts at raising a bunker. */
    private static final int BUILD_PERIOD = 300;
    /** Blocks laid per building pass, so the work spreads over several visits. */
    private static final int BLOCKS_PER_PASS = 22;
    /** Rubble needed before it starts building. */
    private static final int BUILD_THRESHOLD = 24;

    private final List<ItemStack> cargo = new ArrayList<>();

    public ConstructionTripodEntity(EntityType<? extends ConstructionTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(460.0D, 0.20D, 16.0D, 20.0D, 12.0D, 1.0D, 46.0D);
    }

    @Override
    protected void addExtraGoals() {
        // one gun, and not a big one: three small rounds, then a long reload
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.LASER, 5.0F, 30.0D, 14, 70, 3, 4, true));
    }

    /* ------------------------------------------------------------------ */
    /*  The claw and the works                                             */
    /* ------------------------------------------------------------------ */

    @Override
    protected void serverTick() {
        super.serverTick();
        if (this.onIntervalZero(HARVEST_PERIOD)) {
            this.harvestWithClaw();
        }
        if (this.onIntervalZero(BUILD_PERIOD)) {
            this.buildOutpost();
        }
    }

    /** Where the claw hangs: out in front of the machine, a little above the ground. */
    public Vec3 getClawPosition() {
        Vec3 forward = this.getViewVector(1.0F);
        return new Vec3(this.getX() + forward.x * 9.0D, this.getY() + 2.5D, this.getZ() + forward.z * 9.0D);
    }

    /** The claw tears up whatever it can reach and loads it into the holds. */
    private void harvestWithClaw() {
        if (this.cargo.size() >= MAX_CARGO) {
            return;
        }
        BlockPos centre = BlockPos.containing(this.getClawPosition());
        for (int attempt = 0; attempt < 14 && this.cargo.size() < MAX_CARGO; attempt++) {
            BlockPos pos = centre.offset(this.random.nextInt(5) - 2, this.random.nextInt(5) - 2,
                    this.random.nextInt(5) - 2);
            if (!this.level().isLoaded(pos)) {
                continue;
            }
            BlockState state = this.level().getBlockState(pos);
            if (state.isAir() || state.hasBlockEntity()) {
                continue;
            }
            if (state.getDestroySpeed(this.level(), pos) < 0.0F) {
                continue;               // bedrock and barriers stay where they are
            }
            ItemStack block = new ItemStack(state.getBlock().asItem());
            if (block.isEmpty()) {
                continue;
            }
            this.level().destroyBlock(pos, false);
            this.addToCargo(block);
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.CRIT, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                        4, 0.3D, 0.3D, 0.3D, 0.02D);
            }
        }
    }

    private void addToCargo(ItemStack stack) {
        for (ItemStack held : this.cargo) {
            if (ItemStack.isSameItemSameTags(held, stack) && held.getCount() < held.getMaxStackSize()) {
                held.grow(1);
                return;
            }
        }
        if (this.cargo.size() < MAX_CARGO) {
            this.cargo.add(stack.copyWithCount(1));
        }
    }

    /** Raises a small, roofed Martian bunker a few paces ahead, out of the rubble in the holds. */
    private void buildOutpost() {
        if (this.cargoCount() < BUILD_THRESHOLD) {
            return;
        }
        Direction facing = this.getDirection();
        BlockPos.MutableBlockPos spot = this.blockPosition().mutable().move(facing, 7);
        int groundY = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, spot.getX(), spot.getZ());
        BlockPos base = new BlockPos(spot.getX(), groundY, spot.getZ());

        int placed = 0;
        // floor
        for (int dx = -2; dx <= 2 && placed < BLOCKS_PER_PASS; dx++) {
            for (int dz = -2; dz <= 2 && placed < BLOCKS_PER_PASS; dz++) {
                if (this.placeCollected(base.offset(dx, 0, dz))) {
                    placed++;
                }
            }
        }
        // walls, three high, with a two by two door on the near side
        for (int dy = 1; dy <= 3 && placed < BLOCKS_PER_PASS; dy++) {
            for (int dx = -2; dx <= 2 && placed < BLOCKS_PER_PASS; dx++) {
                for (int dz = -2; dz <= 2 && placed < BLOCKS_PER_PASS; dz++) {
                    if (Math.abs(dx) != 2 && Math.abs(dz) != 2) {
                        continue;
                    }
                    if (dy <= 2 && dz == 2 && Math.abs(dx) < 2) {
                        continue;       // doorway, so the garrison can walk out
                    }
                    if (this.placeCollected(base.offset(dx, dy, dz))) {
                        placed++;
                    }
                }
            }
        }
        // roof
        for (int dx = -2; dx <= 2 && placed < BLOCKS_PER_PASS; dx++) {
            for (int dz = -2; dz <= 2 && placed < BLOCKS_PER_PASS; dz++) {
                if (this.placeCollected(base.offset(dx, 4, dz))) {
                    placed++;
                }
            }
        }
        if (placed > 0 && this.level() instanceof ServerLevel server) {
            server.playSound(null, base.getX(), base.getY(), base.getZ(), SoundEvents.STONE_PLACE,
                    SoundSource.HOSTILE, 2.0F, 0.7F);
            server.sendParticles(ParticleTypes.CLOUD, base.getX() + 0.5D, base.getY() + 1.0D, base.getZ() + 0.5D,
                    20, 2.5D, 1.0D, 2.5D, 0.02D);
        }
    }

    /** Total number of blocks sitting in the holds. */
    public int cargoCount() {
        int total = 0;
        for (ItemStack stack : this.cargo) {
            total += stack.getCount();
        }
        return total;
    }

    /** Places one block out of the holds. Returns true when a block really went down. */
    private boolean placeCollected(BlockPos pos) {
        if (this.cargo.isEmpty()) {
            return false;
        }
        BlockState existing = this.level().getBlockState(pos);
        if (!existing.isAir() && !existing.canBeReplaced()) {
            return false;
        }
        ItemStack stack = this.cargo.get(0);
        Block block = Block.byItem(stack.getItem());
        if (block == Blocks.AIR) {
            this.cargo.remove(0);       // something that is not a block at all, drop it from the hold
            return false;
        }
        this.level().setBlockAndUpdate(pos, block.defaultBlockState());
        stack.shrink(1);
        if (stack.isEmpty()) {
            this.cargo.remove(0);
        }
        return true;
    }

    /* ------------------------------------------------------------------ */

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        Vec3 claw = this.getClawPosition();
        this.level().addParticle(ParticleTypes.CLOUD, claw.x, claw.y, claw.z, 0.0D, -0.02D, 0.0D);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean causedByPlayer) {
        super.dropCustomDeathLoot(source, looting, causedByPlayer);
        for (ItemStack stack : this.cargo) {
            this.spawnAtLocation(stack);
        }
        this.cargo.clear();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        ListTag list = new ListTag();
        for (ItemStack stack : this.cargo) {
            list.add(stack.save(new CompoundTag()));
        }
        nbt.put("Cargo", list);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.cargo.clear();
        ListTag list = nbt.getList("Cargo", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            this.cargo.add(ItemStack.of(list.getCompound(i)));
        }
    }
}
