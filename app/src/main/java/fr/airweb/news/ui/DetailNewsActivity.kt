package fr.airweb.news.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import fr.airweb.news.R
import fr.airweb.news.databinding.ActivityDetailNewsBinding
import fr.airweb.news.databinding.ActivitySplashScreenBinding
import fr.airweb.news.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Majdi RABEH on 06/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class DetailNewsActivity : AppCompatActivity() {

    private lateinit var activityDetailNewsBinding: ActivityDetailNewsBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailNewsBinding = ActivityDetailNewsBinding.inflate(layoutInflater)
        val view = activityDetailNewsBinding.root
        setContentView(view)
        setupToolbar(activityDetailNewsBinding.toolbar)
        mainViewModel.getSelectedNew().observe(this, Observer {
            if (it != null) {
                activityDetailNewsBinding.newModel = it
                Picasso.get()
                    .load(it.picture)
                    .into(activityDetailNewsBinding.background)
                activityDetailNewsBinding.executePendingBindings()
            }
        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        if (supportActionBar != null) {
            val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_backspace_24)
            supportActionBar!!.setHomeAsUpIndicator(upArrow)
            toolbar.setTitleTextColor(android.graphics.Color.WHITE)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

}