package fr.airweb.news.room.dao

import androidx.lifecycle.LiveData

import fr.airweb.news.room.entity.NewEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(new: NewEntity): Long

    @Query("SELECT DISTINCT * FROM news  WHERE type = :typeHot or type= :typeNews or type= :typeActuality ORDER BY LOWER(date) ASC")
    fun getNewsByDateStream(
        typeHot: String,
        typeNews: String,
        typeActuality: String
    ): Flowable<List<NewEntity>>

    @Query("SELECT DISTINCT * FROM news  WHERE type = :typeHot or type= :typeNews or type= :typeActuality ORDER BY LOWER(title) ASC")
    fun getNewsByTitleStream(
        typeHot: String,
        typeNews: String,
        typeActuality: String
    ): Flowable<List<NewEntity>>

    @Query("SELECT DISTINCT  * FROM news ORDER BY LOWER(title) ASC")
    fun getAllNewsByTitleStream(): Flowable<List<NewEntity>>

    @Query("SELECT DISTINCT  * FROM news ORDER BY LOWER(date) ASC")
    fun getAllNewsByDateStream(): Flowable<List<NewEntity>>

    @Query("SELECT DISTINCT * FROM news WHERE title LIKE '%' || :keyWord || '%' ORDER BY LOWER(title)")
    fun getAllNewsByWordsStream(keyWord: String): Flowable<List<NewEntity>>

    @Query("DELETE FROM news")
    fun clearNews()


}