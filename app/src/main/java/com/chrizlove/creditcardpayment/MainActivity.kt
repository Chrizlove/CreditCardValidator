package com.chrizlove.creditcardpayment

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var cardNumberTypeValid=false
    private var cardNumberValid = false
    private var cardExpiryValid = false
    private var cardCVVValid = false
    private var cardFirstnameValid = false
    private var cardLastnameValid = false
    private val listOfPattern = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //adding the regex exp to for different cards
        addValidationPatterns()

        //handles ui of the cardExpiryEditText
        cardNumberHandler()

        //handles ui of the cardExpiryEditText
        cardExpiryHandler()

        //handles the submission of details
        onSubmit()
        }

    private fun onSubmit() {
        submitButton.setOnClickListener {

            //card number empty handler
            if(cardNumberEditText.text.isNullOrBlank())
            {
                cardNumberText.helperText = "Card number is required"
            }
            else{
                //luhn validation for the credit card number
                if(creditCardLuhnValidation(cardNumberEditText.text.toString()) && cardNumberTypeValid)
                {
                    cardNumberValid = true
                    cardNumberText.helperText = ""
                }
                else{
                    cardNumberText.helperText = "Card number is invalid"
                }
            }

            //card cvv validation
            if(cardCVVNumberEditText.text?.length ==3)
            {
                cardCVVValid=true
                cardCVVNumberText.helperText = ""
            }
            else{
                cardCVVNumberText.helperText = "CVV is invalid"
            }

            //card expiry empty handler
            if(cardExpiryNumberEditText.text.isNullOrBlank())
            {
                cardExpiryNumberText.helperText = "Expiry is required"
            }
            else{
                if(expiryValidator())
                {
                    cardExpiryNumberText.helperText = ""
                    cardExpiryValid=true
                }
                else {
                    cardExpiryNumberText.helperText = "Card is expired"
                }
            }

            //card expiry empty handler
            if(cardFirstNameNumberEditText.text.isNullOrBlank())
            {
                cardFirstNameNumberText.helperText = "Firstname is required"
            }
            else{
                cardFirstnameValid=true
                cardFirstNameNumberText.helperText = ""
            }

            //card expiry empty handler
            if(cardSecondNameEditText.text.isNullOrBlank())
            {
                cardSecondNameText.helperText = "Lastname is required"
            }
            else{
                cardLastnameValid=true
                cardSecondNameText.helperText = ""
            }

            if(cardNumberValid && cardExpiryValid && cardCVVValid && cardFirstnameValid && cardLastnameValid)
            {
                    //create a alert for successful payment
                    createAlert()
            }
        }
    }

    private fun expiryValidator(): Boolean {
    val calender = Calendar.getInstance()
        val expiry = cardExpiryNumberEditText.text.toString()
        val month = "${expiry.get(0)}${expiry.get(1)}"
        val year = "${expiry.get(3)}${expiry.get(4)}"
        return false
    }

    private fun createAlert() {
    val alertDialog = AlertDialog.Builder(this).setTitle("Payment Successful")
                                    .setPositiveButton("Ok", object: DialogInterface.OnClickListener{
                                        override fun onClick(p0: DialogInterface?, p1: Int) {
                                            p0?.dismiss()
                                        }
                                    }).create()
        alertDialog.show()
    }

    private fun creditCardLuhnValidation(cardNumber: String) : Boolean {
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

    private fun addValidationPatterns() {
        val ptVisa = "^4[0-9]{6,}$"
        listOfPattern.add(ptVisa)
        val ptMasterCard = "^5[1-5][0-9]{5,}$"
        listOfPattern.add(ptMasterCard)
        val ptAmeExp = "^3[47][0-9]{5,}$"
        listOfPattern.add(ptAmeExp)
        val ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$"
        listOfPattern.add(ptDiscover)
    }

    private fun cardExpiryHandler() {
        cardExpiryNumberEditText.doOnTextChanged { text, start, before, count ->
            if(start==1 && count ==1)
            {
                cardExpiryNumberEditText.setText("${text}/")
                cardExpiryNumberEditText.setSelection(3)
            }
            else if(start==3 && count==0)
            {
                val string = text.toString()
                val char1 = string.get(0)
                val char2 = string.get(1)
                cardExpiryNumberEditText.setText("${char1}${char2}")
                cardExpiryNumberEditText.setSelection(2)
            }
        }
    }

    private fun cardNumberHandler() {
        cardNumberEditText.doOnTextChanged { text, start, before, count ->
            val cardNumberString = text.toString()
            for(exp in listOfPattern)
            {
                if(cardNumberString.matches(exp.toRegex()))
                {
                    if(exp == "^4[0-9]{6,}$")
                    {
                        Log.d("cardtype","visa")
                        cardTypeImage.setImageDrawable(getDrawable(R.drawable.visalogo))
                    }
                    else if(exp == "^5[1-5][0-9]{5,}$"){
                        Log.d("cardtype","master")
                        cardTypeImage.setImageDrawable(getDrawable(R.drawable.mastercardlogo))
                    }
                    else if(exp == "^3[47][0-9]{5,}$"){
                        Log.d("cardtype","amexp")
                        cardTypeImage.setImageDrawable(getDrawable(R.drawable.americanexpresslogo))
                    }
                    else if(exp == "^6(?:011|5[0-9]{2})[0-9]{3,}$"){
                        Log.d("cardtype","discover")
                        cardTypeImage.setImageDrawable(getDrawable(R.drawable.discoverlogo))
                    }
                    cardNumberTypeValid=true
                    break
                }
                else{
                    cardNumberTypeValid = false
                    Log.d("cardtype","none")
                    cardTypeImage.setImageDrawable(getDrawable(R.drawable.ic_baseline_credit_card_24))
                }
            }
        }
    }
}
