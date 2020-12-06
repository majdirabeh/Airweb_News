package fr.airweb.news.room.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
@Entity(tableName = "news")
class NewEntity(
    @ColumnInfo(name = "nid") @PrimaryKey @field:NonNull val nid: Int,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "dateformated") val dateformated: String,
    @ColumnInfo(name = "picture") val picture: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: String
)