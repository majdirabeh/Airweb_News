package fr.airweb.news.network.service

import fr.airweb.news.network.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
interface ApiService {

    @GET("psg/psg.json")
    fun getNews(): Observable<NewsResponse>

}