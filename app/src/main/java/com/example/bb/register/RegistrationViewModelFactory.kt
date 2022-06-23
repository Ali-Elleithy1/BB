package com.example.bb.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bb.database.BBDatabaseDao

class RegistrationViewModelFactory (private val dataSource: BBDatabaseDao, private val application: Application): ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegistrationViewModel::class.java))
        {
            return RegistrationViewModel(dataSource, application) as T
        }
        else
        {
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}