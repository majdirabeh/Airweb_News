package fr.airweb.news.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.airweb.news.repository.MainRepository
import fr.airweb.news.repository.model.NewModel
import fr.airweb.news.utils.SortSheetDialogFragment
import org.koin.core.KoinComponent
import timber.log.Timber

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class MainViewModel(private val mainRepository: MainRepository) : ViewModel(), KoinComponent {

    companion object {
        var TAG = MainViewModel::class.java.simpleName
    }

    private lateinit var eventClickListener: EventClickListener

    init {
        Timber.tag(TAG)
    }

    /**
     * Invoked for getting news from api
     */
    fun loadNewsFromApi() {
        mainRepository.getNews()
    }

    /**
     * Invoked for getting news from room
     * @param listTypes
     * @param orderBy
     */
    fun getPersistNews(
        listTypes: HashMap<String, Boolean>,
        orderBy: String
    ): MutableLiveData<ArrayList<NewModel>> {
        return mainRepository.getPersistNews(listTypes, orderBy)
    }

    /**
     * Invoked for getting news from room
     * @param orderBy
     */
    fun getAllPersistNews(orderBy: String): MutableLiveData<ArrayList<NewModel>> {
        return mainRepository.getAllPersistNews(orderBy)
    }

    /**
     * Invoked for getting news from room by title
     * @param keyWord
     */
    fun performSearch(keyWord: String): MutableLiveData<ArrayList<NewModel>> {
        return mainRepository.getAllPersistNewsByTitle(keyWord)
    }

    /**
     * Invoked for saving filter value
     */
    fun setFiltersValue(listTypes: HashMap<String, Boolean>) {
        mainRepository.filterTypes.value = listTypes
    }

    fun getFiltersValue(): HashMap<String, Boolean> {
        return mainRepository.filterTypes.value!!
    }

    /**
     * Invoked for saving sort value
     */
    fun setSortValue(sortValue: String) {
        mainRepository.orderBy.value = sortValue
    }

    fun getSortValue(): String {
        return mainRepository.orderBy.value!!
    }

    /**
     * Invoked for listener click buttons
     * @param eventClickListener
     */
    fun setEventClickListener(eventClickListener: EventClickListener) {
        this.eventClickListener = eventClickListener
    }

    fun getEventClickListener(): EventClickListener {
        return this.eventClickListener
    }

    /**
     * Get selected item when Detail News
     */
    fun getSelectedNew(): MutableLiveData<NewModel> {
        return mainRepository.selectedNew
    }

    /**
     * Save selected New when click recycle item
     * @param newModel
     */
    fun setSelectedNew(newModel: NewModel) {
        mainRepository.selectedNew.value = newModel
    }

    fun onFabClicked() {
        //Timber.e("onFabClicked")
        eventClickListener.onFabClicked()
    }

    fun onTriLayoutClicked() {
        //Timber.e("onTriLayoutClicked")
        eventClickListener.onTriLayoutClicked()
    }

    fun onFilterLayoutClicked() {
        //Timber.e("onFilterLayoutClicked")
        eventClickListener.onFilterLayoutClicked()
    }


    interface EventClickListener {
        fun onFabClicked()
        fun onTriLayoutClicked()
        fun onFilterLayoutClicked()
    }

}