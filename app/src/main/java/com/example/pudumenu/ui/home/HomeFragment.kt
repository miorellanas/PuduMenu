package com.example.pudumenu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pudumenu.R
import com.example.pudumenu.databinding.FragmentHomeBinding
import com.example.pudumenu.ui.home.adapter.InvoiceAdapter
import com.example.pudumenu.ui.home.model.Invoice
import com.example.pudumenu.ui.home.model.ProductItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupInvoiceList()

        binding.buttonNewSale.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_new_sale)
        }
        return root
    }

    private fun setupInvoiceList() {
        val sampleInvoices = createSampleData()
        val invoiceAdapter = InvoiceAdapter(sampleInvoices)

        binding.recyclerViewInvoices.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = invoiceAdapter
        }
    }

    private fun createSampleData(): List<Invoice> {
        val product1 = ProductItem("Producto A", "PA001", 1, 2500.0)
        val product2 = ProductItem("Producto B", "PB002", 3, 9000.0)
        val product3 = ProductItem("Producto C", "PC003", 2, 5000.0)

        val invoice1 = Invoice("1634", listOf(product1, product2), product1.price * product1.quantity + product2.price * product2.quantity)
        val invoice2 = Invoice("1635", listOf(product3), product3.price * product3.quantity)
        val invoice3 = Invoice("1636", listOf(product1), product1.price * product1.quantity + product3.price * product3.quantity)
        val invoice4 = Invoice("1637", listOf(product1, product3), product1.price * product1.quantity + product3.price * product3.quantity)
        val invoice5 = Invoice("1638", listOf(product2, product3), product1.price * product1.quantity + product3.price * product3.quantity)
        val invoice6 = Invoice("1639", listOf(product1, product3, product2), product1.price * product1.quantity + product3.price * product3.quantity)


        return listOf(invoice1, invoice2, invoice3, invoice4, invoice5, invoice6)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
