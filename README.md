# GeoPulse – Environmental Heatmap HUD

A lightweight Fabric client-side mod that analyzes your surroundings in real time and displays an intuitive heatmap-style HUD bar on the left side of your screen.

GeoPulse provides instant feedback about danger, ores, and environmental conditions — helping you make smart decisions while caving, mining, or exploring.

---

## 🔥 Features

### 🛡️ Danger Detection

GeoPulse scans the player’s surroundings and assigns “Danger Points” based on:

- Nearby hostile mobs (within 5–8 blocks)
- Low light level (≤ 7)
- Lava detected in a 4-block radius
- Fall height below the player (unsafe drops or caves)

### 💎 Ore Detection

A small area around/below the player is checked for ores. Each ore type adds a specific score:

| Ore Type                | Points |
| ----------------------- | ------ |
| Iron                    | 2      |
| Gold / Redstone / Lapis | 3      |
| Diamond (regular)       | 7      |
| Diamond (deepslate)     | 10     |
| Ancient Debris          | 12     |

If ore score > danger score → the bar turns **blue**.

### 🎨 Heatmap Color Logic

| Danger Level | Color  |
| ------------ | ------ |
| 0–20         | Green  |
| 21–40        | Yellow |
| 41–70        | Orange |
| 70+          | Red    |
| Ore > Danger | Blue   |

### 🎛️ Configurable (Internal Settings)

- Toggle ore score text
- Toggle danger score text
- Toggle HUD bar visibility
- Customize bar width & height
  (Cloth Config support planned)

### 💻 Fully Client-Side

GeoPulse does **not** need to be installed on the server.

Works perfectly in:

- Singleplayer
- Multiplayer
- Modpacks

---

## 🧭 How It Works

1. Launch the game with Fabric Loader.
2. Enter any world.
3. The heatmap HUD appears on the left side of the screen.
4. Move around to see the dynamic changes:
   - Approaching mobs → danger increases
   - Near lava → danger increases
   - Discovering ores → ore score increases (bar turns blue)

Simple, fast, effective.

---

## 📦 Installation

### Requirements

- **Minecraft:** 1.20+ / 1.21+
- **Fabric Loader** (latest)
- **Fabric API** (matching your version)

### Steps

1. Download the latest GeoPulse release `.jar`.
2. Place it into your mods folder:
   - **Windows:** `%appdata%/.minecraft/mods`
   - **macOS:** `~/Library/Application Support/minecraft/mods`
   - **Linux:** `~/.minecraft/mods`
3. Start Minecraft using the _Fabric Loader_ profile.
4. Enjoy the dynamic environmental HUD!

---

## 🛠️ Building from Source

Clone the repository:

```sh
git clone https://github.com/<yourname>/geopulse.git
cd geopulse
```

Build with Gradle:

```sh
./gradlew build
```

## The compiled mod `.jar` will be in `build/libs/`.

## 📄 License

This project is licensed under the Apache 2.0 License. See the [LICENSE](LICENSE) file for details
