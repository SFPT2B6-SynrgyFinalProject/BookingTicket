package id.synrgy6team2.bookingticket.presentation.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.common.parseToRupiah
import id.synrgy6team2.bookingticket.common.parseToStringTime
import id.synrgy6team2.bookingticket.common.parseToTime
import id.synrgy6team2.bookingticket.domain.model.TicketResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.ItemPemilihanTiketPesawatBinding
import javax.inject.Inject

class PemilihanTiketAdapter @Inject constructor() : ListAdapter<TicketResponseModel.Data.AvailableFlightItem, PemilihanTiketAdapter.AvailableFlightItemViewHolder>(
        AvailableFlightItemComparator
    ) {

    private var _onClick: ((position: Int, item: TicketResponseModel.Data.AvailableFlightItem) -> Unit)? =
        null

    private object AvailableFlightItemComparator :
        DiffUtil.ItemCallback<TicketResponseModel.Data.AvailableFlightItem>() {
        override fun areItemsTheSame(
            oldItem: TicketResponseModel.Data.AvailableFlightItem,
            newItem: TicketResponseModel.Data.AvailableFlightItem
        ): Boolean {
            return oldItem.ticketId == newItem.ticketId
        }

        override fun areContentsTheSame(
            oldItem: TicketResponseModel.Data.AvailableFlightItem,
            newItem: TicketResponseModel.Data.AvailableFlightItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableFlightItemViewHolder {
        return AvailableFlightItemViewHolder(
            ItemPemilihanTiketPesawatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AvailableFlightItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class AvailableFlightItemViewHolder(private val binding: ItemPemilihanTiketPesawatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: TicketResponseModel.Data.AvailableFlightItem) {
            binding.tvTempatAsal.text = item.departureAirportCode ?: "CODE"
            binding.tvTempatTiba.text = item.arrivalAirportCode ?: "CODE"
            binding.tvWaktuBerangkat.text = item.departureDateTime?.parseToTime() ?: "00:00"
            binding.tvWaktuTiba.text = item.arrivalDateTime?.parseToTime() ?: "00:00"
            binding.tvDurasi.text = item.durationInMin?.parseToStringTime() ?: "0m"
            binding.tvHargaAsli.text = item.basePricePerPerson?.parseToRupiah() ?: "0"
            binding.tvHargaDiskon.text = item.discountedPricePerPerson?.parseToRupiah() ?: "0"
            if (item.withFood == true) {
                binding.contentLuggageWithFood.isVisible = true
                binding.contentLuggageOnly.isVisible = false
            } else {
                binding.contentLuggageWithFood.isVisible = false
                binding.contentLuggageOnly.isVisible = true
            }
        }
    }

    fun onClick(listener: (position: Int, item: TicketResponseModel.Data.AvailableFlightItem) -> Unit) {
        _onClick = listener
    }
}