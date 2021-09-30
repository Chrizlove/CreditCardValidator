package com.chrizlove.creditcardpayment

import android.util.Log
import java.util.*

object ExpiryCreditCardValidator {

     fun expiryValidator(expiryText: String): Int {

         //function returns 0 for invalid format
         //function returns 1 for expired date
         //function returns 2 for valid date
        val calender = Calendar.getInstance()
        val currentMonth = calender.get(Calendar.MONTH)
        val currentYear = calender.get(Calendar.YEAR)
         val expiryArray = expiryText.toCharArray()
         if(expiryArray.size!=5)
         {
             return 0
         }
         for(i in 0..4)
         {
             if(i==2)
             {
                 if(expiryArray.get(i)=='/')
                 {
                     continue
                 }
                 else{
                     return 0
                 }
             }
            else{
                 if(expiryArray.get(i).isDigit())
                 {
                     continue
                 }
                 else{
                     return 0
                 }
             }
         }
        val month = "${expiryText.get(0)}${expiryText.get(1)}".toInt()
         if(month>12 || month==0)
         {
             return 0
         }
        val year = "20${expiryText.get(3)}${expiryText.get(4)}".toInt()
        if(year < currentYear)
        {
            return 1
        }
        if(month < currentMonth && year == currentYear){
            return 1
        }
        return 2
    }
}