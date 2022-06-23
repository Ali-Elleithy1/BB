package com.example.bb.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bb.database.BBDatabaseDao

class LoginViewModelFactory(private val dataSource: BBDatabaseDao, private val application: Application) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java))
        {
            return LoginViewModel(dataSource,application) as T
        }
        else
        {
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }

}