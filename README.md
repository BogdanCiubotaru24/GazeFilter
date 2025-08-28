# GazeFilter — Typewriter Audience Extension

GazeFilter is a Typewriter extension that adds a packet-aware audience filter which includes players only when they are looking at a specific Typewriter entity definition. It's ideal for gaze-based prompts, tutorials, interactions, and cinematic moments.

## Features
- Packet-aware: uses per-player AudienceEntityDisplay.position(..) and ntityState(..) (no reliance on vanilla Bukkit entities).
- Tunable: maxDistance, ngleDegrees, and optional equireLineOfSight.
- Responsive: re-evaluates on tick and on player move/teleport.
- Composable: place as a parent to "trigger audience" entries to fire actions on enter/exit.

## Requirements
- Kotlin/JVM: 21
- Typewriter Engine: 0.8.0 (configurable, see "Upgrading Typewriter")
- Gradle plugin: com.typewritermc.module-plugin 1.1.3
- Paper API: 1.21.x (compileOnly)
- PacketEvents API: 2.2.1 (compileOnly)

## Project Structure
- Core entry: src/main/kotlin/com/gamesofeducation/gaze/entries/audience/GazeFilterAudienceEntry.kt
- Gradle scripts: uild.gradle.kts, settings.gradle.kts

## Build
- Windows: ./gradlew.bat build
- macOS/Linux: ./gradlew build
- Output: uild/libs/GazeFilter.jar

## Install
1. Copy GazeFilter.jar into your Typewriter extensions folder (next to your other extensions).
2. Reload Typewriter or restart the server.

## Usage
- In the Typewriter web panel, add the audience entry gaze_filter_audience.
- Set definition to the Typewriter Entity Definition you want to track.
- Add your "trigger audience" (or similar) entries under it to fire actions on enter/exit.

### Configuration Fields
- definition: the Typewriter Entity Definition to track.
- maxDistance (Double): maximum range in blocks (default 6.0).
- ngleDegrees (Double): view cone width in degrees (default 9.0). For small or flat displays, 20–30 often feels better.
- equireLineOfSight (Boolean): if true, an unobstructed block ray must exist between player and target (default false).

### Notes
- Composite/wrapper definitions: If you're using a wrapper definition (e.g., a hitbox that "owns" an item display), select the wrapper/base definition that actually owns the instance for best results.
- If aiming feels strict, widen ngleDegrees and/or slightly increase maxDistance during tuning.

## Upgrading Typewriter
You can target newer Typewriter versions by updating both the dependency and the declared engine version in uild.gradle.kts.

In uild.gradle.kts:

`kts
plugins {
    kotlin("jvm") version "2.0.21"
    id("com.typewritermc.module-plugin") version "1.1.3"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.typewritermc.com/beta") // or stable if applicable
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    // Bump this to the desired engine version
    compileOnly("com.typewritermc:engine-paper:0.8.0")

    compileOnly("com.github.retrooper.packetevents:api:2.2.1")
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
}

// Also bump the engineVersion here to match
// (This block exists in your build.gradle.kts under 	ypewriter { ... })
typewriter {
    namespace = "gamesofeducation"
    extension {
        name = "GazeFilter"
        engineVersion = "0.8.0"
        channel = com.typewritermc.moduleplugin.ReleaseChannel.BETA
        paper()
    }
}
`

Steps to upgrade:
1. Change compileOnly("com.typewritermc:engine-paper:<new-version>") to the desired version.
2. Change ngineVersion = "<new-version>" in the 	ypewriter { extension { ... } } block.
3. If you're moving off beta, switch to the stable repository and channel when available.
4. Adjust paper-api to match your target Paper/MC version if needed.
5. Run a clean build: ./gradlew clean build.

If a new Typewriter version introduces API changes, resolve compile errors accordingly and rebuild.

## License
Specify your chosen license here (e.g., MIT). If none is provided, all rights reserved by default.

## Credits
- Built for the Typewriter engine.
- Thanks to the Typewriter community for reference examples and APIs.
# GazeFilter — Typewriter Audience Extension

GazeFilter is a Typewriter extension that adds a packet‑aware audience filter which includes players only when they are looking at a specific Typewriter entity definition. It’s ideal for gaze‑based prompts, tutorials, interactions, and cinematic moments.

## Features
- Packet‑aware: uses per‑player `AudienceEntityDisplay.position(..)` and `entityState(..)` (no reliance on vanilla Bukkit entities).
- Tunable: `maxDistance`, `angleDegrees`, and optional `requireLineOfSight`.
- Responsive: re‑evaluates on tick and on player move/teleport.
- Composable: place as a parent to “trigger audience” entries to fire actions on enter/exit.

## Requirements
- Kotlin/JVM: 21
- Typewriter Engine: 0.8.0 (configurable, see “Upgrading Typewriter”)
- Gradle plugin: `com.typewritermc.module-plugin` 1.1.3
- Paper API: 1.21.x (compileOnly)
- PacketEvents API: 2.2.1 (compileOnly)

## Project Structure
- Core entry: `src/main/kotlin/com/gamesofeducation/gaze/entries/audience/GazeFilterAudienceEntry.kt`
- Gradle scripts: `build.gradle.kts`, `settings.gradle.kts`

## Build
- Windows: `./gradlew.bat build`
- macOS/Linux: `./gradlew build`
- Output: `build/libs/GazeFilter.jar`

## Install
1. Copy `GazeFilter.jar` into your Typewriter extensions folder (next to your other extensions).
2. Reload Typewriter or restart the server.

## Usage
- In the Typewriter web panel, add the audience entry `gaze_filter_audience`.
- Set `definition` to the Typewriter Entity Definition you want to track.
- Add your “trigger audience” (or similar) entries under it to fire actions on enter/exit.

### Configuration Fields
- `definition`: the Typewriter Entity Definition to track.
- `maxDistance` (Double): maximum range in blocks (default 6.0).
- `angleDegrees` (Double): view cone width in degrees (default 9.0). For small or flat displays, 20–30 often feels better.
- `requireLineOfSight` (Boolean): if true, an unobstructed block ray must exist between player and target (default false).

### Notes
- Composite/wrapper definitions: If you’re using a wrapper definition (e.g., a hitbox that “owns” an item display), select the wrapper/base definition that actually owns the instance for best results.
- If aiming feels strict, widen `angleDegrees` and/or slightly increase `maxDistance` during tuning.

## Upgrading Typewriter
You can target newer Typewriter versions by updating both the dependency and the declared engine version in `build.gradle.kts`.

In `build.gradle.kts`:

```kts
plugins {
    kotlin("jvm") version "2.0.21"
    id("com.typewritermc.module-plugin") version "1.1.3"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.typewritermc.com/beta") // or stable if applicable
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    // Bump this to the desired engine version
    compileOnly("com.typewritermc:engine-paper:0.8.0")

    compileOnly("com.github.retrooper.packetevents:api:2.2.1")
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
}

// Also bump the engineVersion here to match
// (This block exists in your build.gradle.kts under `typewriter { ... }`)
typewriter {
    namespace = "gamesofeducation"
    extension {
        name = "GazeFilter"
        engineVersion = "0.8.0"
        channel = com.typewritermc.moduleplugin.ReleaseChannel.BETA
        paper()
    }
}
```

Steps to upgrade:
1. Change `compileOnly("com.typewritermc:engine-paper:<new-version>")` to the desired version.
2. Change `engineVersion = "<new-version>"` in the `typewriter { extension { ... } }` block.
3. If you’re moving off beta, switch to the stable repository and channel when available.
4. Adjust `paper-api` to match your target Paper/MC version if needed.
5. Run a clean build: `./gradlew clean build`.

If a new Typewriter version introduces API changes, resolve compile errors accordingly and rebuild.

## License
Specify your chosen license here (e.g., MIT). If none is provided, all rights reserved by default.

## Credits
- Built for the Typewriter engine.
- Thanks to the Typewriter community for reference examples and APIs.
