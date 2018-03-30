package ui.anwesome.com.kotlinplugview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.plugview.PlugView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlugView.create(this)
    }
}
