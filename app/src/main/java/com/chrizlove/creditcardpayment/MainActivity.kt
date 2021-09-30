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
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private var cardNumberTypeValid=false
    private var cardNumberValid = false
    private var cardExpiryValid = false
    private var cardCVVValid = false
    private var cardFirstnameValid = false
    private var cardLastnameValid = false
    private var cardType="none"
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

            //card number on submit handler and validation
            cardNumberOnSubmitHandler()

            //card cvv on submit validation
            cardCVVOnSubmitHandler()

            //card expiry on submit handler and validation
            cardExpiryOnSubmitHandler()

            //firstname on submit handler and validation
            cardFirstnameOnSubmitHandler()

            //lastname on submit handler and validation
            cardLastnameOnSubmitHandler()

            //final check for all above validation
            if(cardNumberValid && cardExpiryValid && cardCVVValid && cardFirstnameValid && cardLastnameValid)
            {
                    //create a alert for successful payment
                    createAlert()
            }
        }
    }

    private fun cardLastnameOnSubmitHandler() {

        if(cardSecondNameEditText.text.isNullOrBlank())
        {
            cardSecondNameText.helperText = "Lastname is required"
        }
        else{
            //name validation
            if(!NameValidator.validateName(cardSecondNameEditText.text.toString()))
            {
                cardLastnameValid=true
                cardSecondNameText.helperText = ""
            }
            else{
                cardSecondNameText.helperText = "Only alphabet and spaces allowed!"
            }
        }
    }

    private fun cardFirstnameOnSubmitHandler() {
        if(cardFirstNameNumberEditText.text.isNullOrBlank())
        {
            cardFirstNameNumberText.helperText = "Firstname is required"
        }
        else{
            //name validation
            if(!NameValidator.validateName(cardFirstNameNumberEditText.text.toString()))
            {
                cardFirstnameValid=true
                cardFirstNameNumberText.helperText = ""
            }
            else{
                cardFirstNameNumberText.helperText = "Only alphabet and spaces allowed!"
            }
        }
    }

    private fun cardExpiryOnSubmitHandler() {
        if(cardExpiryNumberEditText.text.isNullOrBlank())
        {
            cardExpiryNumberText.helperText = "Expiry is required"
        }
        else if(cardExpiryNumberEditText.text!!.length !=5)
        {
            cardExpiryNumberText.helperText = "Expiry is invalid"
        }
        else{
            //expiry validation
                val expiryResult = ExpiryCreditCardValidator.expiryValidator(cardExpiryNumberEditText.text.toString())
            if(expiryResult==2)
            {
                cardExpiryNumberText.helperText = ""
                cardExpiryValid=true
            }
            else if(expiryResult==1){
                cardExpiryNumberText.helperText = "Card is expired"
            }
            else {
                cardExpiryNumberText.helperText = "Expiry is invalid"
            }
        }
    }

    private fun cardCVVOnSubmitHandler() {
        if(cardType=="none")
        {
            cardCVVNumberText.helperText = "Enter valid card number"
        }
        else if(cardType!="americanexp")
        {
            if(cardCVVNumberEditText.text?.length ==3)
            {
                cardCVVValid=true
                cardCVVNumberText.helperText = ""
            }
            else{
                cardCVVNumberText.helperText = "CVV is invalid"
            }
        }
        else{
            if(cardCVVNumberEditText.text?.length ==4)
            {
                cardCVVValid=true
                cardCVVNumberText.helperText = ""
            }
            else{
                cardCVVNumberText.helperText = "CVV is invalid"
            }
        }
    }

    private fun cardNumberOnSubmitHandler() {

        if(cardNumberEditText.text.isNullOrBlank())
        {
            cardNumberText.helperText = "Card number is required"
        }
        else{

            //luhn validation for the credit card number
             if(LuhnCreditCardValidator.creditCardLuhnValidation(cardNumberEditText.text.toString()) && cardNumberTypeValid)
            {
                cardNumberValid = true
                cardNumberText.helperText = ""
            }
            else{
                cardNumberText.helperText = "Card number is invalid"
            }
        }
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
            //code for / handler
            if(start==1 && count ==1)
            {
                cardExpiryNumberEditText.setText("${text}/")
                cardExpiryNumberEditText.setSelection(3)
            }

            else if(start==3 && count==0)
            {
                val string = text.toString()
                val char1 = string.get(0)
                if(char1.digitToInt() == 0)
                {
                    cardExpiryNumberEditText.setText("")
                    cardExpiryNumberEditText.setSelection(0)
                }
                else{
                    cardExpiryNumberEditText.setText("${char1}")
                    cardExpiryNumberEditText.setSelection(1)
                }
            }

            else if(start==2 && count==0)
            {
                val string = text.toString()
                val char1 = string.get(0)
                if(char1.digitToInt() == 0)
                {
                    cardExpiryNumberEditText.setText("")
                    cardExpiryNumberEditText.setSelection(0)
                }
                else{
                    cardExpiryNumberEditText.setText("${char1}")
                    cardExpiryNumberEditText.setSelection(1)
                }
            }

            //code of input handler
            if(count==1 && start ==0)
            {
                val string = text.toString()
                if(string.get(0).digitToInt()!=0 && string.get(0).digitToInt() >=2)
                {
                    cardExpiryNumberEditText.setText("0${text}/")
                    cardExpiryNumberEditText.setSelection(3)
                }
            }

            //code for 0 month handler
            if(count==1 && start ==0)
            {
                val string = text.toString()
                if(string.get(0).digitToInt()==0)
                {
                    cardExpiryNumberEditText.setText("")
                    cardExpiryNumberEditText.setSelection(0)
                }
            }

            //code for month above 12 handler
            if(count==1 && start==1)
            {
                val string = text.toString()
                if(string.get(1).digitToInt()>=3)
                {
                    if(string.get(0).digitToInt()!=0)
                    {
                        cardExpiryNumberEditText.setText("${string.get(0)}")
                        cardExpiryNumberEditText.setSelection(1)
                    }
                }
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
                        cardTypeImage.setImageDrawable(getDrawable(R.drawable.visalogo))
                        cardType="visa"
                    }
                    else if(exp == "^5[1-5][0-9]{5,}$"){
                        cardTypeImage.setImageDrawable(getDrawable(R.drawable.mastercardlogo))
                        cardType="mastercard"
                    }
                    else if(exp == "^3[47][0-9]{5,}$"){
                        cardTypeImage.setImageDrawable(getDrawable(R.drawable.americanexpresslogo))
                        cardType="americanexp"
                    }
                    else if(exp == "^6(?:011|5[0-9]{2})[0-9]{3,}$"){
                        cardTypeImage.setImageDrawable(getDrawable(R.drawable.discoverlogo))
                        cardType="discover"
                    }
                    cardNumberTypeValid=true
                    break
                }
                else{
                    cardNumberTypeValid = false
                    cardType="none"
                    cardTypeImage.setImageDrawable(getDrawable(R.drawable.ic_baseline_credit_card_24))
                }
            }
        }
    }
}
