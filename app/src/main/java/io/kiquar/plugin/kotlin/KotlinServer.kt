package io.kiquar.plugin.kotlin

import android.app.Activity
import android.content.Context
import com.rk.exec.isTerminalInstalled
import com.rk.file.child
import com.rk.file.BuiltinFileType
import com.rk.file.sandboxHomeDir
import com.rk.icons.Icon
import com.rk.lsp.LspConnectionConfig
import com.rk.lsp.ScriptedLspServer
import java.io.File

class KotlinServer(
    override val icon: Icon? = BuiltinFileType.KOTLIN.icon,
    override val supportedExtensions: List<String> = listOf("kt", "kts"),
    override val installScript: File
) : ScriptedLspServer() {

    override val id = "kotlin"
    override val languageName = "Kotlin"
    override val serverName = "kotlin-lsp"
    override val installId = "Kotlin Language Server"

    private val kotlinLspVersion = "262.8190.0"

    override suspend fun isInstalled(context: Context): Boolean {
        if (!isTerminalInstalled()) {
            return false
        }
        return sandboxHomeDir().child(".lsp/kotlin/bin/intellij-server").exists() &&
                sandboxHomeDir().child(".lsp/kotlin/bin/intellij-server").canExecute()
    }

    override fun install(activity: Activity) {
        launchInstaller(activity, kotlinLspVersion)
    }

    override fun uninstall(activity: Activity) {
        launchInstaller(activity, "--uninstall")
    }

    override fun update(activity: Activity) {
        launchInstaller(activity, "--update")
    }

    override suspend fun isUpdatable(context: Context): Boolean {
        val versionFile = sandboxHomeDir().child(".lsp/kotlin/version.txt")
        val currentVersionText = runCatching { versionFile.readText().trim() }.getOrNull() ?: return false
        return currentVersionText != kotlinLspVersion
    }

    override fun getConnectionConfig(): LspConnectionConfig {
        return LspConnectionConfig.Socket(
            host = "localhost",
            port = 9000
        )
    }
}