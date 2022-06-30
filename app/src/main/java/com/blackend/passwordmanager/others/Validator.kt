package com.blackend.passwordmanager.others

import com.blackend.passwordmanager.data.models.PasswordCriteria
import java.util.regex.Pattern

object Validator {

    /**
     * Checks if the title is valid.
     * @param input - String
     * @return - true if the name is valid.
     */
    fun isValidTitle(input: String): Boolean {
        return (input.trim().length > 1)
    }

    /**
     * Checks if the username is valid.
     * @param input - String
     * @return - true if the username is valid.
     */
    fun isValidUserName(input: String): Boolean {
        return (input.trim().length > 2)
    }

    fun isValidPassword(input: String): Boolean {
        return (input.trim().length > 3)
    }

    /**
     * Checks if the password is valid as per the following password policy.
     * 1. Password should be minimum minimum 8 characters long.
     * 2. Password should contain at least one number.
     * 3. Password should contain both uppercase and lowercase letters
     * 4. Password should contain at least one special character.
     * Allowed special characters: "~!@#$€%^&*()-_=+|/,."';:{}[]<>?"
     * 5. Password should contain at least 5 unique characters
     *
     * @param input - String
     * @return - true if the password is valid as per the password policy.
     */
    fun matchCriteria(
        input: String,
        criteriaList: List<PasswordCriteria>
    ): Pair<Boolean, List<PasswordCriteria>> {

        // Password should be minimum minimum 8 characters long
        val criteria1 =
            criteriaList.first { it.criteriaId == InvalidPasswordCodes.LENGTH_LESS_THAN_EIGHT }
        criteria1.criteriaMet = input.length >= 8

        // Password should contain at least one number
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(input)
        val criteria2 =
            criteriaList.first { it.criteriaId == InvalidPasswordCodes.DOES_NOT_CONTAIN_A_NUMBER }
        criteria2.criteriaMet = matcher.matches()

        // Password should contain both uppercase and lowercase letters
        val exp1 = ".*[A-Z].*"
        val pattern1 = Pattern.compile(exp1)
        val matcher1 = pattern1.matcher(input)

        val exp2 = ".*[a-z].*"
        val pattern2 = Pattern.compile(exp2)
        val matcher2 = pattern2.matcher(input)

        val criteria3 =
            criteriaList.first { it.criteriaId == InvalidPasswordCodes.DOES_NOT_CONTAIN_BOTH_UPPERCASE_AND_LOWERCASE_CHAR }

        criteria3.criteriaMet = matcher1.matches() && matcher2.matches()

        // Password should contain at least one special character
        exp = ".*[~!@#\$€%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(input)
        val criteria4 =
            criteriaList.first { it.criteriaId == InvalidPasswordCodes.DOES_NOT_HAVE_A_SPECIAL_CHAR }
        criteria4.criteriaMet = matcher.matches()

        // Password should contain at least 5 unique characters
        val criteria5 =
            criteriaList.first { it.criteriaId == InvalidPasswordCodes.HAS_LESS_THAN_FIVE_UNIQUE_LETTERS }
        criteria5.criteriaMet = input.toSet().size >= 5


        return Pair(criteriaList.all { it.criteriaMet }, criteriaList)
    }


    /**
     * Checks if the form is valid.
     * @param title - String
     * @param userName - String
     * @param password - String
     * @return - Pair containing validity and error source(if any).
     */
    fun isFormCorrectlyFilled(
        title: String,
        userName: String,
        password: String
    ): Pair<Boolean, InvalidInput> {
        if (!isValidTitle(title)) {
            return Pair(
                false,
                InvalidInput.InvalidTitle(InvalidPasswordCodes.TITLE_INPUT_ERROR_MSG)
            )
        }

        if (!isValidUserName(userName)) {
            return Pair(
                false,
                InvalidInput.InvalidUserName(InvalidPasswordCodes.USERNAME_INPUT_ERROR_MSG)
            )
        }

        if (!isValidPassword(password)) {
            return Pair(
                false,
                InvalidInput.InvalidPassword(InvalidPasswordCodes.PASSWORD_INPUT_ERROR_MSG)
            )
        }

        return Pair(true, InvalidInput.None("Success"))

    }
}