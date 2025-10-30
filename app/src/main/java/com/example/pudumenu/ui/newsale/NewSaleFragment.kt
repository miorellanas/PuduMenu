package com.example.pudumenu.ui.newsale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.example.pudumenu.R
import com.example.pudumenu.data.Product
import com.example.pudumenu.data.ProductRepository
import com.example.pudumenu.databinding.FragmentNewSaleBinding
import com.example.pudumenu.databinding.ItemSaleProductBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.text.NumberFormat
import java.util.Locale

class NewSaleFragment : Fragment() {

    private var _binding: FragmentNewSaleBinding? = null
    private val binding get() = _binding!!

    private val currentSaleProducts = mutableListOf<Product>()
    private val productRepository = ProductRepository

    private val scanLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(ScanContract()) { result ->
        result.contents?.let {
            binding.productCodeEditText.setText(it)
            binding.addProductButton.performClick()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupDropdowns()
        setupClickListeners()
    }

    private fun setupDropdowns() {
        val documentTypes = resources.getStringArray(R.array.document_types)
        val documentTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, documentTypes)
        (binding.documentTypeMenu.editText as? AutoCompleteTextView)?.setAdapter(documentTypeAdapter)

        val paymentMethods = resources.getStringArray(R.array.payment_methods)
        val paymentMethodAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, paymentMethods)
        (binding.paymentMethodMenu.editText as? AutoCompleteTextView)?.setAdapter(paymentMethodAdapter)
    }

    private fun setupClickListeners() {
        binding.addProductButton.setOnClickListener {
            addProduct()
        }

        binding.scanButton.setOnClickListener {
            startScanner()
        }

        binding.approveSaleButton.setOnClickListener {
            if (currentSaleProducts.isNotEmpty()) {
                Toast.makeText(requireContext(), "Venta Aprobada", Toast.LENGTH_SHORT).show()
                currentSaleProducts.clear()
                binding.saleItemsContainer.removeAllViews()
                updateSubtotal()
            } else {
                Toast.makeText(requireContext(), "No hay productos en la venta", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt("Escanear un código de producto")
        options.setCameraId(0)
        options.setBeepEnabled(true)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        scanLauncher.launch(options)
    }

    private fun addProduct() {
        val productCode = binding.productCodeEditText.text.toString()
        if (productCode.isBlank()) {
            binding.productInputLayout.error = "Ingrese un código"
            return
        }

        val product = productRepository.findProductByCode(productCode)

        if (product != null) {
            currentSaleProducts.add(product)
            addProductToView(product)
            updateSubtotal()
            binding.productCodeEditText.text?.clear()
            binding.productInputLayout.error = null
        } else {
            binding.productInputLayout.error = "Producto no encontrado"
        }
    }

    private fun addProductToView(product: Product) {
        val inflater = LayoutInflater.from(context)
        val productItemBinding = ItemSaleProductBinding.inflate(inflater, binding.saleItemsContainer, false)
        productItemBinding.productNameTextView.text = product.name
        productItemBinding.productPriceTextView.text = formatPrice(product.price)
        binding.saleItemsContainer.addView(productItemBinding.root)
    }

    private fun updateSubtotal() {
        val subtotal = currentSaleProducts.sumOf { it.price }
        binding.subtotalValue.text = formatPrice(subtotal)
    }

    private fun formatPrice(price: Double): String {
        return NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(price)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}