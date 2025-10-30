package com.example.pudumenu.ui.home.model

data class Invoice(
    val invoiceNumber: String,
    val productList: List<ProductItem>,
    val subtotal: Double
)
