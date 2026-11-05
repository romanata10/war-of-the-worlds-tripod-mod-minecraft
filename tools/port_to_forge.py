#!/usr/bin/env python3
"""Carries the mod sources from Fabric (Yarn mappings) to Forge 1.20.1 (Mojang mappings).

The mod was written against Yarn names, so the port is mostly a very mechanical rename:

    net.minecraft.world.World        ->  net.minecraft.world.level.Level
    player.getBlockPos()             ->  player.blockPosition()
    AbstractBlock.Settings.create()  ->  BlockBehaviour.Properties.of()

Everything that is *not* a rename (registration, events, the creative tab, spawning, the client
bootstrapping) lives in hand written Forge classes and is not touched here.

The script is idempotent: running it twice changes nothing.

Usage:  python3 tools/port_to_forge.py [--check]
"""

import pathlib
import re
import sys

ROOT = pathlib.Path(__file__).resolve().parent.parent
JAVA = ROOT / "src/main/java"

# --------------------------------------------------------------------------- #
#  1. Imports: every Yarn class we use and where it lives in Mojang mappings
# --------------------------------------------------------------------------- #

IMPORTS = {
    "net.minecraft.block.AbstractBlock": "net.minecraft.world.level.block.state.BlockBehaviour",
    "net.minecraft.block.Block": "net.minecraft.world.level.block.Block",
    "net.minecraft.block.BlockEntityProvider": "net.minecraft.world.level.block.EntityBlock",
    "net.minecraft.block.BlockState": "net.minecraft.world.level.block.state.BlockState",
    "net.minecraft.block.Blocks": "net.minecraft.world.level.block.Blocks",
    "net.minecraft.block.ShapeContext": "net.minecraft.world.phys.shapes.CollisionContext",
    "net.minecraft.block.entity.BlockEntity": "net.minecraft.world.level.block.entity.BlockEntity",
    "net.minecraft.block.entity.BlockEntityTicker": "net.minecraft.world.level.block.entity.BlockEntityTicker",
    "net.minecraft.block.entity.BlockEntityType": "net.minecraft.world.level.block.entity.BlockEntityType",
    "net.minecraft.client.item.TooltipContext": "net.minecraft.world.item.TooltipFlag",
    "net.minecraft.client.model.ModelPart": "net.minecraft.client.model.geom.ModelPart",
    "net.minecraft.client.render.OverlayTexture": "net.minecraft.client.renderer.texture.OverlayTexture",
    "net.minecraft.client.render.RenderLayer": "net.minecraft.client.renderer.RenderType",
    "net.minecraft.client.render.VertexConsumer": "com.mojang.blaze3d.vertex.VertexConsumer",
    "net.minecraft.client.render.VertexConsumerProvider": "net.minecraft.client.renderer.MultiBufferSource",
    "net.minecraft.client.render.block.BlockRenderManager": "net.minecraft.client.renderer.block.BlockRenderDispatcher",
    "net.minecraft.client.render.entity.EntityRenderer": "net.minecraft.client.renderer.entity.EntityRenderer",
    "net.minecraft.client.render.entity.EntityRendererFactory": "net.minecraft.client.renderer.entity.EntityRendererProvider",
    "net.minecraft.client.render.entity.MobEntityRenderer": "net.minecraft.client.renderer.entity.MobRenderer",
    "net.minecraft.client.render.entity.model.EntityModel": "net.minecraft.client.model.EntityModel",
    "net.minecraft.client.render.entity.model.EntityModelLayer": "net.minecraft.client.model.geom.ModelLayerLocation",
    "net.minecraft.client.util.math.MatrixStack": "com.mojang.blaze3d.vertex.PoseStack",
    "net.minecraft.entity.AreaEffectCloudEntity": "net.minecraft.world.entity.AreaEffectCloud",
    "net.minecraft.entity.Entity": "net.minecraft.world.entity.Entity",
    "net.minecraft.entity.EntityType": "net.minecraft.world.entity.EntityType",
    "net.minecraft.entity.LivingEntity": "net.minecraft.world.entity.LivingEntity",
    "net.minecraft.entity.MovementType": "net.minecraft.world.entity.MoverType",
    "net.minecraft.entity.SpawnGroup": "net.minecraft.world.entity.MobCategory",
    "net.minecraft.entity.SpawnReason": "net.minecraft.world.entity.MobSpawnType",
    "net.minecraft.entity.SpawnRestriction": "net.minecraft.world.entity.SpawnPlacements",
    "net.minecraft.entity.ai.goal.ActiveTargetGoal": "net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal",
    "net.minecraft.entity.ai.goal.Goal": "net.minecraft.world.entity.ai.goal.Goal",
    "net.minecraft.entity.ai.goal.LookAroundGoal": "net.minecraft.world.entity.ai.goal.RandomLookAroundGoal",
    "net.minecraft.entity.ai.goal.LookAtEntityGoal": "net.minecraft.world.entity.ai.goal.LookAtPlayerGoal",
    "net.minecraft.entity.ai.goal.MeleeAttackGoal": "net.minecraft.world.entity.ai.goal.MeleeAttackGoal",
    "net.minecraft.entity.ai.goal.RevengeGoal": "net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal",
    "net.minecraft.entity.ai.goal.SwimGoal": "net.minecraft.world.entity.ai.goal.FloatGoal",
    "net.minecraft.entity.ai.goal.WanderAroundFarGoal": "net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal",
    "net.minecraft.entity.attribute.DefaultAttributeContainer": "net.minecraft.world.entity.ai.attributes.AttributeSupplier",
    "net.minecraft.entity.attribute.EntityAttributes": "net.minecraft.world.entity.ai.attributes.Attributes",
    "net.minecraft.entity.damage.DamageSource": "net.minecraft.world.damagesource.DamageSource",
    "net.minecraft.entity.data.DataTracker": "net.minecraft.network.syncher.SynchedEntityData",
    "net.minecraft.entity.data.TrackedData": "net.minecraft.network.syncher.EntityDataAccessor",
    "net.minecraft.entity.data.TrackedDataHandlerRegistry": "net.minecraft.network.syncher.EntityDataSerializers",
    "net.minecraft.entity.effect.StatusEffect": "net.minecraft.world.effect.MobEffect",
    "net.minecraft.entity.effect.StatusEffectCategory": "net.minecraft.world.effect.MobEffectCategory",
    "net.minecraft.entity.effect.StatusEffectInstance": "net.minecraft.world.effect.MobEffectInstance",
    "net.minecraft.entity.effect.StatusEffects": "net.minecraft.world.effect.MobEffects",
    "net.minecraft.entity.mob.HostileEntity": "net.minecraft.world.entity.monster.Monster",
    "net.minecraft.entity.mob.MobEntity": "net.minecraft.world.entity.Mob",
    "net.minecraft.entity.mob.PathAwareEntity": "net.minecraft.world.entity.PathfinderMob",
    "net.minecraft.entity.passive.IronGolemEntity": "net.minecraft.world.entity.animal.IronGolem",
    "net.minecraft.entity.passive.VillagerEntity": "net.minecraft.world.entity.npc.Villager",
    "net.minecraft.entity.player.PlayerEntity": "net.minecraft.world.entity.player.Player",
    "net.minecraft.item.BlockItem": "net.minecraft.world.item.BlockItem",
    "net.minecraft.item.Item": "net.minecraft.world.item.Item",
    "net.minecraft.item.ItemGroup": "net.minecraft.world.item.CreativeModeTab",
    "net.minecraft.item.ItemStack": "net.minecraft.world.item.ItemStack",
    "net.minecraft.item.SpawnEggItem": "net.minecraft.world.item.SpawnEggItem",
    "net.minecraft.nbt.NbtCompound": "net.minecraft.nbt.CompoundTag",
    "net.minecraft.particle.ParticleEffect": "net.minecraft.core.particles.ParticleOptions",
    "net.minecraft.particle.ParticleTypes": "net.minecraft.core.particles.ParticleTypes",
    "net.minecraft.registry.Registries": "net.minecraft.core.registries.BuiltInRegistries",
    "net.minecraft.registry.Registry": "net.minecraft.core.Registry",
    "net.minecraft.registry.tag.BlockTags": "net.minecraft.tags.BlockTags",
    "net.minecraft.registry.tag.DamageTypeTags": "net.minecraft.tags.DamageTypeTags",
    "net.minecraft.server.MinecraftServer": "net.minecraft.server.MinecraftServer",
    "net.minecraft.server.network.ServerPlayerEntity": "net.minecraft.server.level.ServerPlayer",
    "net.minecraft.server.world.ServerWorld": "net.minecraft.server.level.ServerLevel",
    "net.minecraft.sound.BlockSoundGroup": "net.minecraft.world.level.block.SoundType",
    "net.minecraft.sound.SoundCategory": "net.minecraft.sounds.SoundSource",
    "net.minecraft.sound.SoundEvent": "net.minecraft.sounds.SoundEvent",
    "net.minecraft.sound.SoundEvents": "net.minecraft.sounds.SoundEvents",
    "net.minecraft.text.Text": "net.minecraft.network.chat.Component",
    "net.minecraft.util.ActionResult": "net.minecraft.world.InteractionResult",
    "net.minecraft.util.Formatting": "net.minecraft.ChatFormatting",
    "net.minecraft.util.Hand": "net.minecraft.world.InteractionHand",
    "net.minecraft.util.Identifier": "net.minecraft.resources.ResourceLocation",
    "net.minecraft.util.TypedActionResult": "net.minecraft.world.InteractionResultHolder",
    "net.minecraft.util.hit.BlockHitResult": "net.minecraft.world.phys.BlockHitResult",
    "net.minecraft.util.math.BlockPos": "net.minecraft.core.BlockPos",
    "net.minecraft.util.math.Box": "net.minecraft.world.phys.AABB",
    "net.minecraft.util.math.Direction": "net.minecraft.core.Direction",
    "net.minecraft.util.math.MathHelper": "net.minecraft.util.Mth",
    "net.minecraft.util.math.RotationAxis": "com.mojang.math.Axis",
    "net.minecraft.util.math.Vec3d": "net.minecraft.world.phys.Vec3",
    "net.minecraft.util.math.random.Random": "net.minecraft.util.RandomSource",
    "net.minecraft.util.shape.VoxelShape": "net.minecraft.world.phys.shapes.VoxelShape",
    "net.minecraft.world.BlockView": "net.minecraft.world.level.BlockGetter",
    "net.minecraft.world.Heightmap": "net.minecraft.world.level.levelgen.Heightmap",
    "net.minecraft.world.PersistentState": "net.minecraft.world.level.saveddata.SavedData",
    "net.minecraft.world.World": "net.minecraft.world.level.Level",
    "net.minecraft.world.WorldAccess": "net.minecraft.world.level.LevelAccessor",
    "net.minecraft.world.WorldView": "net.minecraft.world.level.LevelReader",
    # the worldgen tag of the fluids
    "net.minecraft.registry.tag.FluidTags": "net.minecraft.tags.FluidTags",
}

# The generated model files already speak Mojang mappings, they are rewritten by the generator.
GENERATED = {
    "client/model/TripodModels.java",
    "client/model/MartianModels.java",
    "client/model/HumanModels.java",
}

# --------------------------------------------------------------------------- #
#  2. Class names in the code
# --------------------------------------------------------------------------- #

CLASSES = {
    # worlds and entities
    "World": "Level",
    "ServerWorld": "ServerLevel",
    "WorldAccess": "LevelAccessor",
    "WorldView": "LevelReader",
    "BlockView": "BlockGetter",
    "PlayerEntity": "Player",
    "ServerPlayerEntity": "ServerPlayer",
    "MobEntity": "Mob",
    "HostileEntity": "Monster",
    "PathAwareEntity": "PathfinderMob",
    "VillagerEntity": "Villager",
    "IronGolemEntity": "IronGolem",
    "PersistentState": "SavedData",
    "Identifier": "ResourceLocation",
    "Vec3d": "Vec3",
    "Box": "AABB",
    "MathHelper": "Mth",
    "RotationAxis": "Axis",
    "Text": "Component",
    "Formatting": "ChatFormatting",
    "Hand": "InteractionHand",
    "ActionResult": "InteractionResult",
    "TypedActionResult": "InteractionResultHolder",
    "NbtCompound": "CompoundTag",
    "ParticleEffect": "ParticleOptions",
    "SoundCategory": "SoundSource",
    "BlockSoundGroup": "SoundType",
    "SpawnGroup": "MobCategory",
    "SpawnReason": "MobSpawnType",
    "SpawnRestriction": "SpawnPlacements",
    "StatusEffectInstance": "MobEffectInstance",
    "StatusEffectCategory": "MobEffectCategory",
    "StatusEffect": "MobEffect",
    "StatusEffects": "MobEffects",
    "TrackedDataHandlerRegistry": "EntityDataSerializers",
    "TrackedData": "EntityDataAccessor",
    "DataTracker": "SynchedEntityData",
    "DefaultAttributeContainer": "AttributeSupplier",
    "EntityAttributes": "Attributes",
    "MovementType": "MoverType",
    "ShapeContext": "CollisionContext",
    "Registries": "BuiltInRegistries",
    "ItemGroup": "CreativeModeTab",
    "MatrixStack": "PoseStack",
    "VertexConsumerProvider": "MultiBufferSource",
    "RenderLayer": "RenderType",
    "EntityModelLayer": "ModelLayerLocation",
    "EntityRendererFactory": "EntityRendererProvider",
    "MobEntityRenderer": "MobRenderer",
    "TexturedModelData": "LayerDefinition",
    "ModelData": "MeshDefinition",
    "ModelPartData": "PartDefinition",
    "ModelPartBuilder": "CubeListBuilder",
    "ModelTransform": "PartPose",
    # goals
    "SwimGoal": "FloatGoal",
    "WanderAroundFarGoal": "WaterAvoidingRandomStrollGoal",
    "LookAroundGoal": "RandomLookAroundGoal",
    "LookAtEntityGoal": "LookAtPlayerGoal",
    "ActiveTargetGoal": "NearestAttackableTargetGoal",
    "RevengeGoal": "HurtByTargetGoal",
    "EscapeDangerGoal": "PanicGoal",
}

# --------------------------------------------------------------------------- #
#  3. Methods and fields
# --------------------------------------------------------------------------- #

MEMBERS = [
    (r"\.getWorld\(\)", ".level()"),
    (r"\.getWorld\(", ".level("),
    (r"\.isClient\b", ".isClientSide"),
    (r"\.getPos\(\)", ".position()"),
    (r"\.getBlockPos\(\)", ".blockPosition()"),
    (r"\.getEyePos\(\)", ".getEyePosition()"),
    (r"\.getRotationVec\(", ".getViewVector("),
    (r"\.getVelocity\(\)", ".getDeltaMovement()"),
    (r"\.setVelocity\(", ".setDeltaMovement("),
    (r"\.addVelocity\(", ".push("),
    (r"\.velocityModified", ".hurtMarked"),
    (r"\.getHeight\(\)", ".getBbHeight()"),
    (r"\.getWidth\(\)", ".getBbWidth()"),
    (r"\.squaredDistanceTo\(", ".distanceToSqr("),
    (r"\.canSee\(", ".hasLineOfSight("),
    (r"\.getDamageSources\(\)", ".damageSources()"),
    (r"\.spawnParticles\(", ".sendParticles("),
    (r"\.getEntitiesByClass\(", ".getEntitiesOfClass("),
    (r"\.breakBlock\(", ".destroyBlock("),
    (r"\.setBlockState\(", ".setBlock("),
    (r"\.getDefaultState\(\)", ".defaultBlockState()"),
    (r"\.isOpaque\(\)", ".canOcclude()"),
    (r"\.getHardness\(", ".getDestroySpeed("),
    (r"\.createCuboidShape\(", ".box("),
    (r"\.getOutlineShape\(", ".getShape("),
    (r"\.canPlaceAt\(", ".canSurvive("),
    (r"\.getStateForNeighborUpdate\(", ".updateShape("),
    (r"\.onEntityCollision\(", ".entityInside("),
    (r"\.randomDisplayTick\(", ".animateTick("),
    (r"\.onPlaced\(", ".setPlacedBy("),
    (r"\.onUse\(", ".use("),
    (r"\.getStackInHand\(", ".getItemInHand("),
    (r"\.setStackInHand\(", ".setItemInHand("),
    (r"\.getMainHandStack\(\)", ".getMainHandItem()"),
    (r"\.setCurrentHand\(", ".startUsingItem("),
    (r"\.refreshPositionAndAngles\(", ".moveTo("),
    (r"\.getPassengerList\(\)", ".getPassengers()"),
    (r"\.getTopY\(", ".getHeight("),
    (r"\.initialize\(", ".finalizeSpawn("),
    (r"\.setPersistent\(\)", ".setPersistenceRequired()"),
    (r"\.getLocalDifficulty\(", ".getCurrentDifficultyAt("),
    (r"\.dropStack\(", ".spawnAtLocation("),
    (r"\.computeFallDamage\(", ".calculateFallDamage("),
    (r"\.getMinAmbientSoundDelay\(\)", ".getAmbientSoundInterval()"),
    (r"\.isFireImmune\(\)", ".fireImmune()"),
    (r"\.canBeLeashedBy\(", ".canBeLeashed("),
    (r"\.getXpToDrop\(\)", ".getExperienceReward()"),
    (r"\.initDataTracker\(\)", ".defineSynchedData()"),
    (r"\.dropLoot\(", ".dropCustomDeathLoot("),
    (r"\.tryAttack\(", ".doHurtTarget("),
    (r"\.onDeath\(", ".die("),
    (r"\.isOnGround\(\)", ".onGround()"),
    (r"\.canBreatheInWater\(\)", ".canBreatheUnderwater()"),
    (r"\.isPushedByFluids\(\)", ".isPushedByFluid()"),
    (r"\.expand\(", ".inflate("),
    (r"\.requestTeleport\(", ".teleportTo("),
    (r"\.getPlayerByUuid\(", ".getPlayerByUUID("),
    (r"\.hasStatusEffect\(", ".hasEffect("),
    (r"\.addStatusEffect\(", ".addEffect("),
    (r"\.removeStatusEffect\(", ".removeEffect("),
    (r"\.setOnFireFor\(", ".setSecondsOnFire("),
    (r"\.getUuid\(\)", ".getUUID()"),
    (r"\.getPersistentStateManager\(\)", ".getDataStorage()"),
    (r"\.markDirty\(\)", ".setDirty()"),
    (r"\.getOrCreate\(", ".computeIfAbsent("),
    (r"\.isIn\(", ".is("),
    (r"Block\.NOTIFY_ALL", "Block.UPDATE_ALL"),
    (r"\.registerData\(", ".defineId("),
    (r"this\.dataTracker\.startTracking\(", "this.entityData.define("),
    (r"this\.dataTracker\.set\(", "this.entityData.set("),
    (r"this\.dataTracker\.get\(", "this.entityData.get("),
    (r"\.damage\(this\.damageSources\(\)\.mobAttack\(this\)", ".hurt(this.damageSources().mobAttack(this)"),
    (r"\.damage\(source, amount\)", ".hurt(source, amount)"),
    (r"\.damage\(source,", ".hurt(source,"),
    (r"\.damage\(this\.damageSources\(\)", ".hurt(this.damageSources()"),
    (r"\.damage\(world\.damageSources\(\)", ".hurt(world.damageSources()"),
    (r"\.spawnEntity\(", ".addFreshEntity("),
    (r"\.getClosestPlayer\(", ".getNearestPlayer("),
    (r"\.getYaw\(\)", ".getYRot()"),
    (r"\.setYaw\(", ".setYRot("),
    (r"\.getPitch\(\)", ".getXRot()"),
    (r"\.setPitch\(", ".setXRot("),
    (r"\bbodyYaw\b", "yBodyRot"),
    (r"\bheadYaw\b", "yHeadRot"),
    (r"\.createExplosion\(", ".explode("),
    (r"\.getItemCooldownManager\(\)", ".getCooldowns()"),
    (r"\.sendMessage\(", ".displayClientMessage("),
    (r"\.removeAllPassengers\(", ".ejectPassengers("),
    (r"\.isDead\(\)", ".isDeadOrDying()"),
    (r"\.getBottomY\(", ".getMinBuildHeight("),
    (r"\.setRadiusGrowth\(", ".setRadiusPerTick("),
    (r"\.setRadiusOnUse\(", ".setDurationOnUse("),
    (r"\.setParticleType\(", ".setParticle("),
    (r"\.getBlockRenderManager\(\)", ".getBlockRenderer()"),
    (r"\.getCutout\(\)", ".cutout()"),
    (r"\.getTranslucent\(\)", ".translucent()"),
    (r"\.canApplyUpdateEffect\(", ".isDurationEffectTick("),
    (r"\.isSolidBlock\(", ".isSolidRender("),
    (r"\.getSquaredDistance\(", ".distanceToSqr("),
    (r"\.level\(Level\.", ".getLevel(Level."),
    # vanilla sounds lost their ENTITY_/BLOCK_/ITEM_ prefixes in Mojang mappings
    (r"SoundEvents\.ENTITY_", "SoundEvents."),
    (r"SoundEvents\.BLOCK_", "SoundEvents."),
    (r"SoundEvents\.ITEM_", "SoundEvents."),
    # item stacks and hand handling
    (r"\.getDefaultStack\(\)", ".getDefaultInstance()"),
    (r"new Item\.Settings\(\)", "new Item.Properties()"),
    (r"\.maxCount\(", ".stacksTo("),
    (r"\.maxDamage\(", ".durability("),
    # block properties
    (r"AbstractBlock\.Settings\.create\(\)", "BlockBehaviour.Properties.of()"),
    (r"AbstractBlock\.Settings", "BlockBehaviour.Properties"),
    (r"\.sounds\(SoundType\.", ".sound(SoundType."),
    (r"\.luminance\(", ".lightLevel("),
    (r"\.requiresTool\(\)", ".requiresCorrectToolForDrops()"),
    (r"\.nonOpaque\(\)", ".noOcclusion()"),
    (r"\.noCollision\(\)", ".noCollission()"),
    (r"\.breakInstantly\(\)", ".instabreak()"),
    (r"\.ticksRandomly\(\)", ".randomTicks()"),
    # entity type builders
    (r"EntityType\.Builder\.create\(", "EntityType.Builder.of("),
    (r"\.setDimensions\(", ".sized("),
    (r"\.maxTrackingRange\(", ".clientTrackingRange("),
    (r"\.trackingTickInterval\(", ".updateInterval("),
    (r"EntityType\.Builder\.<(\w+)>create\(", r"EntityType.Builder.<\1>of("),
    # goals
    (r"Goal\.Control\.", "Goal.Flag."),
    (r"\.setControls\(", ".setFlags("),
    # client rendering
    (r"\.push\(\)", ".pushPose()"),
    (r"\.pop\(\)", ".popPose()"),
    (r"\.peek\(\)\.getPositionMatrix\(\)", ".last().pose()"),
    (r"\.getPositionMatrix\(\)", ".last().pose()"),
    (r"\.multiply\(Axis\.", ".mulPose(Axis."),
    (r"\.multiply\(RotationAxis\.", ".mulPose(Axis."),
    (r"Axis\.POSITIVE_X\.rotationDegrees\(", "Axis.XP.rotationDegrees("),
    (r"Axis\.POSITIVE_Y\.rotationDegrees\(", "Axis.YP.rotationDegrees("),
    (r"Axis\.POSITIVE_Z\.rotationDegrees\(", "Axis.ZP.rotationDegrees("),
    (r"Axis\.NEGATIVE_X\.rotationDegrees\(", "Axis.XN.rotationDegrees("),
    (r"Axis\.NEGATIVE_Y\.rotationDegrees\(", "Axis.YN.rotationDegrees("),
    (r"Axis\.NEGATIVE_Z\.rotationDegrees\(", "Axis.ZN.rotationDegrees("),
    (r"\.light\(LightmapTextureManager\.MAX_LIGHT_COORDINATE, OverlayTexture\.DEFAULT_UV\)",
     ".uv2(LightTexture.FULL_BRIGHT)",
     ),
    (r"\.light\((\w+), OverlayTexture\.DEFAULT_UV\)", r".uv2(\1)"),
    (r"\.overlay\(OverlayTexture\.DEFAULT_UV\)", ".overlayCoords(OverlayTexture.NO_OVERLAY)"),
    (r"\.getPart\(", ".bakeLayer("),
    # model geometry
    (r"LayerDefinition\.of\(", "LayerDefinition.create("),
    (r"\.addChild\(", ".addOrReplaceChild("),
    (r"\.cuboid\(", ".addBox("),
    (r"\bpivotY\b", "y"),
    (r"\bpivotX\b", "x"),
    (r"\bpivotZ\b", "z"),
    (r"\.pitch\b", ".xRot"),
    (r"\.roll\b", ".zRot"),
    (r"\.yaw\b", ".yRot"),
    # render types and vertex format
    (r"RenderType\.getEyes\(", "RenderType.eyes("),
    (r"RenderType\.getEntityTranslucentEmissive\(", "RenderType.entityTranslucentEmissive("),
    (r"RenderType\.getEntitySolid\(", "RenderType.entitySolid("),
    (r"\.light\((\w+)\)", r".uv2(\1)"),
    (r"\.next\(\)", ".endVertex()"),
    # the entity render dispatcher kept its old field name in yarn, not in Mojang
    (r"this\.dispatcher\b", "this.entityRenderDispatcher"),
    (r"context\.getBlockRenderer\(\)", "context.getBlockRenderDispatcher()"),
    (r"\.render\(matrices, vertices,", ".renderToBuffer(matrices, vertices,"),
    (r"public void setAngles\(", "public void setupAnim("),
    (r"Animatable", "Animatable"),
]

# --------------------------------------------------------------------------- #
#  4. Straight text level fixes that do not fit a single rule
# --------------------------------------------------------------------------- #

LITERALS = [
    # the yarn enum lives inside Level now
    ("Level.ExplosionSourceType.MOB", "Level.ExplosionInteraction.MOB"),
    ("Level.ExplosionSourceType.NONE", "Level.ExplosionInteraction.NONE"),
    ("Level.ExplosionSourceType.BLOCK", "Level.ExplosionInteraction.BLOCK"),
    ("Level.ExplosionSourceType.TNT", "Level.ExplosionInteraction.TNT"),
    ("Level.ExplosionSourceType.TRIGGER", "Level.ExplosionInteraction.TRIGGER"),
    # particle ids that changed spelling
    ("ParticleTypes.LARGE_SMOKE", "ParticleTypes.LARGE_SMOKE"),
    ("ParticleTypes.EXPLOSION_EMITTER", "ParticleTypes.EXPLOSION_EMITTER"),
]


# simple name -> Mojang fully qualified name, used for references written out in full in the code
FQN = {fqn.rsplit(".", 1)[-1]: fqn for fqn in set(IMPORTS.values()) | {
    "net.minecraft.world.entity.projectile.AbstractHurtingProjectile",
    "net.minecraft.world.entity.projectile.Projectile",
    "net.minecraft.world.entity.animal.IronGolem",
    "net.minecraft.world.entity.monster.Monster",
    "net.minecraft.world.entity.ai.goal.PanicGoal",
    "net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal",
    "net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal",
    "net.minecraft.world.entity.ai.goal.FloatGoal",
    "net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal",
    "net.minecraft.world.entity.ai.goal.RandomLookAroundGoal",
    "net.minecraft.world.entity.ai.goal.LookAtPlayerGoal",
    "net.minecraft.world.entity.ai.goal.MeleeAttackGoal",
}}


def port_source(text, path):
    """Translates one file. Returns the new text and the list of things it could not map."""
    if any(str(path).endswith(name) for name in GENERATED):
        return text, []

    # imports first: exact FQN replacement, so the package move is always right
    def swap_import(match):
        fqn = match.group(1)
        if fqn in IMPORTS:
            return "import " + IMPORTS[fqn] + ";"
        return match.group(0)

    text = re.sub(r"^import (net\.minecraft\.[\w.]+);$", swap_import, text, flags=re.M)

    # class names, longest first so that ServerWorld wins over World
    for old in sorted(CLASSES, key=len, reverse=True):
        new = CLASSES[old]
        text = re.sub(r"\b" + old + r"\b", new, text)

    # members
    for old, new in MEMBERS:
        text = re.sub(old, new, text)

    # fully qualified references left in the code body (net.minecraft.entity.ai.goal.PanicGoal)
    def swap_inline(match):
        simple = match.group(0).rsplit(".", 1)[-1]
        return FQN.get(simple, match.group(0))

    text = re.sub(r"net\.minecraft\.(?:[a-z][\w]*\.)*[A-Z][\w]*", swap_inline, text)

    for old, new in LITERALS:
        text = text.replace(old, new)

    # duplicates the class pass may have produced in imports (e.g. two spellings of one class)
    lines, seen = [], set()
    for line in text.split("\n"):
        if line.startswith("import ") and line.endswith(";") and not line.startswith("import static"):
            fqn = line[len("import "):-1]
            if fqn.startswith("dev.arena"):
                lines.append(line)
                continue
            if fqn in seen:
                continue
            seen.add(fqn)
        lines.append(line)
    text = "\n".join(lines)

    left = re.findall(r"\bnet\.minecraft\.[\w.]*[A-Z]\w*\b", text)
    return text, sorted(set(left))


def main():
    check = "--check" in sys.argv
    changed, unmapped = 0, {}
    for path in sorted(JAVA.rglob("*.java")):
        old = path.read_text()
        new, left = port_source(old, path.relative_to(JAVA))
        if left:
            unmapped[str(path.relative_to(JAVA))] = left
        if new != old:
            changed += 1
            if not check:
                path.write_text(new)
    print(f"{changed} file(s) {'would change' if check else 'ported'}")
    for name, leftovers in unmapped.items():
        print(" left:", name, leftovers)
    return 0


if __name__ == "__main__":
    sys.exit(main())
