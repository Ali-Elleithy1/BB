package com.example.bb.donor.cases

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bb.database.BBDatabaseDao

class CaseViewModelFactory (private val dataSource: BBDatabaseDao, private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CaseViewModel::class.java))
        {
            return CaseViewModel(dataSource,application) as T
        }
        else
        {
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }

}