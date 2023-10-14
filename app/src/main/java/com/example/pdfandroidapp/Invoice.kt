package com.example.pdfandroidapp



data class Invoice(
    val number: Long,
    val price: Float,
    val link: String,
    val date: String,
    val from: PersonalInfo,
    val to: PersonalInfo,
    val products: List<Products>,
    val signatureUrl: String? = null
)

data class PersonalInfo(
    val name: String,
    val address: String
)


data class Products(
    val description: String,
    val rate: Float,
    val quantity: Int
)
