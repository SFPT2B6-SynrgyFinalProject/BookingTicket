package id.synrgy6team2.bookingticket.common

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.github.anderscheow.validator.Validation
import io.github.anderscheow.validator.rules.common.equalTo
import io.github.anderscheow.validator.rules.common.minimumLength
import io.github.anderscheow.validator.rules.common.notEmpty
import io.github.anderscheow.validator.rules.common.notNull
import io.github.anderscheow.validator.rules.regex.PasswordRule
import io.github.anderscheow.validator.rules.regex.email
import io.github.anderscheow.validator.rules.regex.withPassword
import io.github.anderscheow.validator.validation

fun TextInputLayout.emailValid(): Validation {
    return validation(this) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +email(R.string.txt_not_email)
        }
    }
}

fun TextInputLayout.passwordValid(): Validation {
    return validation(this) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +minimumLength(8, R.string.txt_not_min_length_8)
            +withPassword(PasswordRule.PasswordRegex.ALPHA_MIXED_CASE, R.string.txt_not_lowercase_and_uppercase)
        }
    }
}

fun confirmPasswordValid(textInputLayout: TextInputLayout, textInputEditText: TextInputEditText): Validation {
    return validation(textInputLayout) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +minimumLength(8, R.string.txt_not_min_length_8)
            +withPassword(PasswordRule.PasswordRegex.ALPHA_MIXED_CASE, R.string.txt_not_lowercase_and_uppercase)
            +equalTo(textInputEditText.text.toString(), R.string.txt_not_valid_confirm_password)
        }
    }
}