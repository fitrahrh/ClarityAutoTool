# ✨ ClarityAutoTool

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.x-green?style=for-the-badge&logo=minecraft)
![Paper](https://img.shields.io/badge/Server-Paper-blue?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-1.0-brightgreen?style=for-the-badge)

**Smart Tool Switcher** A lightweight, optimized Minecraft Paper plugin that automatically switches your active hand item to the best tool in your hotbar when breaking blocks.

---

## 📑 Table of Contents

- [Features](#-features)
- [Requirements](#-requirements)
- [Installation](#-installation)
- [Commands](#-commands)
- [Permissions](#-permissions)
- [Configuration](#-configuration)
- [License](#-license)

---

## 🚀 Features

| Feature | Description |
|---------|-------------|
| **Auto Tool Switch** | Automatically selects the most efficient tool from your hotbar to break the block you are looking at. |
| **Native 1.21 API** | Uses Paper's native 1.21 API for break speed calculation and tool preference (takes enchantments into account). |
| **Smart Tool Selection** | Prevents random item swapping by ensuring the switched tool actually drops the block correctly (e.g., uses Iron Pickaxe instead of Golden Pickaxe for Diamond Ore). |
| **Clean Architecture** | Lightweight and optimized codebase that does not cause lag. |
| **Folia Support** | Safe asynchronous configuration saving that complies with Folia's regionalized threading. |
| **Instant Break Ignore** | Smartly ignores blocks that break instantly (like tall grass or torches) to avoid unnecessary tool switching. |

---

## 📋 Requirements

| Component | Minimum Version |
|-----------|-----------------|
| Server | Paper 1.21.x |
| Java | 21 |

---

## 📦 Installation

1. Download `ClarityAutoTool-1.0.jar`
2. Place the `.jar` file into your server's `plugins/` folder
3. Restart or reload the server
4. Players can immediately use `/autotool` to toggle the feature

---

## 💻 Commands

| Command | Description | Example |
|---------|-------------|---------|
| `/autotool` | Toggle AutoTool on or off for yourself | `/autotool` |
| `/autotool reload` | Reload the plugin configuration | `/autotool reload` |

> **Aliases:** `/at` can be used as an alternative to `/autotool`

---

## 🔑 Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `autotool.use` | Allows the player to use the AutoTool feature and toggle command | `true` (All players) |
| `autotool.reload` | Allows access to `/autotool reload` command | OP |

---

## ⚙️ Configuration

All configuration is located at `plugins/ClarityAutoTool/config.yml`.

### Messages

You can customize the messages sent to players using standard Minecraft color codes (`&`).

```yaml
# ClarityAutoTool Configuration

# Messages
messages:
  autotool_on: "&aAutoTool has been enabled!"
  autotool_off: "&cAutoTool has been disabled!"
  reloaded: "&aAutoTool configuration reloaded!"
  no_permission: "&cYou don't have permission to do that."

# List of player UUIDs that have disabled autotool
autotool_disabled: []
```

---

## 📄 License

This project is licensed under the MIT License.
