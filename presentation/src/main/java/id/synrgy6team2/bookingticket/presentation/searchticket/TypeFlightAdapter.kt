package id.synrgy6team2.bookingticket.presentation.searchticket

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.domain.model.FlightClassResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.ItemTypeFlightBinding
import javax.inject.Inject

class TypeFlightAdapter(
    private val data: MutableList<FlightClassResponseModel.DataItem> = mutableListOf()
) : RecyclerView.Adapter<TypeFlightAdapter.ViewHolder>() {

    private var _onClick: ((position: Int, item: FlightClassResponseModel.DataItem) -> Unit)? = null

    class ViewHolder(val binding: ItemTypeFlightBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<FlightClassResponseModel.DataItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTypeFlightBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.tvTypeFlight.text = item.name
        holder.binding.root.setOnClickListener {
            _onClick?.let {
                it(position, item)
            }
        }
    }

    fun onClick(listener: (position: Int, item: FlightClassResponseModel.DataItem) -> Unit) {
        _onClick = listener
    }
}