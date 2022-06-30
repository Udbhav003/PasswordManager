package com.blackend.passwordmanager.others

sealed class InvalidInput(val msg: String) {
    class InvalidTitle(msg: String) : InvalidInput(msg)
    class InvalidUserName(msg: String) : InvalidInput(msg)
    class InvalidPassword(msg: String) : InvalidInput(msg)
    class None(msg: String) : InvalidInput(msg)
}
