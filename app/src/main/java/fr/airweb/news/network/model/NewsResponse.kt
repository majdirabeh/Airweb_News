package fr.airweb.news.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
data class NewsResponse(
    @SerializedName("news") val news: List<New>
)