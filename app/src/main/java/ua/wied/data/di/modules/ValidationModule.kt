package ua.wied.data.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.wied.domain.models.validation.Validator
import ua.wied.domain.usecases.validation.ValidateCompanyUseCase
import ua.wied.domain.usecases.validation.ValidateEmailUseCase
import ua.wied.domain.usecases.validation.ValidateEmptyUseCase
import ua.wied.domain.usecases.validation.ValidateLoginUseCase
import ua.wied.domain.usecases.validation.ValidatePasswordConfirmUseCase
import ua.wied.domain.usecases.validation.ValidatePasswordUseCase
import ua.wied.domain.usecases.validation.ValidatePhoneUseCase

@Module
@InstallIn(SingletonComponent::class)
class ValidationModule {

    @Provides
    fun provideValidator(
        validateCompany: ValidateCompanyUseCase,
        validateEmail: ValidateEmailUseCase,
        validateEmpty: ValidateEmptyUseCase,
        validateLogin: ValidateLoginUseCase,
        validatePassword: ValidatePasswordUseCase,
        validatePasswordConfirm: ValidatePasswordConfirmUseCase,
        validatePhone: ValidatePhoneUseCase
    ): Validator {
        return Validator(
            validateCompany,
            validateEmail,
            validateEmpty,
            validateLogin,
            validatePassword,
            validatePasswordConfirm,
            validatePhone
        )
    }
}
