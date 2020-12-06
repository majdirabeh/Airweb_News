package fr.airweb.news.repository

import org.koin.dsl.module

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */

val RepositoryModule = module {
    single { MainRepository(get(), get()) }
}