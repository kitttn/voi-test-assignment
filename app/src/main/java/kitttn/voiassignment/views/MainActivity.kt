package kitttn.voiassignment.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import kitttn.api.services.AuthService
import kitttn.voiassignment.R
import kitttn.voiassignment.extensions.component
import kitttn.voiassignment.extensions.createViewModel

class MainActivity : FragmentActivity() {
    private val viewModel by lazy {
        createViewModel(this) { component.mainViewModel }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!checkStartingIntent()) {
            authenticate()
        }
    }

    private fun checkStartingIntent(): Boolean {
        val data = intent.data ?: return false
        val code = data.getQueryParameter("code") ?: return false
        Log.i(TAG, "checkStartingIntent: Got code: $code")
        viewModel.authenticate(code)
        return true
    }

    private fun authenticate() {
        startActivity(Intent.parseUri(AuthService.AUTH_REQUEST, 0))
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}