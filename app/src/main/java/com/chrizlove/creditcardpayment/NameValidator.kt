package com.chrizlove.creditcardpayment

import java.util.regex.Pattern

object NameValidator {

     fun validateName(name: String): Boolean {
        val patternName = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE)
        val matcher = patternName.matcher(name)
        return matcher.find()
    }
}