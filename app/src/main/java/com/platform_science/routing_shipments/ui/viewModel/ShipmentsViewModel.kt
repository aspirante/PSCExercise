package com.platform_science.routing_shipments.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.platform_science.routing_shipments.data.model.ShipmentsModel
import com.platform_science.routing_shipments.domain.usecase.GetShipmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject

/**
 * View model class
 *
 * @constructor Inject use case
 */
@HiltViewModel
class ShipmentsViewModel @Inject constructor(
    private val getShipmentsUseCase: GetShipmentsUseCase
) : ViewModel() {
    private val log: Logger = Logger.getLogger(ShipmentsViewModel::class.java.name)

    val shipmentsModel = MutableLiveData<ShipmentsModel>()
    val isLoading = MutableLiveData<Boolean>()
    val isItemSelected = MutableLiveData<Int>()

    init {
        fetchData()
    }

    /**
     * Fetch shipments
     */
    private fun fetchData() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getShipmentsUseCase()
            result.let {
                shipmentsModel.postValue(it)
                isLoading.postValue(false)
            }
        }
    }

    /**
     * Item selected from drivers list
     *
     * @param position Item selected
     */
    fun itemSelected(position: Int) {
        isItemSelected.postValue(position)
    }
}