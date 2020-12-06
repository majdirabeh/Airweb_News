package fr.airweb.news.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import fr.airweb.news.Constants
import fr.airweb.news.Constants.Companion.SORT_BY_TITLE
import fr.airweb.news.network.model.New
import fr.airweb.news.network.model.NewsResponse
import fr.airweb.news.network.service.ApiService
import fr.airweb.news.repository.model.NewModel
import fr.airweb.news.room.dao.NewsDao
import fr.airweb.news.room.entity.NewEntity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
@SuppressLint("CheckResult")
class MainRepository(private val apiService: ApiService, private val newsDao: NewsDao) {


    var selectedNew = MutableLiveData<NewModel>()
    var orderBy = MutableLiveData<String>()
    var filterTypes = MutableLiveData<HashMap<String, Boolean>>()
    val listNews: ArrayList<NewModel> = arrayListOf()

    init {
        Timber.tag(MainRepository::class.java.simpleName)
        orderBy.value = SORT_BY_TITLE
        val listTypes = hashMapOf<String, Boolean>()
        listTypes.put("news", true)
        listTypes.put("hot", false)
        listTypes.put("actualité", false)
        filterTypes.value = listTypes
    }

    fun getNews() {
        val observer = object : Observer<NewsResponse> {
            override fun onNext(t: NewsResponse) {
                newsDao.clearNews()
                t.news.forEach {
                    newsDao.insert(it.toNewEntity())
                    //Timber.e("onNext ${it.title}")
                }

                t.news.forEach {
                    listNews.add(convertNewModel(it))
                }
                //Timber.e("onNext ${listNews.size}")
            }

            override fun onComplete() {
                //Timber.e("onComplete")
                //listNewModel.value = listNews
            }

            override fun onSubscribe(d: Disposable) {
                //Timber.tag("MainRepository").e("onSubscribe")

            }

            override fun onError(e: Throwable) {
                //Timber.e("onError ${e.message}")
            }
        }

        apiService.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(observer)

    }


    fun getPersistNews(
        listType: HashMap<String, Boolean>,
        orderBy: String
    ): MutableLiveData<ArrayList<NewModel>> {
        val listNewModel = MutableLiveData<ArrayList<NewModel>>()
        val listNews: ArrayList<NewModel> = arrayListOf()

        var typeHot = ""
        var typeNews = ""
        var typeActuality = ""

        if (listType["hot"] == true) {
            typeHot = "hot"
        }
        if (listType["news"] == true) {
            typeNews = "news"
        }
        if (listType["actualité"] == true) {
            typeActuality = "actualité"
        }
        if (orderBy == SORT_BY_TITLE) {
            newsDao.getNewsByTitleStream(typeHot, typeNews, typeActuality)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    listNews.clear()
                    it.forEach {
                        listNews.add(convertNewEntityModel(it))
                    }
                    listNewModel.value = arrayListOf()
                    listNewModel.value = listNews
                }

        } else {
            newsDao.getNewsByDateStream(typeHot, typeNews, typeActuality)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    listNews.clear()
                    it.forEach {
                        listNews.add(convertNewEntityModel(it))
                    }
                    listNewModel.value = arrayListOf()
                    listNewModel.value = listNews
                }
        }

        //Timber.e("Persist list News ${listNews.value!!.size}")
        return listNewModel
    }

    fun getAllPersistNews(orderBy: String): MutableLiveData<ArrayList<NewModel>> {
        val listNewModel = MutableLiveData<ArrayList<NewModel>>()
        val listNews: ArrayList<NewModel> = arrayListOf()
        if (orderBy == SORT_BY_TITLE) {
            newsDao.getAllNewsByTitleStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    listNews.clear()
                    it.forEach {
                        listNews.add(convertNewEntityModel(it))
                    }
                    listNewModel.value = arrayListOf()
                    listNewModel.value = listNews
                }
        } else {
            newsDao.getAllNewsByDateStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    listNews.clear()
                    it.forEach {
                        listNews.add(convertNewEntityModel(it))
                    }
                    listNewModel.value = arrayListOf()
                    listNewModel.value = listNews
                }
        }


        //Timber.e("Persist list News ${listNews.value!!.size}")
        return listNewModel
    }

    @SuppressLint("CheckResult")
    fun getAllPersistNewsByTitle(keyWord: String): MutableLiveData<ArrayList<NewModel>> {
        val listNewModel = MutableLiveData<ArrayList<NewModel>>()
        val listNews: ArrayList<NewModel> = arrayListOf()

        newsDao.getAllNewsByWordsStream(keyWord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                listNews.clear()
                it.forEach {
                    listNews.add(convertNewEntityModel(it))
                }
                listNewModel.value = arrayListOf()
                listNewModel.value = listNews
            }

        //Timber.e("Persist list News ${listNews.value!!.size}")
        return listNewModel
    }

    private fun convertNewEntityModel(newEntity: NewEntity): NewModel {
        return NewModel(
            newEntity.content,
            newEntity.date,
            newEntity.dateformated,
            newEntity.nid,
            newEntity.picture,
            newEntity.title,
            newEntity.type
        )
    }

    private fun convertNewModel(newEntity: New): NewModel {
        return NewModel(
            newEntity.content,
            newEntity.date,
            newEntity.dateformated,
            newEntity.nid,
            newEntity.picture,
            newEntity.title,
            newEntity.type
        )
    }


}