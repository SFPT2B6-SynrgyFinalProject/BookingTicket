package id.synrgy6team2.bookingticket.data.local.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import id.synrgy6team2.bookingticket.data.local.entity.OrderEntity

class OrderTypeConverter {
    @TypeConverter
    fun fromDepartureToString(departure: OrderEntity.Departure?): String? {
        return Gson().toJson(departure)
    }

    @TypeConverter
    fun fromStringToDeparture(string: String?): OrderEntity.Departure? {
        return Gson().fromJson(string, OrderEntity.Departure::class.java)
    }

    @TypeConverter
    fun fromArrivalToString(arrival: OrderEntity.Arrival?): String? {
        return Gson().toJson(arrival)
    }

    @TypeConverter
    fun fromStringToArrival(string: String?): OrderEntity.Arrival? {
        return Gson().fromJson(string, OrderEntity.Arrival::class.java)
    }

    @TypeConverter
    fun fromAirlineToString(airline: OrderEntity.Airline?): String? {
        return Gson().toJson(airline)
    }

    @TypeConverter
    fun fromStringToAirline(string: String?): OrderEntity.Airline? {
        return Gson().fromJson(string, OrderEntity.Airline::class.java)
    }
}