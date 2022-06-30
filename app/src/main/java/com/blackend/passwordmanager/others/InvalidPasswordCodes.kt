package com.blackend.passwordmanager.others

object InvalidPasswordCodes {
    const val LENGTH_LESS_THAN_EIGHT = 101
    const val DOES_NOT_CONTAIN_A_NUMBER = 102
    const val DOES_NOT_CONTAIN_BOTH_UPPERCASE_AND_LOWERCASE_CHAR = 103
    const val DOES_NOT_HAVE_A_SPECIAL_CHAR = 104
    const val HAS_LESS_THAN_FIVE_UNIQUE_LETTERS = 105

    const val TITLE_INPUT_ERROR_MSG = "Title too short!"
    const val USERNAME_INPUT_ERROR_MSG = "Username too short!"
    const val PASSWORD_INPUT_ERROR_MSG = "Should have a minimum of 4 letters"

}