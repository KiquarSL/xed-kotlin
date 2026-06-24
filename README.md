# Kotlin Extension

This extension adds Kotlin and LSP by JetBrains

**WARNING**: It unstable extension. LSP can using a lot of memory and can slow working!

### Installation

Install the extension through the Xed-Editor's extension marketplace, and you're ready to go! Alternatively, you can download the latest release ZIP file and install it via Settings > Extensions > Install from storage.

After install extension install kotlin and lsp in Settings > Editor > Language servers > Kotlin > Install

Check installed:
```bash
kotlin --help
intellij-server --help
```

## Usage

More likely lsp from extension no working, use instruction below for using lsp.

1. Go in **Settins > Editor > LSP manager > Kotlin** (intellij-server) and disable it.
2. Add external lsp with button in down. Address: `localhost`, Port: `8081`, extension: `.kt`
3. Before start lsp run command in terminal and wait when lsp will done: `intellij-server --socket localhost:8081`
4. Open gradle/maven project and open your Kotlin files.

## Using for build plugins

If you write plugin for Xed-Editor with it extension, then change task `createFinalZig`, if you did't itz then lsp no run here!:
```groovy
tasks.register<Zip>("createFinalZip") {
    outputs.upToDateWhen { false }
    description = "Archives the generated APK files into a single ZIP file."
    group = "build"

    doFirst {
        val apkFiles = layout.buildDirectory
            .dir("outputs/apk")
            .get()
            .asFile
            .walk()
            .filter { it.extension == "apk" }
            .toList()

        if (apkFiles.size > 1) {
            throw GradleException("multiple apk files detected, this build system cannot handle multiple apk files")
        }

        if (apkFiles.isEmpty()) {
            throw GradleException("No apk files found, run ./gradlew assembleRelease first")
        }

        val apk = apkFiles.first()
        val manifest = File(rootDir, "manifest.json")

        val manifestJson: JsonObject by lazy {
            val text = manifest.readText()
            Gson().fromJson(text, JsonObject::class.java)
        }

        val extensionName: String by lazy {
            manifestJson.get("name").asString
        }

        val iconFile = File(rootDir, "icon.png")
        val readmeFile = File(rootDir, "README.md")
        val changelogFile = File(rootDir, "CHANGELOG.md")

        archiveFileName.set("$extensionName.zip")

        from(apk) { into("") }
        from(manifest) { into("") }
        from(iconFile) { into("") }
        from(readmeFile) { into("") }
        from(changelogFile) { into("") }

        destinationDirectory.set(File(rootDir, "output"))
    }
}
```

## Build

Debug build:
```bash
./gradlew assembleDebug
./gradlew :app:createFinalZip
```

Release build:
```bash
./gradlew assembleRelease
./gradlew :app:createFinalZip
```

Or use files `./compileDebug` or `./compileRelease`