package ua.wied.domain.models.settings

data class Settings(
    val language: Language,
    val darkTheme: Boolean?,
) {
    companion object {
        fun getDefaultSettings() = Settings(
            language = Language.ENGLISH,
            darkTheme = false
        )
    }
}