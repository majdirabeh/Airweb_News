package fr.airweb.news

import android.annotation.SuppressLint
import android.app.Application
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import fr.airweb.news.network.NetworkModule
import fr.airweb.news.repository.RepositoryModule
import fr.airweb.news.room.DatabaseModule
import fr.airweb.news.viewmodel.ViewModelModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
@SuppressLint("CheckResult")
class App : Application() {


    companion object {

        lateinit var onNetworkReactive: OnNetworkReactive

        fun setOnNetworkConnexion(onNetworkReactive: OnNetworkReactive) {
            this.onNetworkReactive = onNetworkReactive
            checkInternetConnexion()
        }

        /**
         * Check Network state
         */
        private fun checkInternetConnexion() {
            val settings = InternetObservingSettings.builder()
                .host("https://www.google.com")
                .strategy(SocketInternetObservingStrategy())
                .timeout(15000)
                .build()
            ReactiveNetwork
                .observeInternetConnectivity(settings)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<Boolean> { isConnected ->
                    onNetworkReactive.onNetworkConnexion(isConnected)
                })
        }
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKOin()
    }

    /**
     * Initialisation Timber
     */
    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    /**
     * Initialisation KOin
     */
    private fun initKOin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(NetworkModule, DatabaseModule, RepositoryModule, ViewModelModule))
        }
    }


    interface OnNetworkReactive {
        fun onNetworkConnexion(isConnected: Boolean)
    }

}