package com.app.movie_search_circuit.presentation

import android.os.Parcel
import android.os.Parcelable
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen

object HomeScreen : Screen, Parcelable {
    data class State(val message: String) : CircuitUiState

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) = Unit

    @JvmField
    val CREATOR: Parcelable.Creator<HomeScreen> = object : Parcelable.Creator<HomeScreen> {
        override fun createFromParcel(parcel: Parcel): HomeScreen = HomeScreen

        override fun newArray(size: Int): Array<HomeScreen?> = arrayOfNulls(size)
    }
}
