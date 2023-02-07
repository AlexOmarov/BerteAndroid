package com.example.berteandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.berteandroid.business.viewmodel.UserViewModel
import com.example.berteandroid.persistence.entity.User
import com.example.berteandroid.persistence.repository.UserDao
import com.example.berteandroid.system.database.AppDatabase
import com.example.berteandroid.system.web.WebService
import com.example.berteandroid.ui.compose.screens.MainScreen
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import java.util.*

class MainActivity : ComponentActivity() {

    private val service: UserDao by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initApp()
        setContent { MainScreen() }
        lifecycleScope.launch {
            service.insertAll(User(UUID.randomUUID(), "FIRST"))
        }
    }

    private fun initApp() {
        val appModule = module {
            single { Room.databaseBuilder(get(), AppDatabase::class.java, "berte-db").build() }
            single { get<AppDatabase>().userDao() }
            single { WebService() }
            viewModel { UserViewModel(get(), get()) }
        }
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }
}
