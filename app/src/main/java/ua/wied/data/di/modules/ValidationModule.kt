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
    fun provideValidateCompany(@ApplicationContext context: Context): ValidateCompanyUseCase {
        return ValidateCompanyUseCase(context)
    }

    @Provides
    fun provideValidateEmail(@ApplicationContext context: Context): ValidateEmailUseCase {
        return ValidateEmailUseCase(context)
    }

    @Provides
    fun provideValidateEmpty(@ApplicationContext context: Context): ValidateEmptyUseCase {
        return ValidateEmptyUseCase(context)
    }

    @Provides
    fun provideValidateLogin(@ApplicationContext context: Context): ValidateLoginUseCase {
        return ValidateLoginUseCase(context)
    }

    @Provides
    fun provideValidatePassword(@ApplicationContext context: Context): ValidatePasswordUseCase {
        return ValidatePasswordUseCase(context)
    }

    @Provides
    fun provideValidatePasswordConfirm(@ApplicationContext context: Context): ValidatePasswordConfirmUseCase {
        return ValidatePasswordConfirmUseCase(context)
    }

    @Provides
    fun provideValidatePhone(@ApplicationContext context: Context): ValidatePhoneUseCase {
        return ValidatePhoneUseCase(context)
    }

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
