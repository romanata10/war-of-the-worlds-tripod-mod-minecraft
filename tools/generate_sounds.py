#!/usr/bin/env python3
"""Procedural sound generator for the mod.

Creates the Martian tripod horn, the heat ray zap and the machine's footstep as OGG Vorbis files
(mono, 44.1 kHz) — the format Minecraft expects for `assets/<namespace>/sounds/`.

Usage:  python3 tools/generate_sounds.py     (needs numpy + soundfile)
"""

import os

import numpy as np
import soundfile as sf

ASSETS = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "assets", "wotw")
SOUNDS = os.path.join(ASSETS, "sounds")
RATE = 44100
rng = np.random.default_rng(20251030)


def time_axis(seconds):
    return np.arange(int(RATE * seconds)) / RATE


def soft_clip(x, drive=1.5):
    return np.tanh(x * drive) / np.tanh(drive)


def sweep_phase(freq_curve):
    """Instantaneous frequency -> phase, so sweeps do not drift into the subsonic range."""
    return 2.0 * np.pi * np.cumsum(freq_curve) / RATE


def one_pole_lowpass(x, cutoff):
    alpha = 1.0 - np.exp(-2.0 * np.pi * cutoff / RATE)
    out = np.zeros_like(x)
    acc = 0.0
    for i, sample in enumerate(x):
        acc += alpha * (sample - acc)
        out[i] = acc
    return out


def one_pole_highpass(x, cutoff):
    return x - one_pole_lowpass(x, cutoff)


def soft_limit(x, ceiling=0.95):
    return np.tanh(x / ceiling) * ceiling


def normalize(x, peak=0.92):
    maximum = np.max(np.abs(x))
    if maximum < 1e-9:
        return x
    return x / maximum * peak


def fade(x, attack=0.05, release=0.3):
    n = len(x)
    env = np.ones(n)
    a = min(int(attack * RATE), n // 2)
    r = min(int(release * RATE), n // 2)
    if a:
        env[:a] = np.linspace(0.0, 1.0, a) ** 1.6
    if r:
        env[-r:] = np.linspace(1.0, 0.0, r) ** 1.8
    return x * env


def tripod_horn(duration=3.4):
    """The famous 2005 siren: two detuned brass-like voices wailing on one long note."""
    t = time_axis(duration)
    vibrato = 1.0 + 0.035 * np.sin(2.0 * np.pi * 0.45 * t)
    glide = 1.0 - 0.10 * (t / duration) ** 1.4

    signal = np.zeros_like(t)
    for base, gain in ((74.0, 1.0), (110.0, 0.55), (147.0, 0.28)):
        phase = sweep_phase(base * vibrato * glide)
        for harmonic in range(1, 7):
            signal += gain / harmonic * np.sin(phase * harmonic + 0.3 * harmonic)

    # slow "breathing" tremolo, like the air being forced through the horn
    signal *= 0.72 + 0.28 * np.sin(2.0 * np.pi * 5.5 * t - np.pi / 2.0) ** 2

    # a bit of air / dust noise
    noise = rng.normal(0.0, 1.0, len(t))
    signal += 0.10 * one_pole_lowpass(noise, 900.0)

    signal = one_pole_lowpass(signal, 1800.0)
    signal = one_pole_highpass(signal, 45.0)
    signal = soft_clip(signal, 1.05)
    signal = fade(signal, attack=0.4, release=0.9)
    return soft_limit(normalize(signal, 0.75))


def heat_ray(duration=1.15):
    """A crackling heat ray: noise burst through a falling resonance plus a low thump."""
    t = time_axis(duration)
    noise = rng.normal(0.0, 1.0, len(t))

    # falling resonance: band-limited noise emulated with a swept comb
    phase = sweep_phase(2400.0 * np.exp(-3.4 * t) + 320.0)
    tone = np.sin(phase) * 0.5 + np.sin(phase * 1.97) * 0.2

    crackle = (rng.random(len(t)) < 0.02).astype(float)
    crackle = np.convolve(crackle, np.ones(24) / 24.0, mode="same")

    thump = np.sin(sweep_phase(150.0 * np.exp(-8.0 * t) + 60.0)) * np.exp(-7.0 * t)

    signal = 0.8 * noise * np.exp(-3.0 * t) + 0.6 * tone * np.exp(-1.8 * t) + 0.7 * thump
    signal += 1.1 * crackle * np.exp(-0.9 * t)
    signal = one_pole_lowpass(signal, 6500.0)
    signal = one_pole_highpass(signal, 90.0)
    signal = soft_clip(signal, 1.6)
    return soft_limit(normalize(fade(signal, attack=0.005, release=0.25), 0.78))


def tripod_step(duration=1.4):
    """A very heavy mechanical footstep: sub thump, metal ring, dust."""
    t = time_axis(duration)
    sub = np.sin(sweep_phase(58.0 * np.exp(-3.5 * t) + 34.0)) * np.exp(-4.2 * t)

    metal = np.zeros_like(t)
    for freq, gain, decay in ((318.0, 0.35, 6.0), (517.0, 0.26, 7.5), (742.0, 0.18, 9.0), (1103.0, 0.12, 11.0)):
        metal += gain * np.sin(2.0 * np.pi * freq * t) * np.exp(-decay * t)

    noise = rng.normal(0.0, 1.0, len(t)) * np.exp(-14.0 * t)
    noise = one_pole_lowpass(noise, 2500.0)

    signal = 0.95 * sub + 0.5 * metal + 0.35 * noise
    signal = one_pole_highpass(signal, 32.0)
    signal = soft_clip(signal, 1.3)
    return soft_limit(normalize(fade(signal, attack=0.004, release=0.4), 0.8))


def write(name, samples):
    path = os.path.join(SOUNDS, name)
    sf.write(path, samples.astype(np.float32), RATE, format="OGG", subtype="VORBIS")
    print(f"{name}: {len(samples) / RATE:.2f}s, {os.path.getsize(path) // 1024} KB")


def main():
    os.makedirs(SOUNDS, exist_ok=True)
    write("tripod_horn.ogg", tripod_horn())
    write("heat_ray.ogg", heat_ray())
    write("tripod_step.ogg", tripod_step())


if __name__ == "__main__":
    main()
