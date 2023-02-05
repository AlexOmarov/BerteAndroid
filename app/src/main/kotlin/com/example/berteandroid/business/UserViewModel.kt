package com.example.berteandroid.business

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.berteandroid.persistence.entity.User
import com.example.berteandroid.persistence.repository.UserDao

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    fun getAll(): LiveData<List<User>> {
        Log.i("FIRST", "Get request for get all users")
        return userDao.getAll()
    }
}