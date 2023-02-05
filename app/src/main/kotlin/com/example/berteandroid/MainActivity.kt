package com.example.berteandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.berteandroid.business.UserViewModel
import com.example.berteandroid.system.database.AppDatabase
import com.example.berteandroid.ui.compose.screens.MainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initApp()
        setContent { MainScreen() }
    }

    private fun initApp() {
        val appModule = module {
            single { Room.databaseBuilder(get(), AppDatabase::class.java, "berte-db").build() }
            single { get<AppDatabase>().userDao() }
            viewModel { UserViewModel(get()) }
        }
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }
}
