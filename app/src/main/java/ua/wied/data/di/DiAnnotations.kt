package ua.wied.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserStoragePreference

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class JwtTokenPreference