package fr.airweb.news.room

import androidx.room.Room
import org.koin.dsl.module

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */

val DatabaseModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "airweb_news-db").build() }
    single { get<AppDatabase>().newsDao() }
}