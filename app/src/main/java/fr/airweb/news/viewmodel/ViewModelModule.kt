package fr.airweb.news.viewmodel

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */

val ViewModelModule = module {
    viewModel { MainViewModel(get()) }
}