package com.chrizlove.creditcardpayment

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LuhnCreditCardValidatorTest{

    @Test
    fun whenInputIsValid()
    {
        val cardNumber = "6011111111111117"
        val result = LuhnCreditCardValidator.creditCardLuhnValidation(cardNumber)
        assertEquals(true,result)
    }

    @Test
    fun whenInputIsInvalid()
    {
        val cardNumber = "4388576018402626"
        val result = LuhnCreditCardValidator.creditCardLuhnValidation(cardNumber)
        assertEquals(false,result)
    }

    @Test
    fun whenInputIs0()
    {
        val cardNumber = "0"
        val result = LuhnCreditCardValidator.creditCardLuhnValidation(cardNumber)
        assertEquals(false,result)
    }

    @Test
    fun whenInputIsEmpty()
    {
        val cardNumber = ""
        val result = LuhnCreditCardValidator.creditCardLuhnValidation(cardNumber)
        assertEquals(false,result)
    }
}