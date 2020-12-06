package fr.airweb.news.repository.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class NewModel(
    val content: String,
    val date: String,
    val dateformated: String,
    val nid: Int,
    val picture: String,
    val title: String,
    val type: String
)
