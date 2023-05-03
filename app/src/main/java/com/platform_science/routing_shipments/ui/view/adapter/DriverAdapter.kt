package com.platform_science.routing_shipments.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.platform_science.routing_shipments.databinding.ItemDriverBinding
import com.platform_science.routing_shipments.domain.model.Driver
import java.util.logging.Logger

/**
 * Adapter to fill recycler view of drivers
 */
class DriverAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Driver, DriverAdapter.DriversViewHolder>(DriversDiffCallBack()) {
    private val log: Logger = Logger.getLogger(DriverAdapter::class.java.name)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversViewHolder {
        return DriversViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) {
        val driver = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(driver, position)
        }
        holder.bind(driver)
    }

    class DriversViewHolder private constructor(private val binding: ItemDriverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val log: Logger = Logger.getLogger(DriverAdapter::class.java.name)

        fun bind(driver: Driver) {
            binding.tvDriver.text = driver.driver
            binding.tvAddress.text = driver.address
        }

        companion object {
            fun from(parent: ViewGroup): DriversViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDriverBinding.inflate(layoutInflater, parent, false)
                return DriversViewHolder(binding)
            }
        }

    }

    class OnClickListener(val clickListener: (driver: Driver, position: Int) -> Unit) {
        fun onClick(driver: Driver, position: Int) = clickListener(driver, position)
    }

    class DriversDiffCallBack : DiffUtil.ItemCallback<Driver>() {
        override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem == newItem
        }

    }
}