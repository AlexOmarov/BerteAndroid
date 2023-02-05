package com.example.berteandroid.ui.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.berteandroid.business.UserViewModel
import com.example.berteandroid.persistence.entity.User
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
                it
            )
        }
    }
}

@Composable
fun MainScreenContent(data: LiveData<List<ElementData>>, padding: PaddingValues) {
    Column(Modifier.padding(padding)) {
        SearchBar()
        HomeSection { AlignBodySection(data) }
        HomeSection { FavoriteCollectionCardSection(data) }
    }
}
