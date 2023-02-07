package com.example.berteandroid.business

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berteandroid.persistence.entity.User
import com.example.berteandroid.persistence.repository.UserDao
import com.example.berteandroid.system.web.WebService
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao, private val webService: WebService) : ViewModel() {
    fun getAll(): LiveData<List<User>> {
        Log.i("FIRST", "Get request for get all users")
        return userDao.getAll()
    }

    fun getDataFromInternet(): LiveData<List<String>> {
        val result = MutableLiveData<List<String>>()
        viewModelScope.launch {
            result.value = webService.getData()
        }
        return result
    }
}