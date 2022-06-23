package com.example.bb.login

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bb.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val database: BBDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

//    private var _charities = MutableLiveData<List<Charity>>()
//    val charities: LiveData<List<Charity>>
//        get() = _charities

    val charities = database.getCharities()

    fun clearCharities() {
        viewModelScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO)
        {
            database.clearCharities()
        }
    }

//    fun getAllCharities()
//    {
//        viewModelScope.launch {
//            get()
//        }
//    }
//
//    private suspend fun get()
//    {
//        withContext(Dispatchers.IO)
//        {
//            _charities.postValue(database.getCharities())
//        }
//    }

    fun onInsertCharity() {
        viewModelScope.launch {
            var charity = Charity()
            //Ahl Masr
            charity.name = "Ahl Masr"
            charity.description =
                "The Ahl Masr Trauma and Burn Hospital is an empowerment agency for burn and trauma victims.\n" +
                        "We provide tailored and efficient integrated burn and trauma management to help re-integrate victims back into society with a healed mind, body and soul. \n" +
                        "Our emphasis on holistic wellness has inspired our patient-staff centric model, to care for the patients, families, and staff alike."
            charity.image =
                "https://ahl-masr.ngo/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2021/02/cropped-Ahl-Masr-Logo-english-and-arabic-pdf-e1650757333332.png.webp"
            insertCharity(charity)

            //Baheya Hospital
            charity.name = "Baheya Hospital"
            charity.description =
                "Who we are! The story has started when Mrs. Baheya Wahbi who is the wife of ENG.Ahmed Othman was diagnosed with breast cancer. \n" +
                        "Because of her kind heart she decided to help women who fight the disease and poverty to overcome and be cured, \n" +
                        "Baheya foundation was established in 2015 and it consists of 6 floors and devices with a value of 150 million pounds."
            charity.image = "https://baheya.org/new-uploads/settings/24911538143776.png"
            insertCharity(charity)

            //57357
            charity.name = "57357"
            charity.description =
                "Building a Sustainable Foundation to Prevent and Combat Cancer Through Research, \n" +
                        "Smart Education and Quality Healthcare Provided with Passion and Justice to Alleviate the Suffering of Children with Cancer and Their Families Free of Charge."
            charity.image =
                "https://upload.wikimedia.org/wikipedia/ar/thumb/9/9a/%D9%85%D8%B3%D8%AA%D8%B4%D9%81%D9%89_57357.png/280px-%D9%85%D8%B3%D8%AA%D8%B4%D9%81%D9%89_57357.png"
            insertCharity(charity)

            //Al Nas Hospital
            charity.name = "Al Nas Hospital"
            charity.description =
                "Al Nas is a new charitable hospital for children located in the very densely populated Shubra El Kheima area, north of Cairo, in Qalyubia governorate, one of the largest area and population governorates of the Greater Republic.\n" +
                        "\n" +
                        "This hospital considers one of the largest distinguished medical centers in the Arab region and Africa with capacity reaches about 600 beds, \n" +
                        "it should be noted that the hospital’s five buildings have been completely completed in terms of construction, finishing and equipping on an area of \u200B\u200B30,000 M containing, \n" +
                        "in addition to the new services building."
            charity.image =
                "https://alnas-hospital.com/storage/settings/February2020/QoQbputqzrVNxyGPXyp0.png"
            insertCharity(charity)

            //Magdy Yacoub
            charity.name = "Magdy Yacoub"
            charity.description =
                "10 million children every year are born with a congenital heart defect – yet 75% of them, mostly in developing countries, \n" +
                        "will not have the care they need to survive and thrive into adulthood.The Magdi Yacoub Global Heart Foundation’s Initiative is helping to change their stories.  \n" +
                        "We invite you to join us."
            charity.image =
                "https://myglobalheart.org/wp-content/uploads/2018/09/MYA_logo_PMS@2x.png\n"
            insertCharity(charity)
        }
    }

    private suspend fun insertCharity(charity: Charity) {
        withContext(Dispatchers.IO)
        {
            database.insertCharity(charity)
        }
    }

    fun onLogin(email: String, password: String) {
        viewModelScope.launch {
            find(email, password)
        }
    }

    private suspend fun find(email: String, password: String) {
        withContext(Dispatchers.IO)
        {
            database.clearCurrentUser()
            _user.postValue(database.find(email, password))
        }
    }

    fun saveCurrentUser() {
        viewModelScope.launch {
            insertCurrentUser()
        }
    }

    private suspend fun insertCurrentUser() {
        withContext(Dispatchers.IO)
        {
            if (_user.value?.id != null) {
                database.insertCurrentUser(CurrentUser(_user.value!!.id))
            }
        }
    }

    fun clearUser() {
        _user.value = User()
    }
}