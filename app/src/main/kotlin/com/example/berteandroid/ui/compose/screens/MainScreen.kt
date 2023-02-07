package com.example.berteandroid.ui.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.berteandroid.business.viewmodel.UserViewModel
import com.example.berteandroid.persistence.entity.User
import com.example.berteandroid.system.web.dto.Mars
import com.example.berteandroid.ui.compose.defaults.BerteBottomNavigation
import com.example.berteandroid.ui.compose.defaults.HomeSection
import com.example.berteandroid.ui.compose.defaults.SearchBar
import com.example.berteandroid.ui.compose.dto.ElementData
import com.example.berteandroid.ui.compose.sections.AlignBodySection
import com.example.berteandroid.ui.compose.sections.FavoriteCollectionCardSection
import com.example.berteandroid.ui.compose.theme.BerteAndroidTheme
import org.koin.androidx.compose.koinViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(userViewModel: UserViewModel = koinViewModel()) {
    BerteAndroidTheme {
        Scaffold(bottomBar = { BerteBottomNavigation() }) {
            MainScreenContent(
                userViewModel.getAll().map { users: List<User> ->
                    users.map { user ->
                        ElementData(
                            user.name,
                            user.id.toString()
                        )
                    }
                },
                userViewModel.getDataFromInternet(),
                it
            )
        }
    }
}

@Composable
fun MainScreenContent(data: LiveData<List<ElementData>>, dataFromNet: LiveData<List<Mars>>, padding: PaddingValues) {
    Column(Modifier.padding(padding)) {
        val state by dataFromNet.observeAsState()
        val res = state ?: listOf()
        SearchBar()
        HomeSection { AlignBodySection(data) }
        HomeSection { FavoriteCollectionCardSection(data) }
        Text(res.toString())
    }
}
