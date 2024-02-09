package id.synrgy6team2.bookingticket.common

import android.content.Context
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.github.anderscheow.validator.Validation
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.rules.common.equalTo
import io.github.anderscheow.validator.rules.common.minimumLength
import io.github.anderscheow.validator.rules.common.notEmpty
import io.github.anderscheow.validator.rules.common.notNull
import io.github.anderscheow.validator.rules.regex.PasswordRule
import io.github.anderscheow.validator.rules.regex.email
import io.github.anderscheow.validator.rules.regex.withPassword
import io.github.anderscheow.validator.validation
import io.github.anderscheow.validator.validator

private fun TextInputLayout.emailValid(): Validation {
    return validation(this) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +email(R.string.txt_not_email)
        }
    }
}

private fun TextInputLayout.passwordValid(): Validation {
    return validation(this) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +minimumLength(8, R.string.txt_not_min_length_8)
            // +withPassword(PasswordRule.PasswordRegex.ALPHA_MIXED_CASE, R.string.txt_not_lowercase_and_uppercase)
        }
    }
}

private fun TextInputLayout.confirmPasswordValid(textInputEditText: TextInputEditText): Validation {
    return validation(this) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +minimumLength(8, R.string.txt_not_min_length_8)
            // +withPassword(PasswordRule.PasswordRegex.ALPHA_MIXED_CASE, R.string.txt_not_lowercase_and_uppercase)
            +equalTo(textInputEditText.text.toString(), R.string.txt_not_valid_confirm_password)
        }
    }
}

private fun TextInputLayout.generalValid(): Validation {
    return validation(this) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
        }
    }
}

enum class ValidationType {
    EMAIL,
    PASSWORD,
    CONFIRM_PASSWORD,
    GENERAL
}

fun Context.onValidation(
    vararg validationParams: Pair<TextInputLayout, ValidationType>,
    onConfirmPassword: TextInputEditText?,
    onSuccess: () -> Unit
) {
    val validations = mutableListOf<Validation>()
    validationParams.forEach { (textInputLayout, validationType) ->
        val validation = when (validationType) {
            ValidationType.EMAIL -> textInputLayout.emailValid()
            ValidationType.PASSWORD -> textInputLayout.passwordValid()
            ValidationType.CONFIRM_PASSWORD -> textInputLayout.confirmPasswordValid(
                onConfirmPassword ?: textInputLayout.editText as TextInputEditText
            )
            ValidationType.GENERAL -> textInputLayout.generalValid()
        }
        validations.add(validation)
    }

    validator(this) {
        mode = Mode.SINGLE
        listener = object : Validator.OnValidateListener {
            override fun onValidateFailed(errors: List<String>) {}

            override fun onValidateSuccess(values: List<String>) {
                onSuccess.invoke()
            }
        }
        validate(*validations.toTypedArray())
    }
}