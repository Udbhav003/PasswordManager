package com.blackend.passwordmanager.others

import java.security.SecureRandom

object PasswordManager {

    private const val letters: String = "abcdefghijklmnopqrstuvwxyz"
    private const val uppercaseLetters: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val numbers: String = "0123456789"
    private const val special: String = "@#=+!£$%&?"
    private const val maxPasswordLength: Float = 20F //Max password length that my app creates
    private const val maxPasswordFactor: Float =
        10F //Max password factor based on chars inside password


    /**
     * Generate a random password
     * @param isWithLetters Boolean value to specify if the password must contain letters
     * @param isWithUppercase Boolean value to specify if the password must contain uppercase letters
     * @param isWithNumbers Boolean value to specify if the password must contain numbers
     * @param isWithSpecial Boolean value to specify if the password must contain special chars
     * @param length Int value with the length of the password
     * @return the new password.
     */
    fun generatePassword(
        isWithLetters: Boolean,
        isWithUppercase: Boolean,
        isWithNumbers: Boolean,
        isWithSpecial: Boolean,
        length: Int
    ): String {

        var result = ""
        var i = 0

        if (isWithLetters) {
            result += this.letters
        }
        if (isWithUppercase) {
            result += this.uppercaseLetters
        }
        if (isWithNumbers) {
            result += this.numbers
        }
        if (isWithSpecial) {
            result += this.special
        }

        val rnd = SecureRandom.getInstance("SHA1PRNG")
        val sb = StringBuilder(length)

        while (i < length) {
            val randomInt: Int = rnd.nextInt(result.length)
            sb.append(result[randomInt])
            i++
        }

        return sb.toString()
    }

    /**
     * Evaluate a random password
     * @param passwordToTest String with the password to test
     * @return a number from 0 to 1, 0 is a very bad password and 1 is a perfect password
     */
    fun evaluatePassword(passwordToTest: String): Boolean {

        val condition1 = passwordToTest.any { it.isDigit() }
        val condition2 = passwordToTest.any { special.contains(it) }
        val condition3 = passwordToTest.any { it.isUpperCase() }
        val condition4 = passwordToTest.any { it.isLowerCase() }

        return condition1 && condition2 && condition3 && condition4
    }

}