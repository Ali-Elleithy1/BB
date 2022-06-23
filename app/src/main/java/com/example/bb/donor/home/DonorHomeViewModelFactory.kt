package com.example.bb.donor.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bb.database.BBDatabaseDao

class DonorHomeViewModelFactory (private val database:BBDatabaseDao, private val application: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DonorHomeViewModel::class.java))
        {
            return DonorHomeViewModel(database,application) as T
        }
        else
        {
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }

}