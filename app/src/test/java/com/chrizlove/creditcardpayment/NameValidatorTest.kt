package com.chrizlove.creditcardpayment

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NameValidatorTest
{
    @Test
    fun whenNameIsValid()
    {
        val name="John"
        val result = NameValidator.validateName(name)
        assertEquals(false,result)
    }

    @Test
    fun whenNameIsValidAlternative()
    {
        val name="John Doe"
        val result = NameValidator.validateName(name)
        assertEquals(false,result)
    }

    @Test
    fun whenNameIsInValid()
    {
        val name="John 77#$%"
        val result = NameValidator.validateName(name)
        assertEquals(true,result)
    }

    @Test
    fun whenNameIsEmpty()
    {
        val name=""
        val result = NameValidator.validateName(name)
        assertEquals(false,result)
    }
}