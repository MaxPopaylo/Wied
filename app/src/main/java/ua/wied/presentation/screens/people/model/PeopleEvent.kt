package ua.wied.presentation.screens.people.model

sealed class PeopleEvent {
    data class SearchChanged(val value: String): PeopleEvent()
    data class DeletePressed(val value: Int): PeopleEvent()
    data object Refresh : PeopleEvent()
}