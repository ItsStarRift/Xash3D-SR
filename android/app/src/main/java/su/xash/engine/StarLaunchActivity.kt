package su.xash.engine

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class StarLaunchActivity : Activity() {
    private val PERM_REQUEST = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            MaterialAlertDialogBuilder(this).apply {
                setTitle("All-files access required")
                setMessage("This app needs access to all files to load Counter-Strike 1.6 content.")
                setPositiveButton("Open settings") { _, _ ->
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).setData(
                        Uri.fromParts("package", packageName, null)
                    )
                    startActivityForResult(intent, PERM_REQUEST)
                }
                setNeutralButton("Done! Check permissions") { _, _ ->
                    checkStoragePermission()
                }
                setCancelable(false)
                show()
            }
        } else {
            launchGame()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERM_REQUEST) {
            checkStoragePermission()
        }
    }

    private fun launchGame() {
        val launch = Intent(this, XashActivity::class.java).apply {
            putExtra("gamedir", "cstrike")
            putExtra("basedir", "/storage/emulated/0/xash")
            putExtra("gamelibdir", applicationInfo.nativeLibraryDir)
            putExtra("argv", "-log")
            putExtra("package", packageName)
        }
        startActivity(launch)
        finish()
    }
}
