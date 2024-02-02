package id.synrgy6team2.bookingticket.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.parseToTime
import id.synrgy6team2.bookingticket.common.toCustomFormat
import id.synrgy6team2.bookingticket.common.toImgUrl
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.ItemHistoryBinding
import javax.inject.Inject

class HistoryChildAdapter @Inject constructor() : ListAdapter<GetOrderResponseModel.Data.OrdersItem, HistoryChildAdapter.HistoryModelViewHolder>(
        HistoryModelComparator
    ) {

    private var _onClick: ((position: Int, item: GetOrderResponseModel.Data.OrdersItem) -> Unit)? = null

    private object HistoryModelComparator : DiffUtil.ItemCallback<GetOrderResponseModel.Data.OrdersItem>() {
        override fun areItemsTheSame(oldItem: GetOrderResponseModel.Data.OrdersItem, newItem: GetOrderResponseModel.Data.OrdersItem): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: GetOrderResponseModel.Data.OrdersItem, newItem: GetOrderResponseModel.Data.OrdersItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryModelViewHolder {
        return HistoryModelViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class HistoryModelViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: GetOrderResponseModel.Data.OrdersItem) {
            binding.txtTitle.text = "${item.departure?.city} (${item.departure?.code}) - ${item.arrival?.city} (${item.arrival?.code})"
            binding.txtSchedule.text = "${item.departure?.dateTime?.parseToTime()} - ${item.arrival?.dateTime?.parseToTime()} ${item.departure?.dateTime?.toCustomFormat()}"
            binding.txtPerson.text = "${item.totalPassengers?.plus(1)} Orang"
            binding.txtFlightClass.text = "${item.paymentStatus}"
            binding.imgLogoAirport.load(item.airline?.iconUrl.toImgUrl()) {
                crossfade(true)
                placeholder(R.drawable.img_loading)
                error(R.drawable.img_not_found)
                size(ViewSizeResolver(binding.imgLogoAirport))
            }
        }
    }

    fun onClick(listener: (position: Int, item: GetOrderResponseModel.Data.OrdersItem) -> Unit) {
        _onClick = listener
    }
}