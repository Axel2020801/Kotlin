package com.ecommerce.app

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ProductRepository {

    private val apiUrl = "https://jsonkeeper.com/b/MX0A"

    fun fetchProducts(onSuccess: (List<Product>) -> Unit, onError: (Exception) -> Unit) {
        thread {
            try {
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var inputLine: String?

                    while (reader.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    reader.close()

                    val productList = parseJson(response.toString())
                    onSuccess(productList)
                } else {
                    onError(Exception("Error en la conexión. Código de respuesta: $responseCode"))
                }
                connection.disconnect()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun parseJson(jsonString: String): List<Product> {
        val products = mutableListOf<Product>()
        val mainObject = JSONObject(jsonString)
        val jsonArray = mainObject.getJSONArray("products")

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val product = Product(
                id = item.getInt("id"),
                name = item.getString("name"),
                description = item.getString("description"),
                price = item.getDouble("price"),
                currency = item.getString("currency"),
                inStock = item.getBoolean("in_stock")
            )
            products.add(product)
        }
        return products
    }
}