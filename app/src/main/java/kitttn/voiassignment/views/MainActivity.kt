package kitttn.voiassignment.views

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kitttn.voiassignment.R

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}