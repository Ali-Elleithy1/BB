package com.example.bb.donor.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bb.database.BBDatabaseDao
import com.example.bb.database.Charity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DonorHomeViewModel(val dataSource:BBDatabaseDao, application: Application) :AndroidViewModel(application)
{
//    private var _charities = MutableLiveData<List<Charity>>()
//    val charities: LiveData<List<Charity>>
//        get() = _charities

    var testCharities: List<Charity> = listOf(Charity(0L,"Ahl Masr","The Ahl Masr Trauma and Burn Hospital is an empowerment agency for burn and trauma victims.\n" +
            "We provide tailored and efficient integrated burn and trauma management to help re-integrate victims back into society with a healed mind, body and soul. \n" +
            "Our emphasis on holistic wellness has inspired our patient-staff centric model, to care for the patients, families, and staff alike.","https://ahl-masr.ngo/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2021/02/cropped-Ahl-Masr-Logo-english-and-arabic-pdf-e1650757333332.png.webp"))

    val charities = dataSource.getCharities()

    private var _navigateToCharityFragment = MutableLiveData<Long>()
    val navigateToCharityFragment: LiveData<Long>
        get() = _navigateToCharityFragment

    fun onCharityClicked(id: Long)
    {
        _navigateToCharityFragment.value = id
    }

    fun onCharityFragmentNavigated()
    {
        _navigateToCharityFragment.value = null
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