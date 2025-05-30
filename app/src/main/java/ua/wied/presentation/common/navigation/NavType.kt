package ua.wied.presentation.common.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.user.User

val ReportType = navTypeOf<Report>()
val ReportsType = navTypeOf<List<Report>>()
val InstructionType = navTypeOf<Instruction>()
val ElementType = navTypeOf<Element>()
val UserType = navTypeOf<User>()

private inline fun <reified T> navTypeOf(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let(json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: T): String = Uri.encode(json.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putString(key, json.encodeToString(value))

}