package fr.airweb.news.ui

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ferfalk.simplesearchview.utils.DimensUtils
import fr.airweb.news.App
import fr.airweb.news.Constants
import fr.airweb.news.R
import fr.airweb.news.databinding.ActivityMainBinding
import fr.airweb.news.repository.model.NewModel
import fr.airweb.news.ui.adapter.NewsAdapter
import fr.airweb.news.utils.AboutSheetDialogFragment
import fr.airweb.news.utils.FilterSheetDialogFragment
import fr.airweb.news.utils.LoaderDialog
import fr.airweb.news.utils.SortSheetDialogFragment
import fr.airweb.news.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.ArrayList

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class MainActivity : AppCompatActivity(),
    NewsAdapter.ItemClickListener,
    MainViewModel.EventClickListener,
    SortSheetDialogFragment.EventClickListener,
    FilterSheetDialogFragment.EventClickListener,
    App.OnNetworkReactive {

    companion object {
        var TAG = MainActivity::class.java.simpleName
    }

    lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private val newsAdapter by lazy {
        NewsAdapter(this)
    }
    private val loaderDialog = LoaderDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)
        App.setOnNetworkConnexion(this)
        setSupportActionBar(activityMainBinding.toolbar)
        mainViewModel.setEventClickListener(this)
        activityMainBinding.viewModel = mainViewModel
        activityMainBinding.adapter = newsAdapter
        activityMainBinding.visibilityNews = false
        activityMainBinding.executePendingBindings()
        showLoader()
        searchNews()
        getNews()
    }

    /**
     * Search news when user tap word in searchView
     */
    private fun searchNews() {
        activityMainBinding.searchView.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isNotEmpty())
                    mainViewModel.performSearch(p0.toString()).observe(this@MainActivity, Observer {
                        if (it.isNotEmpty()) {
                            newsAdapter.setNews(it)
                            activityMainBinding.visibilityNews = true
                        } else {
                            activityMainBinding.visibilityNews = false
                        }

                    })
                else
                    mainViewModel.getPersistNews(
                        mainViewModel.getFiltersValue(),
                        mainViewModel.getSortValue()
                    ).observe(this@MainActivity, Observer {
                        if (it.isNotEmpty()) {
                            newsAdapter.setNews(it)
                            activityMainBinding.visibilityNews = true
                        } else {
                            activityMainBinding.visibilityNews = false
                        }

                    })
            }
        })
    }

    /**
     *
     */
    private fun getNews() {
        mainViewModel.getPersistNews(mainViewModel.getFiltersValue(), mainViewModel.getSortValue())
            .observe(this, Observer {
                if (it.isNotEmpty()) {
                    newsAdapter.setNews(it)
                    activityMainBinding.visibilityNews = true
                } else {
                    activityMainBinding.visibilityNews = false
                }
                hideLoader()
            })
    }

    /**
     * Show progress bar dialog
     */
    private fun showLoader() {
        val supportFragmentManager = supportFragmentManager
        loaderDialog.show(supportFragmentManager, "LoaderDialog")
    }

    /**
     * Hide progress bar dialog
     */
    private fun hideLoader() {
        loaderDialog.dismiss()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        setupSearchView(menu!!)
        return true
    }

    /**
     * Initialise SearchView
     * @param menu
     */
    private fun setupSearchView(menu: Menu) {
        val item = menu.findItem(R.id.action_search)
        activityMainBinding.searchView.setMenuItem(item)
        val revealCenter: Point = activityMainBinding.searchView.revealAnimationCenter
        revealCenter.x -= DimensUtils.convertDpToPx(40, this)
    }

    override fun onBackPressed() {
        if (activityMainBinding.searchView.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (activityMainBinding.searchView.onActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Start Detail News when user click on item list
     * @param newModel
     */
    override fun onItemClick(newModel: NewModel) {
        //Timber.e(newModel.toString())
        mainViewModel.setSelectedNew(newModel)
        goToDetailNews()
    }

    /**
     * Go to DetailNewsActivity
     */
    private fun goToDetailNews() {
        val intent = Intent(this@MainActivity, DetailNewsActivity::class.java)
        startActivity(intent)
    }

    /**
     * Invoked when About fab button clicked
     */
    override fun onFabClicked() {
        AboutSheetDialogFragment().apply {
            show(supportFragmentManager, AboutSheetDialogFragment.TAG)
        }
    }

    /**
     * Invoked when Sort button clicked
     */
    override fun onTriLayoutClicked() {
        SortSheetDialogFragment().apply {
            show(supportFragmentManager, SortSheetDialogFragment.TAG)
        }.setEventClickListener(this)
    }

    /**
     * Invoked when filter button clicked
     */
    override fun onFilterLayoutClicked() {
        FilterSheetDialogFragment().apply {
            show(supportFragmentManager, FilterSheetDialogFragment.TAG)
        }.setEventClickListener(this, mainViewModel.getFiltersValue())
    }

    /**
     * Invoked when select date sort button clicked
     */
    override fun onDateClicked() {
        mainViewModel.setSortValue(Constants.SORT_BY_DATE)
    }

    /**
     * Invoked when select title sort button
     */
    override fun onTitleClicked() {
        mainViewModel.setSortValue(Constants.SORT_BY_TITLE)
    }

    /**
     * Invoked when filter button clicked
     * @param listType
     */
    override fun onSubmitFilterClicked(listType: HashMap<String, Boolean>) {
        mainViewModel.setFiltersValue(listType)
        if (listType["hot"] == true && listType["news"] == true && listType["actualité"] == true) {
            mainViewModel.getAllPersistNews(mainViewModel.getSortValue()).observe(this@MainActivity, Observer {
                if (it.isNotEmpty()) {
                    newsAdapter.setNews(it)
                    activityMainBinding.visibilityNews = true
                } else {
                    activityMainBinding.visibilityNews = false
                }

            })
        } else {
            mainViewModel.getPersistNews(
                mainViewModel.getFiltersValue(),
                mainViewModel.getSortValue()
            ).observe(this@MainActivity, Observer {
                if (it.isNotEmpty()) {
                    newsAdapter.setNews(it)
                    activityMainBinding.visibilityNews = true
                } else {
                    activityMainBinding.visibilityNews = false
                }

            })
        }
    }

    /**
     * Listener to network state
     * @param isConnected true if connect
     */
    override fun onNetworkConnexion(isConnected: Boolean) {
        if (isConnected) {
            mainViewModel.loadNewsFromApi()
//            if (mainViewModel.getPersistNews(
//                    mainViewModel.getFiltersValue(),
//                    mainViewModel.getSortValue()
//                ).value!!.isEmpty()
//            ) {
//                mainViewModel.loadNewsFromApi()
//            }
        }


    }


}