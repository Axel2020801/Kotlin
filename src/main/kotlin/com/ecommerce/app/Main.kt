package com.ecommerce.app

fun main() {
    println("--- Iniciando petición a la API de E-Commerce ---")
    val repository = ProductRepository()

    repository.fetchProducts(
        onSuccess = { products ->
            println("\n📦 LISTADO DE PRODUCTOS DISPONIBLES:")
            println("----------------------------------------")
            for (product in products) {
                val stockStatus = if (product.inStock) "Disponible" else "Sin Stock"
                println("• Producto: ${product.name}")
                println("  Precio: ${product.currency} ${product.price}")
                println("  Estado: $stockStatus")
                println("----------------------------------------")
            }
        },
        onError = { error ->
            println("\n❌ Error al recuperar los datos de la API:")
            println(error.message)
        }
    )
    Thread.sleep(4000)
}