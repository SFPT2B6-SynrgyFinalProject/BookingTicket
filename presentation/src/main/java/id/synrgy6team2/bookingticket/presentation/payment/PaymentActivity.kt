package id.synrgy6team2.bookingticket.presentation.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import coil.size.ViewSizeResolver
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.ValidationType
import id.synrgy6team2.bookingticket.common.convertToUtcFullDateTime
import id.synrgy6team2.bookingticket.common.createLoadingDialog
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.common.parseToRupiah
import id.synrgy6team2.bookingticket.common.parseToTime
import id.synrgy6team2.bookingticket.common.showNotification
import id.synrgy6team2.bookingticket.common.toCustomFormat
import id.synrgy6team2.bookingticket.common.toImgUrl
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderResponseModel
import id.synrgy6team2.bookingticket.presentation.MainActivity
import id.synrgy6team2.bookingticket.presentation.booked.PaymentStatusActivity
import id.synrgy6team2.bookingticket.presentation.bookingdetail.BookingDetailActivity
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityPaymentBinding
import id.synrgy6team2.bookingticket.presentation.notification.NotificationActivity


@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private val dialog: AlertDialog by lazy { createLoadingDialog() }
    private val orderId: String by lazy { intent.getStringExtra(EXTRA_PASSING_ORDER_ID) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.paymentOrder.observe(this) { state ->
            handleStatePaymentOrder(state, {
                dialog.show()
            }, {
                dialog.dismiss()

                val toNotification = NotificationActivity.getIntentTo(this, true)
                showNotification(getString(R.string.txt_payment_success), getString(R.string.txt_payment_success_detail), toNotification)

                val intent = PaymentStatusActivity.getIntentTo(this, orderId)
                startActivity(intent)
                finish()
            }, { message ->
                dialog.dismiss()
                onToast("Error!", message, StyleType.ERROR)
            })
        }
        viewModel.orderDetail.observe(this) { state ->
            handleStateOrderDetail(state, {
                dialog.show()
                binding.layoutItemDepartureDetails.contentNotFound.isVisible = false
                binding.layoutItemDepartureDetails.content.isVisible = false
            }, { data ->
                dialog.dismiss()
                showDataOrderDetail(data)
                binding.layoutItemDepartureDetails.contentNotFound.isVisible = false
                binding.layoutItemDepartureDetails.content.isVisible = true
            }, { message ->
                dialog.dismiss()
                binding.layoutItemDepartureDetails.contentNotFound.isVisible = true
                binding.layoutItemDepartureDetails.content.isVisible = false
                onToast("Error!", message, StyleType.ERROR)
            })
        }
        viewModel.orderDetail(orderId)
    }

    private fun bindView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnContinue.setOnClickListener {
            sendOrderToPayment()
        }

        binding.layoutItemDepartureDetails.root.setOnClickListener {
            val intent = BookingDetailActivity.getIntentTo(this, orderId)
            startActivity(intent)
        }

        binding.layoutItemDepartureDetails.btnRetry.setOnClickListener {
            viewModel.orderDetail(orderId)
        }
    }

    private fun sendOrderToPayment() {
        onValidation(
            validationParams = arrayOf(
                binding.tilCreditCardNumber to ValidationType.GENERAL,
                binding.tilCreditCardName to ValidationType.GENERAL,
                binding.tilExpirationDate to ValidationType.GENERAL,
                binding.tilCVC to ValidationType.GENERAL
            ),
            onConfirmPassword = null
        ) {
            val creditCard = binding.txtCreditCard.text
            val creditName = binding.txtCreditName.text
            val creditExpire = binding.txtExpireCredit.text
            val creditCode = binding.txtCvc.text
            try {
                val value = PayOrderRequestModel(
                    cvv = creditCode.toString(),
                    cardName = creditName.toString(),
                    cardNumber = creditCard.toString(),
                    expiredDate = creditExpire.toString().convertToUtcFullDateTime(),
                    orderId = orderId
                )
                viewModel.paymentOrder(value)
            } catch (e: Exception) {
                onToast("Error!", "Format harus TahunBulan example: 2805", StyleType.ERROR)
            }
        }
    }

    private fun showDataOrderDetail(data: GetOrderDetailResponseModel?) {
        binding.layoutItemDepartureDetails.tvDepartureCity.text = "${data?.data?.flightDetails?.departure?.city} (${data?.data?.flightDetails?.departure?.code})"
        binding.layoutItemDepartureDetails.tvDestination.text = "${data?.data?.flightDetails?.arrival?.city} (${data?.data?.flightDetails?.arrival?.code})"
        binding.layoutItemDepartureDetails.tvDepartureTime.text = "${data?.data?.flightDetails?.departure?.dateTime?.parseToTime()}"
        binding.layoutItemDepartureDetails.tvArrivalTime.text = "${data?.data?.flightDetails?.arrival?.dateTime?.parseToTime()}"
        binding.layoutItemDepartureDetails.tvArrivalDate.text = "${data?.data?.flightDetails?.departure?.dateTime?.toCustomFormat()}"
        binding.layoutItemDepartureDetails.tvPassengerCount.text = "${data?.data?.passengerDetails?.passengerTotal} Orang"
        binding.layoutItemDepartureDetails.tvFlightClass.text = "${data?.data?.flightClass}"
        binding.layoutItemDepartureDetails.tvContactName.text = "${data?.data?.orderer?.fullName}"
        binding.layoutItemDepartureDetails.tvEmailAddress.text = "${data?.data?.orderer?.email}"
        binding.layoutItemDepartureDetails.tvPhoneNumber.text = "${data?.data?.orderer?.phoneNumber}"
        binding.layoutItemDepartureDetails.tvDiscountAmount.text = "Diskon ${data?.data?.priceDetails?.totalDicount?.parseToRupiah()}"
        binding.layoutItemDepartureDetails.tvActualRates.text = "${data?.data?.priceDetails?.total?.plus(data.data?.priceDetails?.totalDicount ?: 0)?.parseToRupiah()}"
        binding.layoutItemDepartureDetails.tvDiscountRates.text = "${data?.data?.priceDetails?.total?.parseToRupiah()}"
        binding.layoutItemDepartureDetails.tvPassengerName.text = "${data?.data?.orderer?.fullName}"
        binding.layoutItemDepartureDetails.tvRateDetails.text = "${data?.data?.passengerDetails?.passengerTotal} Penumpang termasuk pajak"
        binding.layoutItemDepartureDetails.imgAirline.load(data?.data?.flightDetails?.airline?.iconUrl.toImgUrl()) {
            crossfade(true)
            placeholder(R.drawable.img_loading)
            error(R.drawable.img_not_found)
            size(ViewSizeResolver(binding.layoutItemDepartureDetails.imgAirline))
        }
    }

    private fun handleStateOrderDetail(
        state: State<GetOrderDetailResponseModel>,
        onLoading: () -> Unit,
        onSuccess: (GetOrderDetailResponseModel?) -> Unit,
        onError: (String?) -> Unit) {
        when (state) {
            is State.Loading -> { onLoading.invoke() }
            is State.Success -> { onSuccess.invoke(state.data) }
            is State.Error -> { onError.invoke(state.message) }
        }
    }

    private fun handleStatePaymentOrder(
        state: State<PayOrderResponseModel>,
        onLoading: () -> Unit,
        onSuccess: (PayOrderResponseModel?) -> Unit,
        onError: (String?) -> Unit) {
        when (state) {
            is State.Loading -> { onLoading.invoke() }
            is State.Success -> { onSuccess.invoke(state.data) }
            is State.Error -> { onError.invoke(state.message) }
        }
    }

    companion object {
        private const val EXTRA_PASSING_ORDER_ID: String = "EXTRA_PASSING_ORDER_ID"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            orderId: String?
        ): Intent {
            return Intent(context, PaymentActivity::class.java).apply {
                putExtra(EXTRA_PASSING_ORDER_ID, orderId)
            }
        }
    }
}