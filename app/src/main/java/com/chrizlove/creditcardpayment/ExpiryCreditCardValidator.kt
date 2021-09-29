package com.chrizlove.creditcardpayment

import java.util.*

object ExpiryCreditCardValidator {

     fun expiryValidator(expiryText: String): Boolean {

        val calender = Calendar.getInstance()
        val currentMonth = calender.get(Calendar.MONTH)
        val currentYear = calender.get(Calendar.YEAR)
        val month = "${expiryText.get(0)}${expiryText.get(1)}".toInt()
        val year = "20${expiryText.get(3)}${expiryText.get(4)}".toInt()
        if(year < currentYear)
        {
            return false
        }
        if(month < currentMonth && year == currentYear){
            return false
        }
        return true
    }
}