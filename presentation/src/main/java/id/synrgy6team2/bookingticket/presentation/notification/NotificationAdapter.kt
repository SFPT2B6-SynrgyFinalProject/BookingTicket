package id.synrgy6team2.bookingticket.presentation.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.synrgy6team2.bookingticket.common.parseToTime
import id.synrgy6team2.bookingticket.common.toCustomFormat
import id.synrgy6team2.bookingticket.common.toImgUrl
import id.synrgy6team2.bookingticket.domain.model.NotificationResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.ItemNotificationBinding
import javax.inject.Inject

class NotificationAdapter @Inject constructor() : ListAdapter<NotificationResponseModel.Data.NotificationItem, NotificationAdapter.NotificationModelViewHolder>(
        NotificationModelComparator
    ) {

    private var _onClick: ((position: Int, item: NotificationResponseModel.Data.NotificationItem) -> Unit)? = null

    private object NotificationModelComparator : DiffUtil.ItemCallback<NotificationResponseModel.Data.NotificationItem>() {
        override fun areItemsTheSame(
            oldItem: NotificationResponseModel.Data.NotificationItem,
            newItem: NotificationResponseModel.Data.NotificationItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationResponseModel.Data.NotificationItem,
            newItem: NotificationResponseModel.Data.NotificationItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationModelViewHolder {
        return NotificationModelViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class NotificationModelViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: NotificationResponseModel.Data.NotificationItem) {
            binding.ivImage.load(item.imageUrl.toImgUrl())
            binding.txtTitle.text = item.title
            binding.tvContent.text = item.content
            binding.txtDateTime.text = "${item.createdDatetime?.toCustomFormat()} ${item.createdDatetime?.parseToTime()}"
        }
    }

    fun onClick(listener: (position: Int, item: NotificationResponseModel.Data.NotificationItem) -> Unit) {
        _onClick = listener
    }
}