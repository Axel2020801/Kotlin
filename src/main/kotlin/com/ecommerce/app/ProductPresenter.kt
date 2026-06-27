package com.ecommerce.app

// Interfaz para simular la vista (UI)
interface ProductView {
    fun showProductList(products: List<Product>)
    fun showEmptyState()
}

// Presenter con la lógica requerida por la consigna
class ProductPresenter(private val view: ProductView) {

    fun displayProducts(products: List<Product>?) {
        if (products.isNullOrEmpty()) {
            view.showEmptyState()
        } else {
            view.showProductList(products)
        }
    }
}