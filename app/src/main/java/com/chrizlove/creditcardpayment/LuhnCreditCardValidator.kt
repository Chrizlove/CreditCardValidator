package com.chrizlove.creditcardpayment

object LuhnCreditCardValidator {

    fun creditCardLuhnValidation(cardNumber: String) : Boolean {

        val (digits, others) = cardNumber
            .filterNot(Char::isWhitespace)
            .partition(Char::isDigit)
        if (digits.length <= 1 || others.isNotEmpty()) {
            return false
        }
        val checksum = digits
            .map { it.toInt() - '0'.toInt() }
            .reversed()
            .mapIndexed { index, value ->
                if (index % 2 == 1 && value < 9) value * 2 % 9 else value
            }
            .sum()
        return checksum % 10 == 0
    }
}