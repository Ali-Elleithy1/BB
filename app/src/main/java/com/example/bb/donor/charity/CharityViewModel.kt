package com.example.bb.donor.charity

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

class CharityViewModel(val dataSource: BBDatabaseDao, application: Application) :AndroidViewModel(application) {
    private var _charity = MutableLiveData<Charity>()
    val charity: LiveData<Charity>
        get() = _charity


    private var _cases = MutableLiveData<List<Case>?>()
    val cases: LiveData<List<Case>?>
        get() = _cases

    private var _navigateToCaseFragment = MutableLiveData<Long>()
    val navigateToCaseFragment: LiveData<Long>
        get() = _navigateToCaseFragment

    fun onCaseClicked(id: Long) {
        _navigateToCaseFragment.value = id
    }

    fun onCaseyFragmentNavigated() {
        _navigateToCaseFragment.value = null
    }

    fun onRetrieveData(charityId: Long) {
        viewModelScope.launch {
            retrieveData(charityId)
        }
    }

    private suspend fun retrieveData(charityId: Long) {
        withContext(Dispatchers.IO)
        {
            _charity.postValue(dataSource.getCharityById(charityId))
            _cases.postValue(dataSource.getCharityCases(charityId))
        }
    }
}
