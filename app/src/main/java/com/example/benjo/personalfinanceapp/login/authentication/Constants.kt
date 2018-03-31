package com.example.benjo.personalfinanceapp.login.authentication


class Constants {
    companion object {
        val LOGIN: String = "LOGIN_FRAGMENT"
        val REGISTER: String = "REGISTER_FRAGMENT"
        val REGISTER_UNSUCCESSFUL: String = "Username/password must be at least 3 characters long"
        val REGISTER_SUCCESS: String = "User is now registered"
        val ALREADY_REGISTERED: String = "That user is already Registered"
        val LOGIN_UNSUCCESSFUL: String = "Login unsuccessful"
        val USER: String = "user"
        val SUMMARY: String = "SUMMARY_FRAGMENT"
        val INCOME: String = "INCOME_FRAGMENT"
        val EXPENDITURE: String = "EXPENDITURE_FRAGMENT"
        val DATE_PICKER: String = "DATE_PICKER_FRAGMENT"
        val INCOME_TRANSACTION: String = "INCOME_TRANSACTION"
        val DIALOG_INCOME: String = "DIALOG_INCOME_CATEGORIES"
        val DIALOG_EXPENDITURE: String = "DIALOG_EXPENDITURE_CATEGORIES"
        val INCOME_CATEGORIES: Int = 1
        val EXPENDITURE_CATEGORIES: Int = 2
        val EXPENDITURE_TRANSACTION: String = "EXPENDITURE_TRANSACTION"
        val CONTAINER: String = "CONTAINER_FRAGMENT"
        val MULTIPLE_DATE_PICKER: String = "MULTIPLE_DATE_PICKER_BORAX12"



        // DATABASE CONSTANTS
        val COLUMN_ID = "id"
        val COLUMN_ITEM_INDEX = "incId"
        val COLUMN_TITLE = "title"
        val COLUMN_CATEGORY = "category"
        val COLUMN_AMOUNT = "amount"
        val COLUMN_DATE = "date"
        val COLUMN_IMG = "image"
        val COLUMN_BARCODE = "barcode"


        fun DATABASE_CREATE(TABLE_NAME: String): String {
            return "create table " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ITEM_INDEX + " INTEGER, " +
                    COLUMN_TITLE + " text, " +
                    COLUMN_CATEGORY + " text, " +
                    COLUMN_AMOUNT + " text, " +
                    COLUMN_DATE + " text, " +
                    COLUMN_IMG + " INTEGER, " +
                    COLUMN_BARCODE + " text);"
        }

        fun welcomeMsg(username: String): String? {
            return "Hey $username.\n This is a summary of your current financial situation."
        }


    }
}