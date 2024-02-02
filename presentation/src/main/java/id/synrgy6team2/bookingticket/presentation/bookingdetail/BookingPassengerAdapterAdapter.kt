package id.synrgy6team2.bookingticket.presentation.bookingdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemPassengerBinding
import javax.inject.Inject

class BookingPassengerAdapterAdapter @Inject constructor() :
    ListAdapter<BookingDetailViewModel.Passenger, BookingPassengerAdapterAdapter.PassengerViewHolder>(
        PassengerComparator
    ) {

    private object PassengerComparator : DiffUtil.ItemCallback<BookingDetailViewModel.Passenger>() {
        override fun areItemsTheSame(
            oldItem: BookingDetailViewModel.Passenger,
            newItem: BookingDetailViewModel.Passenger
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: BookingDetailViewModel.Passenger,
            newItem: BookingDetailViewModel.Passenger
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        return PassengerViewHolder(
            ItemPassengerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class PassengerViewHolder(private val binding: ItemPassengerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: BookingDetailViewModel.Passenger) {
            binding.tvSequenceNumber.text = "${bindingAdapterPosition + 1}."
            binding.tvPassengerName.text = "${item.name}"
            binding.tvPassengerCategory.text = "${item.type}"
        }
    }
}