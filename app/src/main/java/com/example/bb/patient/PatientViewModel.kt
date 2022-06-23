package com.example.bb.patient

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bb.database.BBDatabase
import com.example.bb.database.BBDatabaseDao
import com.example.bb.database.Case
import com.example.bb.database.CurrentUser
import com.example.bb.database.relations.PatientAndCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientViewModel (val database: BBDatabaseDao, application: Application): AndroidViewModel(application)
{
    private var _case = MutableLiveData<List<PatientAndCase>>()
    val case: LiveData<List<PatientAndCase>>
        get() = _case

    fun getCase()
    {
        viewModelScope.launch {
            getCaseWithUserId()
        }
    }

    private suspend fun getCaseWithUserId()
    {
        withContext(Dispatchers.IO)
        {
            _case.postValue(database.getPatientAndCaseWithUserId(database.getCurrentUser()))
        }
    }
}