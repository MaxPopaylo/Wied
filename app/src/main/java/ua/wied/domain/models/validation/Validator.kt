package ua.wied.domain.models.validation

import ua.wied.domain.usecases.validation.ValidateCompanyUseCase
import ua.wied.domain.usecases.validation.ValidateEmailUseCase
import ua.wied.domain.usecases.validation.ValidateEmptyUseCase
import ua.wied.domain.usecases.validation.ValidateLoginUseCase
import ua.wied.domain.usecases.validation.ValidatePasswordUseCase
import ua.wied.domain.usecases.validation.ValidatePasswordConfirmUseCase
import ua.wied.domain.usecases.validation.ValidatePhoneUseCase
import javax.inject.Inject

class Validator @Inject constructor(
    private val validateCompany: ValidateCompanyUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validateEmpty: ValidateEmptyUseCase,
    private val validateLogin: ValidateLoginUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validatePasswordConfirm: ValidatePasswordConfirmUseCase,
    private val validatePhone: ValidatePhoneUseCase,
) {

    fun validateCompanyField(text: String): ValidationResult {
        return validateCompany(text)
    }

    fun validateEmailField(text: String): ValidationResult {
        return validateEmail(text)
    }

    fun validateEmptyField(text: String): ValidationResult {
        return validateEmpty(text)
    }

    fun validateLoginField(text: String): ValidationResult {
        return validateLogin(text)
    }

    fun validatePasswordField(text: String): ValidationResult {
        return validatePassword(text)
    }

    fun validatePasswordConfirmField(password: String, confirmPassword: String): ValidationResult {
        return validatePasswordConfirm(password, confirmPassword)
    }

    fun validatePhoneField(text: String): ValidationResult {
        return validatePhone(text)
    }
}
