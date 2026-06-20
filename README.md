# Kotlin Extension

This extension adds Kotlin language and LSP by JetBrains

### Installation

Install the extension through the Xed-Editor's extension marketplace, and you're ready to go! Alternatively, you can download the latest release ZIP file and install it via Settings > Extensions > Install from storage.

After install extension install kotlin and lsp in Settings > Editor > Language servers > Kotlin > Install

Check installed:
```bash
kotlin --help
intellij-server --help
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