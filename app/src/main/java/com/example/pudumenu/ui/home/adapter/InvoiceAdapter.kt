package com.example.pudumenu.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pudumenu.R
import com.example.pudumenu.ui.home.model.Invoice
import java.text.NumberFormat
import java.util.Locale

class InvoiceAdapter(private val invoices: List<Invoice>) : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_summary, parent, false)
        return InvoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val invoice = invoices[position]
        holder.bind(invoice)
    }

    override fun getItemCount(): Int = invoices.size

    class InvoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val invoiceNumber: TextView = itemView.findViewById(R.id.invoice_number_value)
        private val productListContainer: LinearLayout = itemView.findViewById(R.id.product_list_container)
        private val subtotal: TextView = itemView.findViewById(R.id.subtotal_value)
        private val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        fun bind(invoice: Invoice) {
            invoiceNumber.text = "#${invoice.invoiceNumber}"
            subtotal.text = numberFormat.format(invoice.subtotal)

            productListContainer.removeAllViews()


            for (product in invoice.productList) {
                val productView = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.item_product_detail, productListContainer, false)

                val productName: TextView = productView.findViewById(R.id.product_name)
                val productQuantity: TextView = productView.findViewById(R.id.product_quantity)
                val productPrice: TextView = productView.findViewById(R.id.product_price)

                productName.text = product.name
                productQuantity.text = "x${product.quantity}"
                productPrice.text = numberFormat.format(product.price)

                productListContainer.addView(productView)
            }
        }
    }
}
