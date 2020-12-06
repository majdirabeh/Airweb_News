package fr.airweb.news.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.airweb.news.room.dao.NewsDao
import fr.airweb.news.room.entity.NewEntity

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
@Database(entities = [NewEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}