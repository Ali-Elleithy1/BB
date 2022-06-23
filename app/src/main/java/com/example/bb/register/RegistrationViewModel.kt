package com.example.bb.register

import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bb.database.BBDatabaseDao
import com.example.bb.database.Case
import com.example.bb.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class RegistrationViewModel(val database:BBDatabaseDao, application: Application): AndroidViewModel(application) {
    private var _user = MutableLiveData<User>()
    val user:LiveData<User>
        get() = _user

    private var _purpose: Int = -1

    private var newUser = User()
    private var newCase = Case()

    var isButtonClicked = false

    private val mobileCodes: List<String> = listOf("+93", "+355", "+213",  "+376", "+244", "+1-268",  "+54", "+374", "+61", "+43", "+994", "+1-242", "+973", "+880", "+1-246", "+375", "+32", "+501",
        "+229", "+975", "+591", "+387", "+267", "+55", "+673", "+359", "+226", "+257", "+238", "+855", "+237", "+1", "+235", "+56", "+86", "+57", "+269", "+243", "+242", "+506", "+225", "+385",
        "+53", "+357", "+420", "+45", "+253", "+1-767", "+1-809, 1-829, 1-849", "+670", "+593", "+20", "+503", "+240", "+291", "+372", "+268", "+251", "+679", "+358", "+33", "+241", "+220", "+995",
    "+49", "+233", "+30", "+1-473", "+502", "+224", "+245", "+592", "+509", "+504", "+36", "+354", "+91", "+62", "+98", "+964", "+353", "+39", "+1-876", "+81", "+962", "+7", "+254", "+686", "+850",
    "+82", "+383", "+965", "+996", "+856", "+371", "+961", "+266", "+231", "+218", "+423", "+370", "+352", "+261", "+265", "+60", "+960", "+223", "+356", "+692", "+222", "+230", "+52", "+691",
    "+373", "+377", "+976", "+382", "+212", "+258", "+95", "+264", "+674", "+977", "+31", "+64", "+505", "+227", "+234", "+389", "+47", "+968", "+92", "+680", "+507", "+675", "+595", "+51",
    "+63", "+48", "+351", "+974", "+40", "+7", "+250", "+1-869", "+1-758", "+1-784", "+685", "+378", "+239", "+966", "+221", "+381", "+248", "+232", "+65", "+421", "+386", "+677", "+252", "+27",
    "+34", "+94", "+211", "+597", "+46", "+41", "+963", "+886", "+992", "+255", "+66", "+228", "+676", "+1-868", "+216", "+90", "+993", "+688", "+256", "+380", "+971", "+44", "+1", "+598",
    "+998", "+678", "+379", "+58", "+84", "+967", "+260", "+263")

    private val countries: List<String> = listOf("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria",
                                                "Azerbaijan", "The Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia",
                                                "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cape Verde", "Cambodia",
                                                "Cameroon", "Canada", "Chad", "Chile", "China", "Colombia", "Comoros", "Democratic Republic of the Congo", "Republic of the Congo",
                                                "Costa Rica", "Côte d’Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
                                                "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland",
                                                "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",
                                                "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Italy", "Jamaica", "Japan", "Jordan",
                                                "Kazakhstan", "Kenya", "Kiribati", "North Korea", "South Korea", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon",
                                                "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali",
                                                "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
                                                "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua",
                                                "Niger", "Nigeria", "Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru",
                                                "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
                                                "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore",
                                                "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "Spain", "Sri Lanka", "South Sudan", "Suriname", "Sweden",
                                                "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
                                                "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan",
                                                "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe")



    private val purposes: List<String> = listOf("Patient", "Donor")
    private val governorates: List<String> = listOf("Alexandria", "Assiut", "Aswan", "Beheira", "Bani Suef", "Cairo", "Daqahliya", "Damietta", "Fayyoum", "Gharbiya", "Giza", "Helwan",
                                                    "Ismailia", "Kafr El Sheikh", "Luxor", "Marsa Matrouh", "Minya", "Monofiya", "New Valley", "North Sinai", "Port Said", "Qalioubiya",
                                                    "Qena", "Red Sea", "Sharqiya", "Sohag", "South Sinai", "Suez", "Tanta")

    private val cases: List<String> = listOf("Cancer", "General Surgery")

    val mobileCodesAdapter = ArrayAdapter(application.applicationContext,android.R.layout.simple_spinner_item, mobileCodes)

    val countriesAdapter = ArrayAdapter(application.applicationContext, android.R.layout.simple_spinner_item, countries)

    val casesAdapter = ArrayAdapter(application.applicationContext,android.R.layout.simple_spinner_item,cases)

    val purposesAdapter = ArrayAdapter(application.applicationContext,android.R.layout.simple_spinner_item,purposes)

    val governoratesAdapter = ArrayAdapter(application.applicationContext,android.R.layout.simple_spinner_item,governorates)

    fun setPurpose(purpose: Int)
    {
        _purpose = purpose
    }


    fun onRegister(fName: String, lName: String, email: String,mobile: String, password: String, governorate: String, case: String, age: Int, natId:String, purpose: Int)
    {
        newUser.firstName = fName
        newUser.lastName = lName
        newUser.email = email
        newUser.mobile = mobile.toLong()
        newUser.password = password
        if(purpose == 0) {
            newUser.governorate = governorate
            newUser.natId = natId.toLong()
            newUser.category = case
            newUser.age = age

            newCase.category = newUser.category
            newCase.description = "Pending Review"
            newCase.priorityLevel = "Pending Review"
            newCase.status = "Pending Review"
            newCase.userId = newUser.id
            newCase.charityId = (Random.nextInt(0, 4) + 1).toLong()
        }
        newUser.purpose = purpose

        viewModelScope.launch {
            insert(newUser)
        }
    }

    private suspend fun insert(user: User)
    {
        withContext(Dispatchers.IO)
        {
            database.insert(user)
            _user.postValue(database.getLastUser())
        }
    }

    fun updateUserIdInCase()
    {
        if(_purpose == 0) {
            newCase.userId = _user.value!!.id
            viewModelScope.launch {
                update()
            }
        }
    }
    private suspend fun update()
    {
        withContext(Dispatchers.IO)
        {
            database.insertCase(newCase)
        }
    }
}