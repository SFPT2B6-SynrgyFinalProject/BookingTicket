package id.synrgy6team2.bookingticket.presentation.searchticket

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.domain.model.AirportResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.ItemAirportBinding

class AirportAdapter(
    private val data: MutableList<AirportResponseModel.DataItem> = mutableListOf()
) : RecyclerView.Adapter<AirportAdapter.ViewHolder>() {

    private var _onClick: ((position: Int, item: AirportResponseModel.DataItem) -> Unit)? = null

    class ViewHolder(val binding: ItemAirportBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<AirportResponseModel.DataItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAirportBinding.inflate(
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
        holder.binding.tvAirport.text = "${item.airportName} (${item.code})"
        holder.binding.tvCity.text = item.cityName
        holder.binding.root.setOnClickListener {
            _onClick?.let {
                it(position, item)
            }
        }
    }

    fun onClick(listener: (position: Int, item: AirportResponseModel.DataItem) -> Unit) {
        _onClick = listener
    }
}