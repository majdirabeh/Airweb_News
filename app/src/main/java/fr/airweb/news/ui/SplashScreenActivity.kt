package fr.airweb.news.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.airweb.news.App
import fr.airweb.news.databinding.ActivitySplashScreenBinding
import fr.airweb.news.utils.Utils
import fr.airweb.news.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class SplashScreenActivity : AppCompatActivity(), App.OnNetworkReactive {

    lateinit var activitySplashScreenBinding: ActivitySplashScreenBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = activitySplashScreenBinding.root
        setContentView(view)
        App.setOnNetworkConnexion(this)
        Utils.after() {
            goToMain()
        }
    }

    /**
     * Go to MainActivity
     */
    private fun goToMain() {
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onNetworkConnexion(isConnected: Boolean) {
        if (isConnected)
            mainViewModel.loadNewsFromApi()
    }


}