package com.example.bb.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
