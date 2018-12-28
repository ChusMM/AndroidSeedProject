package com.iecisa.androidseed

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.iecisa.androidseed.view.activities.HeroesListActivity
import kotlinx.android.synthetic.main.activity_splash.sample_text

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()

        val intent = Intent(this, HeroesListActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
