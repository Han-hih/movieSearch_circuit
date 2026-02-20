package com.app.movie_search_circuit.presentation

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.presenter.Presenter

class HomePresenter : Presenter<HomeScreen.State> {

    @Composable
    override fun present(): HomeScreen.State {
        return HomeScreen.State(message = "Circuit is ready")
    }
}
