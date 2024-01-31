package id.synrgy6team2.bookingticket.common

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
        .show()
}

