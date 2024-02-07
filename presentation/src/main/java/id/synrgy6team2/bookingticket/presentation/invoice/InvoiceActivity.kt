package id.synrgy6team2.bookingticket.presentation.invoice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.parcelable
import id.synrgy6team2.bookingticket.common.parseToRupiah
import id.synrgy6team2.bookingticket.common.parseToTime
import id.synrgy6team2.bookingticket.common.toCustomFormat
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityInvoiceBinding

@AndroidEntryPoint
class InvoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvoiceBinding

    private val viewModel: InvoiceViewModel by viewModels()
    private val item: GetOrderDetailResponseModel by lazy {
        intent.parcelable(PASSING_DATA_FROM_BOOKING_DETAIL) ?: GetOrderDetailResponseModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.profile.observe(this) {
            binding.tvFullname.text = it.data?.fullName
            binding.tvCustomerEmailAddress.text = it.data?.email
            binding.tvCustomerPhoneNumber.text = it.data?.noHp
        }
    }

    private fun bindView() {
        binding.tvOrderID.text = "Order ID: ${item.data?.orderId}"
        binding.tvPaymentTimeDetail.text = "${item.data?.paymentTime?.parseToTime()} ${item.data?.flightDetails?.departure?.dateTime?.toCustomFormat()}"
        binding.tvProductDescriptionDetail.text = "${item.data?.flightDetails?.airline?.name}"
        binding.tvFlightClass.text = "${item.data?.flightClass} (${item.data?.flightDetails?.departure?.code} - ${item.data?.flightDetails?.arrival?.code})"
        binding.tvTotalAmount.text = "${item.data?.priceDetails?.total?.plus(item.data?.priceDetails?.totalDicount ?: 0)?.parseToRupiah()}"
        binding.tvDiscountAmount.text = "-${item.data?.priceDetails?.totalDicount?.parseToRupiah()}"
        binding.tvSubTotalAmount.text = "${item.data?.priceDetails?.total?.parseToRupiah()}"
        binding.tvTotalPaymentsAmount.text = "${item.data?.priceDetails?.total?.parseToRupiah()}"
    }

    companion object {
        private const val PASSING_DATA_FROM_BOOKING_DETAIL: String = "PASSING_DATA_FROM_BOOKING_DETAIL"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            item: GetOrderDetailResponseModel?
        ): Intent {
            return Intent(context, InvoiceActivity::class.java).apply {
                putExtra(PASSING_DATA_FROM_BOOKING_DETAIL, item)
            }
        }
    }
}