package com.platform_science.routing_shipments.ui.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.platform_science.routing_shipments.common.Status
import com.platform_science.routing_shipments.databinding.ItemAddressBinding
import com.platform_science.routing_shipments.domain.model.Address
import java.util.logging.Logger

/**
 * Adapter to fill recycler view of address
 */
class AddressAdapter() :
    ListAdapter<Address, AddressAdapter.ShipmentsViewHolder>(ShipmentsDiffCallBack()) {
    private val log: Logger = Logger.getLogger(AddressAdapter::class.java.name)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipmentsViewHolder {
        return ShipmentsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShipmentsViewHolder, position: Int) {
        val address = getItem(position)
        holder.bind(address)
    }

    class ShipmentsViewHolder private constructor(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val log: Logger = Logger.getLogger(AddressAdapter::class.java.name)

        fun bind(address: Address) {
            val color = if(address.status == Status.AVAILABLE.ordinal) Color.BLACK else Color.RED
            binding.tvAddress.setTextColor(color)
            binding.tvAddress.text = address.address
        }

        companion object {
            fun from(parent: ViewGroup): ShipmentsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAddressBinding.inflate(layoutInflater, parent, false)
                return ShipmentsViewHolder(binding)
            }
        }

    }

    class ShipmentsDiffCallBack : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }

    }
}