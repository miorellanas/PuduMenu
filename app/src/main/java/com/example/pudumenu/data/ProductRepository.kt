package com.example.pudumenu.data

object ProductRepository {

    private val products = listOf(
        Product(code = "P001", name = "Producto 1", price = 1000.0),
        Product(code = "P002", name = "Producto 2", price = 1500.0),
        Product(code = "P003", name = "Producto 3", price = 2000.0)
    )

    fun findProductByCode(code: String): Product? {
        return products.find { it.code.equals(code, ignoreCase = true) }
    }
}
