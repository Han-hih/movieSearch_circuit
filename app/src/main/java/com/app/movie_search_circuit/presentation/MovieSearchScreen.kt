package com.app.movie_search_circuit.presentation

import android.os.Parcel
import android.os.Parcelable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen

object MovieSearchScreen : Screen, Parcelable {
    enum class Tab { HOME, SEARCH, LIKES }

    data class State(
        val selectedTab: Tab,
        val query: String,
        val isInitialLoading: Boolean,
        val isSearching: Boolean,
        val errorMessage: String?,
        val popularMovies: List<MovieItem>,
        val latestMovies: List<MovieItem>,
        val searchMovies: List<MovieItem>,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    data class MovieItem(
        val id: Int,
        val title: String,
        val releaseDate: String,
    )

    sealed interface Event : CircuitUiEvent {
        data class TabChanged(val tab: Tab) : Event
        data class QueryChanged(val query: String) : Event
        data object SearchClicked : Event
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) = Unit

    @JvmField
    val CREATOR: Parcelable.Creator<MovieSearchScreen> =
        object : Parcelable.Creator<MovieSearchScreen> {
            override fun createFromParcel(parcel: Parcel): MovieSearchScreen = MovieSearchScreen

            override fun newArray(size: Int): Array<MovieSearchScreen?> = arrayOfNulls(size)
        }
}
