package ua.wied.domain.models.validation

data class ValidationResult (
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)