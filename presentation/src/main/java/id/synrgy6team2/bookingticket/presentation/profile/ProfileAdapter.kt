package id.synrgy6team2.bookingticket.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemPreferenceBinding

class ProfileAdapter :
    ListAdapter<ProfileModel, ProfileAdapter.ProfileModelViewHolder>(ProfileModelComparator) {

    private var _onClick: ((position: Int, item: ProfileModel) -> Unit)? = null

    private object ProfileModelComparator : DiffUtil.ItemCallback<ProfileModel>() {
        override fun areItemsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileModelViewHolder {
        return ProfileModelViewHolder(
            ItemPreferenceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ProfileModelViewHolder(private val binding: ItemPreferenceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: ProfileModel) {
            binding.iconPreference.setImageResource(item.img)
            binding.tvPreference.text = binding.root.context.getString(item.title)
        }
    }

    fun onClick(listener: (position: Int, item: ProfileModel) -> Unit) {
        _onClick = listener
    }
}