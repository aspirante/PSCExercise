package com.platform_science.routing_shipments.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.platform_science.routing_shipments.PSCodeApplication.Companion.utils
import com.platform_science.routing_shipments.common.Status
import com.platform_science.routing_shipments.data.model.ShipmentsModel
import com.platform_science.routing_shipments.databinding.FragmentShipmentsListBinding
import com.platform_science.routing_shipments.domain.model.Address
import com.platform_science.routing_shipments.domain.model.Driver
import com.platform_science.routing_shipments.ui.view.adapter.AddressAdapter
import com.platform_science.routing_shipments.ui.view.adapter.DriverAdapter
import com.platform_science.routing_shipments.ui.viewModel.ShipmentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Logger

/**
 * Shipments screen with drivers list and address list
 */
@AndroidEntryPoint
class ShipmentsListFragment : Fragment() {
    private val log: Logger = Logger.getLogger(ShipmentsListFragment::class.java.name)

    private var _binding: FragmentShipmentsListBinding? = null

    private val binding get() = _binding!!

    private val driversShipmentsViewModel: ShipmentsViewModel by viewModels()

    private val driverArray = ArrayList<Driver>()

    private val addressArrayValues = ArrayList<Address>()
    private val addressArrayIndex = ArrayList<String>()

    private var indexValueAddress: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipmentsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /**
     * Initialize components, adapters
     */
    private fun init() {

        binding.imageBtnClose.setOnClickListener {
            activity?.finish()
        }

        val driverAdapter = DriverAdapter(DriverAdapter.OnClickListener { item, position ->
            log.info("driver :: selected ::  -> $item pos:: $position")
            item.let {
                val end = getEnd()
                val randomIndex = getRandomIndex(end)
                indexValueAddress = getIndex(randomIndex)

                if (addressArrayIndex.isNotEmpty() && addressArrayValues[indexValueAddress].status == Status.AVAILABLE.ordinal && driverArray[position].status == Status.AVAILABLE.ordinal) {
                    assignShipment(position, randomIndex)
                }
            }
        })

        val addressAdapter = AddressAdapter()

        observers(driverAdapter, addressAdapter)

        binding.rvDrivers.adapter = driverAdapter
        binding.rvAddress.adapter = addressAdapter

        binding.swipeRefreshDrivers.isEnabled = false
        binding.swipeRefreshShipments.isEnabled = false

    }

    /**
     * Assign shipment (address) to driver randomly
     *
     * @param position Item selected
     * @param randomIndex It's an index of the main array stored in an auxiliary array that generates a number random and recovers the source index to use in the source list
     *                    and assigns the address to the driver.
     */
    private fun assignShipment(position: Int, randomIndex: Int) {
        driverArray[position].address = "Assigned: " + addressArrayValues[indexValueAddress].address
        driverArray[position].status = Status.BUSY.ordinal
        addressArrayValues[indexValueAddress].status = Status.BUSY.ordinal
        addressArrayIndex.removeAt(randomIndex)
        driversShipmentsViewModel.itemSelected(position)
    }

    /**
     * Get the source index from an auxiliary array
     *
     * @param index It's a number generated randomly
     */
    private fun getIndex(index: Int) = if (addressArrayIndex.size > 0) addressArrayIndex[index].toInt() else 0

    /**
     * Generate a random number
     *
     * @param end Maximum range
     */
    private fun getRandomIndex(end: Int) = utils!!.rand(0, end)

    /**
     *  Get a maximum range using an auxiliary array
     */
    private fun getEnd() = if (addressArrayIndex.size > 0) addressArrayIndex.size - 1 else 0

    /**
     * Observers used
     */
    private fun observers(driverAdapter: DriverAdapter, addressAdapter: AddressAdapter) {
        driversShipmentsViewModel.shipmentsModel.observe(viewLifecycleOwner) { data ->

            getDrivers(data, driverAdapter)

            getShipments(data, addressAdapter)

        }

        driversShipmentsViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
        }

        driversShipmentsViewModel.isItemSelected.observe(viewLifecycleOwner) { positionItemDriver ->
            log.info("observer :: refresh :: $positionItemDriver")
            updateItemDriver(driverAdapter, positionItemDriver)
            updateItemAddress(addressAdapter, indexValueAddress)
        }

    }

    /**
     * Update an item of address adapter(list)
     *
     * @param addressAdapter Address list
     * @param position Item to update
     */
    private fun updateItemAddress(addressAdapter: AddressAdapter, position: Int) {
        addressAdapter.notifyItemChanged(position)
    }

    /**
     * Update an Item of driver adapter(list)
     *
     * @param driverAdapter Driver list
     * @param position Item to update
     */
    private fun updateItemDriver(driverAdapter: DriverAdapter, position: Int) {
        driverAdapter.notifyItemChanged(position)
    }

    /**
     * Fetch only the drivers to set in the list.
     *
     * @param data data model
     * @param driverAdapter Adapter to use
     */
    private fun getDrivers(data: ShipmentsModel, driverAdapter: DriverAdapter) {
        data.drivers.let { list ->
            log.info("observer :: drivers :: $list")

            for (driver in list) {
                driverArray.add(Driver(driver = driver))
            }
            log.info("observer :: driver to List :: $driverArray")
            setListDrivers(driverAdapter, driverArray)

        }
    }

    /**
     * Fetch only the shipments to set in the list.
     *
     * @param data data model
     * @param addressAdapter Adapter to use
     */
    private fun getShipments(data: ShipmentsModel, addressAdapter: AddressAdapter) {
        data.shipments.let { list ->
            log.info("observer :: shipments :: $list")

            for (index in list.indices) {
                addressArrayValues.add(Address(address = list[index]))
                addressArrayIndex.add(index, index.toString())
            }

            setListAddress(addressAdapter, addressArrayValues)

        }
    }

    /**
     *  Set items to the driver list.
     *
     *  @param adapter Adapter to use
     *  @param list Items to set in the list
     */
    private fun setListDrivers(adapter: DriverAdapter, list: List<Driver>) {
        log.info("adapter :: driver list :: $list")

        adapter.submitList(null)
        adapter.submitList(list)
    }

    /**
     *  Set items to the driver list.
     *
     *  @param adapter Adapter to use
     *  @param list Items to set in the list
     */
    private fun setListAddress(adapter: AddressAdapter, list: List<Address>) {
        log.info("adapter :: address list :: $list")

        adapter.submitList(null)
        adapter.submitList(list)
    }

}