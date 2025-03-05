package ua.wied.domain.models.validation

interface Validation {
    operator fun invoke(text: String): ValidationResult
}