package ua.wied.domain.usecases.validation

import android.content.Context
import android.util.Patterns
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.wied.R
import ua.wied.domain.models.validation.ValidationResult
import ua.wied.domain.models.validation.Validation
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) : Validation {

    override operator fun invoke(text: String): ValidationResult = when {
        text.isEmpty() -> {
            ValidationResult(
                isSuccessful = false,
                errorMessage = context.getString(R.string.validation_empty_field)
            )
        }

        !Patterns.EMAIL_ADDRESS.matcher(text).matches() -> {
            ValidationResult(
                isSuccessful = false,
                errorMessage = context.getString(R.string.validation_email_field)
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