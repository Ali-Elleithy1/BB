package com.example.bb

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

class LanguageManager(val ctx: Context) {

    fun updateResource(code: String)
    {
        val locale = Locale(code)
        Locale.setDefault(locale)
        val resources: Resources = ctx.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration,resources.displayMetrics)
    }

}