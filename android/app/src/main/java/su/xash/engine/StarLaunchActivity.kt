package su.xash.engine

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class StarLaunchActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launch = Intent(this, XashActivity::class.java).apply {
            putExtra("gamedir", "cstrike")
            putExtra("basedir", "/sdcard/xash")
            putExtra("gamelibdir", applicationInfo.nativeLibraryDir)
            putExtra("argv", "-log")
        }
        startActivity(launch)
        finish()
    }
}
