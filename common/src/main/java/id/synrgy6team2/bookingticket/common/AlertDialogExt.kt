package id.synrgy6team2.bookingticket.common

import android.content.Context
import android.view.LayoutInflater
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.synrgy6team2.bookingticket.common.databinding.DialogProgressBarBinding

fun Context.createMessageDialog(
    title: String,
    message: String,
    onItemClick: ((DialogInterface) -> Unit)? = null
) {
    MaterialAlertDialogBuilder(this).setTitle(title)
        .setMessage(message)
        .setPositiveButton("Ok") { dialog, _ ->
            onItemClick?.invoke(dialog)
        }
        .setCancelable(false)
        .create()
        .show()
}

fun Context.createMessageDialog(
    title: String,
    message: String,
    onItemPositive: ((DialogInterface) -> Unit)? = null,
    onItemNegative: ((DialogInterface) -> Unit)? = null,
) {
    MaterialAlertDialogBuilder(this).setTitle(title)
        .setMessage(message)
        .setPositiveButton("Ok") { dialog, _ ->
            onItemPositive?.invoke(dialog)
        }
        .setNegativeButton("Tidak") { dialog, _ ->
            onItemNegative?.invoke(dialog)
        }
        .setCancelable(false)
        .create()
        .show()
}

fun Context.createLoadingDialog(): AlertDialog {
    val binding = DialogProgressBarBinding.inflate(LayoutInflater.from(this))
    return MaterialAlertDialogBuilder(this)
        .setView(binding.root)
        .setCancelable(false)
        .create()
}

