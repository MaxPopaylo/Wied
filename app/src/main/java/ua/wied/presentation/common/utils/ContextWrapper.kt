package ua.wied.presentation.common.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale
import ua.wied.domain.models.settings.Language

class MyContextWrapper(base: Context) : ContextWrapper(base) {

    companion object {
        fun wrap(context: Context): ContextWrapper {
            val res = context.resources
            val configuration = Configuration(res.configuration)
            val locale = LangUtils.getLocaleByLanguage(context)

            configuration.setLocale(locale)
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)

            configuration.setLayoutDirection(locale)
            val newContext = context.createConfigurationContext(configuration)
            return MyContextWrapper(newContext)
        }
    }
}

object LangUtils {
    fun getLocaleByLanguage(context: Context): Locale {
        val sharedPrefs = context.getSharedPreferences("settings_temp", Context.MODE_PRIVATE)
        val languageName = sharedPrefs.getString("language", Language.ENGLISH.name)

        val language = try {
            Language.valueOf(languageName ?: Language.ENGLISH.name)
        } catch (e: Exception) {
            Language.ENGLISH
        }

        return Locale.forLanguageTag(language.value)
    }
}
