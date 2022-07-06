package com.example.bb.register

import android.app.Application
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bb.database.BBDatabase.Companion.getInstance
import com.example.bb.database.BBDatabaseDao
import com.example.bb.database.Case
import com.example.bb.database.User
import com.example.bb.use_case.ValidationResult
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

    private val mobileCodes: List<String> = listOf("+93", "+355", "+213",  "+376", "+244", "+1-268",  "+54", "+374", "+61", "+43", "+994", "+1-242", "+973",
        "+880", "+1-246", "+375", "+32", "+501", "+229", "+975", "+591", "+387", "+267", "+55", "+673", "+359", "+226", "+257", "+238", "+855", "+237", "+1",
        "+235", "+56", "+86", "+57", "+269", "+243", "+242", "+506", "+225", "+385", "+53", "+357", "+420", "+45", "+253", "+1-767", "+1-809, 1-829, 1-849",
        "+670", "+593", "+20", "+503", "+240", "+291", "+372", "+268", "+251", "+679", "+358", "+33", "+241", "+220", "+995", "+49", "+233", "+30", "+1-473",
        "+502", "+224", "+245", "+592", "+509", "+504", "+36", "+354", "+91", "+62", "+98", "+964", "+353", "+39", "+1-876", "+81", "+962", "+7", "+254", "+686",
        "+850", "+82", "+383", "+965", "+996", "+856", "+371", "+961", "+266", "+231", "+218", "+423", "+370", "+352", "+261", "+265", "+60", "+960", "+223",
        "+356", "+692", "+222", "+230", "+52", "+691", "+373", "+377", "+976", "+382", "+212", "+258", "+95", "+264", "+674", "+977", "+31", "+64", "+505", "+227",
        "+234", "+389", "+47", "+968", "+92", "+680", "+507", "+675", "+595", "+51", "+63", "+48", "+351", "+974", "+40", "+7", "+250", "+1-869", "+1-758", "+1-784",
        "+685", "+378", "+239", "+966", "+221", "+381", "+248", "+232", "+65", "+421", "+386", "+677", "+252", "+27", "+34", "+94", "+211", "+597", "+46", "+41",
        "+963", "+886", "+992", "+255", "+66", "+228", "+676", "+1-868", "+216", "+90", "+993", "+688", "+256", "+380", "+971", "+44", "+1", "+598", "+998", "+678",
        "+379", "+58", "+84", "+967", "+260", "+263")

    private val countries: List<String> = listOf("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria",
        "Azerbaijan", "The Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",
        "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cape Verde", "Cambodia", "Cameroon", "Canada", "Chad", "Chile", "China", "Colombia", "Comoros",
        "Democratic Republic of the Congo", "Republic of the Congo", "Costa Rica", "Côte d’Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti",
        "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland",
        "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary",
        "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "North Korea", "South Korea",
        "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi",
        "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro",
        "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Macedonia", "Norway", "Oman", "Pakistan",
        "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis",
        "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",
        "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "Spain", "Sri Lanka", "South Sudan", "Suriname", "Sweden", "Switzerland", "Syria",
        "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine",
        "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe")



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

    private var isPurposeValid = false
    private var isCountryValid = false
    private var isMobileValid = false
    private var isFNameValid = false
    private var isLNameValid = false
    private var isEmailValid = false
    private var isPasswordValid = false
    private var isConfirmPasswordValid = false
    private var isGovernorateValid = false
    private var isCaseValid = false
    private var isNatIdValid = false
    private var isAgeValid = false


    fun setIsPurposeValid(result: Boolean)
    {
        isPurposeValid = result
    }
    fun getIsPurposeValid(): Boolean
    {
        return isPurposeValid
    }
    fun setIsCountryValid(result: Boolean)
    {
        isCountryValid = result
    }
    fun getIsCountryValid(): Boolean
    {
        return isCountryValid
    }
    fun setIsMobileValid(result: Boolean)
    {
        isMobileValid = result
    }
    fun getIsMobileValid(): Boolean
    {
        return isMobileValid
    }
    fun setIsFNameValid(result: Boolean)
    {
        isFNameValid = result
    }
    fun getIsFNameValid(): Boolean
    {
        return isFNameValid
    }
    fun setIsLNameValid(result: Boolean)
    {
        isLNameValid = result
    }
    fun getIsLNameValid(): Boolean
    {
        return isLNameValid
    }
    fun setIsEmailValid(result: Boolean)
    {
        isEmailValid = result
    }
    fun getIsEmailValid(): Boolean
    {
        return isEmailValid
    }
    fun setIsPasswordValid(result: Boolean)
    {
        isPasswordValid = result
    }
    fun getIsPasswordValid(): Boolean
    {
        return isPasswordValid
    }
    fun setIsConfirmPasswordValid(result: Boolean)
    {
        isConfirmPasswordValid = result
    }
    fun getIsConfirmPasswordValid(): Boolean
    {
        return isConfirmPasswordValid
    }

    fun setIsGovernorateValid(result: Boolean)
    {
        isGovernorateValid = result
    }
    fun getIsGovernorateValid(): Boolean
    {
        return isGovernorateValid
    }

    fun setIsCaseValid(result: Boolean)
    {
        isCaseValid = result
    }
    fun getIsCaseValid(): Boolean
    {
        return isCaseValid
    }

    fun setIsNatIdValid(result: Boolean)
    {
        isNatIdValid = result
    }
    fun getIsNatIdValid(): Boolean
    {
        return isNatIdValid
    }

    fun setIsAgeValid(result: Boolean)
    {
        isAgeValid = result
    }
    fun getIsAgeValid(): Boolean
    {
        return isAgeValid
    }



    fun setPurpose(purpose: Int)
    {
        _purpose = purpose
    }

    fun getCode(country: String): String
    {
        for((index,item) in countries.withIndex())
        {
            if(country == item)
            {
                return mobileCodes[index]
                break
            }
        }
        return ""
    }

    fun validateName(name: String): ValidationResult
    {
        if(name.isBlank())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Name cannot be empty"
            )
        }
        val containsOnlyLetters = name.all { it.isLetter() }
        if(!containsOnlyLetters)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Name cannot have any numbers"
            )
        }
        return ValidationResult(successful = true)
    }

    fun validateEmail(email: String): ValidationResult
    {
        if(email.isBlank())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Email cannot be empty"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "This is not a valid email"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    fun validatePassword(password: String):ValidationResult
    {
        if(password.length < 8)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Password needs to be at least 8 characters"
            )
        }
        val containsDigitsAndLetters = password.any { it.isDigit() } && password.any { it.isLetter() }
        if(!containsDigitsAndLetters)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "The password has to consist of letters and digits"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    fun validateConfirmPassword(password: String, confirmPassword:String):ValidationResult
    {
        if(password != confirmPassword)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "The password don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        if(phoneNumber.isBlank())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Mobile number cannot be empty"
            )
        }
        if(!Patterns.PHONE.matcher(phoneNumber).matches())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid phone number"
            )
        }
        return ValidationResult(successful = true)
    }

    fun validateNationalId(id: String): ValidationResult {
        if(id.isBlank())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "National ID cannot be empty"
            )
        }
        if(id.length != 14)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid National ID"
            )
        }
        return ValidationResult(successful = true)
    }

    fun validateAge(age: String): ValidationResult {
        if(age.isBlank())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Age cannot be empty"
            )
        }
        if(age.toInt() !in 1..149)
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid age"
            )
        }
        return ValidationResult(successful = true)
    }

    fun validateDropDown(selectedItem: String): ValidationResult {
        if(selectedItem.isBlank())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select an option"
            )
        }
        return ValidationResult(successful = true)
    }

    fun isFirstStepFormValid(purpose:Boolean, country: Boolean,mobile:Boolean,fName:Boolean,
                             lName:Boolean,email: Boolean,password:Boolean,confirmPassword:Boolean): Boolean
    {
        if(purpose && country && mobile && fName && lName && email && password && confirmPassword)
        {
            return true
        }
        return false
    }

    fun isSecondStepFormValid(governorate:Boolean, case: Boolean,natId:Boolean,age:Boolean): Boolean
    {
        if(governorate && case && natId && age)
        {
            return true
        }
        return false
    }

//    fun isFirstStepFormValid(purpose:String,purposeHelper:String,country: String,countryHelper:String,mobile:String,mobileHelper:String,fName:String,fNameHelper:String,
//                             lName:String,lNameHelper:String,email: String,emailHelper:String,password:String,passwordHelper:String,confirmPassword:String,confirmPasswordHelper:String): Boolean
//    {
//        return (purposeHelper.isBlank() && countryHelper.isBlank() && fNameHelper.isBlank() && lNameHelper.isBlank() && mobileHelper.isBlank() && emailHelper.isBlank()
//                    && passwordHelper.isBlank() && confirmPasswordHelper.isBlank())
//    }

    fun isSecondStepFormValid()
    {

    }

    fun onRegister(fName: String, lName: String, email: String,mobile: String, password: String, governorate: String, case: String, age: String, natId: String, purpose: Int)
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
            newUser.age = age.toInt()

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