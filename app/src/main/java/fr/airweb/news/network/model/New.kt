package fr.airweb.news.network.model

import com.google.gson.annotations.SerializedName
import fr.airweb.news.room.entity.NewEntity

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
data class New(
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String,
    @SerializedName("dateformated") val dateformated: String,
    @SerializedName("nid") val nid: Int,
    @SerializedName("picture") val picture: String,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String
) {
    fun toNewEntity(): NewEntity {
        return NewEntity(
            nid,
            content,
            date,
            dateformated,
            picture,
            title,
            type
        )
    }
}