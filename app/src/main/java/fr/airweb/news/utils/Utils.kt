package fr.airweb.news.utils

import androidx.appcompat.app.AppCompatActivity
import fr.airweb.news.Constants
import java.util.*
import kotlin.concurrent.schedule

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class Utils {
    companion object {
        fun after(process: () -> Unit) {
            Timer().schedule(Constants.TIMER_SPLASH) {
                process()
            }
        }
    }
}