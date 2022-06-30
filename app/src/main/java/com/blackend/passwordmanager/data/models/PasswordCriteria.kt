package com.blackend.passwordmanager.data.models


data class PasswordCriteria(
    val criteriaId: Int,
    val criteria: String,
    var criteriaMet: Boolean
)
