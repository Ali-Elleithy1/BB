package com.example.bb.patient

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bb.database.BBDatabase
import com.example.bb.database.BBDatabaseDao

class PatientViewModelFactory (private val database: BBDatabaseDao, val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PatientViewModel::class.java))
        {
            return PatientViewModel(database,application) as T
        }
        else
        {
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }

}