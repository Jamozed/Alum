# Alum

> Minecraft Client-Side Mod

## Components

- Coordinate, TPS, FPS, and Ping HUD Elements
- Fast/Hold Right Click (for fast block placement)
- Gamma Override
- Hunger and XP HUD Elements While Mounted
- Keep Chat History
- Map Tooltips
- Mute Toggle Keybinding
- No Potion Offset in Inventory
- Ping in Player List
- Suspicious Stew Tooltip
- Vision (Gamma Override alternative for (some) shaders)
- Zoom

Modules can be configured through Mod Menu if it is installed.

## Requirements

- Fabric Loader
- Fabric API

## Build Instructions

Alum for Minecraft 1.20 uses Java 17, Gradle, and Fabric.  
Jar files will be located in the `build/libs` directory.

### UNIX Build

Run `./gradlew build` to build.  
Run `./gradlew vscode` to configure debugging for Visual Studio Code.  
Run `./gradlew genSources` to generate Minecraft source code for debugging.

## Meta

Copyright (C) 2020, Jakob Wakeling  
[MIT Licence](https://opensource.org/licenses/MIT)
