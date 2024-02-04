package id.synrgy6team2.bookingticket.presentation

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.createMessageDialog
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityMainBinding
import id.synrgy6team2.bookingticket.presentation.login.LoginActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    private val permissionNotificationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.verify.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    onToast(
                        getString(id.synrgy6team2.bookingticket.common.R.string.txt_loading_progress),
                        getString(id.synrgy6team2.bookingticket.common.R.string.txt_loading_progress_description),
                        StyleType.INFO
                    )
                }
                is State.Success -> {
                    createMessageDialog(
                        getString(id.synrgy6team2.bookingticket.common.R.string.txt_verify_successfully),
                        getString(id.synrgy6team2.bookingticket.common.R.string.txt_verify_successfully_description)
                    ) { dialogInterface: DialogInterface -> dialogInterface.dismiss() }
                }
                is State.Error -> {
                    onToast(
                        "Error!",
                        state.message,
                        StyleType.ERROR
                    )
                }
            }
        }
    }

    private fun bindView() {
        intent.data?.let { uri ->
            viewModel.verify(uri.lastPathSegment)
        }

        val host = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        navController = host.navController
        navController.addOnDestinationChangedListener(this)
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.dashboardFragment -> {
                requestPermissionOptions()
                binding.bottomNav.isVisible = true
            }
            R.id.promotionFragment -> binding.bottomNav.isVisible = true
            R.id.bookingFragment -> binding.bottomNav.isVisible = true
            R.id.profileFragment -> binding.bottomNav.isVisible = true
            else -> binding.bottomNav.isVisible = false
        }
    }

    private fun requestPermissionOptions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    createMessageDialog(
                        "Diperlukan Izin Pemberitahuan",
                        "Tanpa notifikasi, mustahil untuk mengetahui informasi tentang Promosi Tiket dan Pemesanan Maskapai Penerbangan",
                        onItemPositive = { dialog: DialogInterface -> dialog.dismiss() }
                    )
                }
                else -> {
                    permissionNotificationLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }
        }
    }
}