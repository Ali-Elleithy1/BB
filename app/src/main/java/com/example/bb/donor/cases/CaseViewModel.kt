package com.example.bb.donor.cases

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bb.database.BBDatabaseDao
import com.example.bb.database.Case
import com.example.bb.database.Charity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CaseViewModel (val database: BBDatabaseDao, application: Application): AndroidViewModel(application)
{
    private var _case = MutableLiveData<Case>()
    val case: LiveData<Case>
        get() = _case

    private var _charity = MutableLiveData<Charity>()
    val charity: LiveData<Charity>
        get() = _charity

    fun retrieveCharity(charityId: Long)
    {
        viewModelScope.launch {
            getCharity(charityId)
        }
    }

    private suspend fun getCharity(charityId: Long)
    {
        withContext(Dispatchers.IO)
        {
            _charity.postValue(database.getCharityById(charityId))
        }
    }

    fun onRetrieveCase(caseId: Long)
    {
        viewModelScope.launch {
            getCase(caseId)
        }
    }

    private suspend fun getCase(caseId: Long)
    {
        withContext(Dispatchers.IO)
        {
            _case.postValue(database.getCaseByCaseId(caseId))
        }
    }

}