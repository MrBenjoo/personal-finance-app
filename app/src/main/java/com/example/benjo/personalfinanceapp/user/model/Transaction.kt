package com.example.benjo.personalfinanceapp.user.model


data class Transaction(var title: String, var amount: String, var category: String, var date: String, var imgRes: Int) {

    fun isValid(): Boolean {
        return title.isNotEmpty() &&
                amount.isNotEmpty() &&
                category.isNotEmpty() &&
                date.isNotEmpty() &&
                imgRes != 0
    }

}