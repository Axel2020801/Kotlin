package com.ecommerce.app

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ProductPresenterTest {

    @Mock
    private lateinit var mockView: ProductView
    private lateinit var presenter: ProductPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter = ProductPresenter(mockView)
    }

    // TEST 1: Dado el listado de productos, validar que la información se pueda mostrar en pantalla.
    @Test
    fun givenProductList_whenDisplayProducts_thenShowsProductsOnScreen() {
        // Given: Armamos la lista simulando el JSON provisto
        val productList = listOf(
            Product(1, "iPhone 13", "The latest iPhone from Apple", 999.99, "USD", true),
            Product(2, "Samsung Galaxy S21", "The latest Samsung phone", 899.99, "USD", true),
            Product(3, "Google Pixel 6", "The latest Google phone", 799.99, "USD", false)
        )

        // When: Ejecutamos el método
        presenter.displayProducts(productList)

        // Then: Verificamos que se llame a la UI para mostrar los datos
        verify(mockView, times(1)).showProductList(productList)
        verify(mockView, never()).showEmptyState()
    }

    // TEST 2: Cuando el listado esté vacío, validar que no se pueda mostrar información en pantalla.
    @Test
    fun givenEmptyList_whenDisplayProducts_thenShowsEmptyStateAndNoProducts() {
        // Given: Una lista vacía
        val emptyList = emptyList<Product>()

        // When: Ejecutamos el método
        presenter.displayProducts(emptyList)

        // Then: Verificamos que se active el estado vacío y no se muestre nada
        verify(mockView, times(1)).showEmptyState()
        verify(mockView, never()).showProductList(anyList())
    }
}