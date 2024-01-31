package id.synrgy6team2.bookingticket.presentation.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemBookingPassengerBinding
import javax.inject.Inject

class BookingPassengerAdapter @Inject constructor() : ListAdapter<String, BookingPassengerAdapter.StringViewHolder>(StringComparator) {

    private var _bookingPassengerListener: BookingPassengerListener? = null

    private object StringComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        return StringViewHolder(
            ItemBookingPassengerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class StringViewHolder(private val binding: ItemBookingPassengerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: String) {
            binding.txtFullName.addTextChangedListener { editTable ->
                _bookingPassengerListener?.onChange(bindingAdapterPosition, "${editTable.toString()} ${binding.txtPassenger.text}")
            }
            binding.txtPassenger.addTextChangedListener { editTable ->
                _bookingPassengerListener?.onChange(bindingAdapterPosition, "${editTable.toString()} ${binding.txtFullName.text.toString()}")
            }
        }
    }

    fun onChange(listener: BookingPassengerListener) {
        _bookingPassengerListener = listener
    }

    interface BookingPassengerListener {
        fun onChange(position: Int, value: String)
    }
}