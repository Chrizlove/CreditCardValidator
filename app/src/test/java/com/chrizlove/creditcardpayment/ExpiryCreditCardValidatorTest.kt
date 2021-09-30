package com.chrizlove.creditcardpayment

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExpiryCreditCardValidatorTest{

    @Test
    fun whenInputIsInvalid()
    {
        val expiryText = "00//2"
        val result = ExpiryCreditCardValidator.expiryValidator(expiryText)
        assertEquals(0,result)
    }

    @Test
    fun whenInputIsInvalid2()
    {
        val expiryText = "17/62"
        val result = ExpiryCreditCardValidator.expiryValidator(expiryText)
        assertEquals(0,result)
    }

    @Test
    fun whenInputIsInvalid3()
    {
        val expiryText = ""
        val result = ExpiryCreditCardValidator.expiryValidator(expiryText)
        assertEquals(0,result)
    }

    @Test
    fun whenInputIsInvalid4()
    {
        val expiryText = "108/24"
        val result = ExpiryCreditCardValidator.expiryValidator(expiryText)
        assertEquals(0,result)
    }

    @Test
    fun whenInputIsInvalid5()
    {
        val expiryText = "1a/24"
        val result = ExpiryCreditCardValidator.expiryValidator(expiryText)
        assertEquals(0,result)
    }

    @Test
    fun whenInputIsInvalid6()
    {
        val expiryText = "10&24"
        val result = ExpiryCreditCardValidator.expiryValidator(expiryText)
        assertEquals(0,result)
    }

    @Test
    fun whenInputIsExpired()
    {
        val expiryText = "11/10"
        val result = ExpiryCreditCardValidator.expiryValidator(expiryText)
        assertEquals(1,result)
    }

    @Test
    fun whenInputIsValid()
    {
        val expiryText = "08/26"
        val result = ExpiryCreditCardValidator.expiryValidator(expiryText)
        assertEquals(2,result)
    }
}