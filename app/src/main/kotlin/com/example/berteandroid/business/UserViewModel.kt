package com.example.berteandroid.business

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berteandroid.persistence.entity.User
import com.example.berteandroid.persistence.repository.UserDao
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    fun getUserByName(name: String): LiveData<User> {
        Log.i("FIRST", "Get request for user with name $name")
        return userDao.findByName(name)
    }

    fun save(user: User) {
        Log.i("FIRST", "Get request for saving user $user")
        viewModelScope.launch {
            userDao.insertAll(user)
        }
    }
}