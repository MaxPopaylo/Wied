package ua.wied.domain.usecases.validation

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.wied.R
import ua.wied.domain.models.validation.ValidationResult
import ua.wied.domain.models.validation.Validation
import javax.inject.Inject

class ValidateEmptyUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : Validation {
    override fun invoke(text: String): ValidationResult = when {
        text.isEmpty() -> {
            ValidationResult(
                isSuccessful = false,
                errorMessage = context.getString(R.string.validation_empty_field)
            )
        }

        else -> {
            ValidationResult(
                isSuccessful = false,
                errorMessage = null
            )
        }
    }
}