package com.example.bb.donor.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bb.database.BBDatabaseDao
import com.example.bb.database.Case
import com.example.bb.database.Charity
import com.example.bb.use_case.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DonorHomeViewModel(val dataSource:BBDatabaseDao, application: Application) :AndroidViewModel(application)
{
//    private var _charities = MutableLiveData<List<Charity>>()
//    val charities: LiveData<List<Charity>>
//        get() = _charities

    private var _case = MutableLiveData<Case>()
    val case:LiveData<Case>
        get() = _case

    var testCharities: List<Charity> = listOf(Charity(0L,"Ahl Masr","The Ahl Masr Trauma and Burn Hospital is an empowerment agency for burn and trauma victims.\n" +
            "We provide tailored and efficient integrated burn and trauma management to help re-integrate victims back into society with a healed mind, body and soul. \n" +
            "Our emphasis on holistic wellness has inspired our patient-staff centric model, to care for the patients, families, and staff alike.","https://ahl-masr.ngo/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2021/02/cropped-Ahl-Masr-Logo-english-and-arabic-pdf-e1650757333332.png.webp"))

    val charities = dataSource.getCharities()

    private var _navigateToCharityFragment = MutableLiveData<Long>()
    val navigateToCharityFragment: LiveData<Long>
        get() = _navigateToCharityFragment

    private var _navigateToCaseFragment = MutableLiveData<Long>()
    val navigateToCaseFragment: LiveData<Long>
        get() = _navigateToCaseFragment

    private var isACaseNumValid = false


    fun setIsCaseNumValid(result: Boolean)
    {
        isACaseNumValid = result
    }
    fun getIsCaseNumValid(): Boolean
    {
        return isACaseNumValid
    }

    fun onCharityClicked(id: Long)
    {
        _navigateToCharityFragment.value = id
    }

    fun onCharityFragmentNavigated()
    {
        _navigateToCharityFragment.value = null
    }

    fun onViewCaseClicked(id: Long)
    {
        _navigateToCaseFragment.value = id
    }

    fun onCaseFragmentNavigated()
    {
        _navigateToCaseFragment.value = null
        _case.value = null
    }

    fun validateCaseNumber(caseNum: String): ValidationResult {
        if(caseNum.isBlank())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Case Number cannot be empty!"
            )
        }
        return ValidationResult(successful = true)
    }

    fun getCase(id: Long)
    {
        viewModelScope.launch {
            find(id)
        }
    }

    private suspend fun find(id: Long)
    {
        withContext(Dispatchers.IO)
        {
            _case.postValue(dataSource.getCaseByCaseId(id))
        }
    }



//    fun getCharities()
//    {
//        viewModelScope.launch {
//            getAllCharities()
//        }
//    }
//
//    private suspend fun getAllCharities()
//    {
//        withContext(Dispatchers.IO){
//            charities = dataSource.getCharities()
//        }
//    }
}