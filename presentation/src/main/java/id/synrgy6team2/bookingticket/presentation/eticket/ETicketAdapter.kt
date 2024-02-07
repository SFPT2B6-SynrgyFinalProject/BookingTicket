package id.synrgy6team2.bookingticket.presentation.eticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemETicketBinding
import javax.inject.Inject

class ETicketAdapter @Inject constructor() : ListAdapter<ETicketViewModel.Passenger, ETicketAdapter.PassengerViewHolder>(PassengerComparator) {

    private var _onClick: ((position: Int, item: ETicketViewModel.Passenger) -> Unit)? = null

    private object PassengerComparator : DiffUtil.ItemCallback<ETicketViewModel.Passenger>() {
        override fun areItemsTheSame(
            oldItem: ETicketViewModel.Passenger,
            newItem: ETicketViewModel.Passenger
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: ETicketViewModel.Passenger,
            newItem: ETicketViewModel.Passenger
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        return PassengerViewHolder(
            ItemETicketBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class PassengerViewHolder(private val binding: ItemETicketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: ETicketViewModel.Passenger) {
            binding.tvSequenceNumValue.text = "${bindingAdapterPosition+1}"
            binding.tvPassengerName.text = "${item.name}"
            binding.tvType.text = item.type
        }
    }

    fun onClick(listener: (position: Int, item: ETicketViewModel.Passenger) -> Unit) {
        _onClick = listener
    }
}