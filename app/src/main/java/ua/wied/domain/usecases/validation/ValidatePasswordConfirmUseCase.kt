package ua.wied.domain.usecases.validation

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.wied.R
import ua.wied.domain.models.validation.ValidationResult
import javax.inject.Inject

class ValidatePasswordConfirmUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    operator fun invoke(password: String, confirmPassword: String): ValidationResult = when {
        password.isEmpty() && confirmPassword.isEmpty() -> {
            ValidationResult(
                isSuccessful = false,
                errorMessage = context.getString(R.string.validation_empty_field)
            )
        }

        password != confirmPassword -> {
            ValidationResult(
                isSuccessful = false,
                errorMessage = context.getString(R.string.validation_password_confirm_field)
            )
        }

        else -> {
            ValidationResult(
                isSuccessful = true,
                errorMessage = null
            )
        }
    }
}