package com.app.movie_search_circuit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.app.movie_search_circuit.presentation.HomePresenter
import com.app.movie_search_circuit.presentation.HomeScreen
import com.app.movie_search_circuit.presentation.HomeUi
import com.app.movie_search_circuit.ui.theme.MoviesearchcircuitTheme
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent

class MainActivity : ComponentActivity() {
    private val circuit: Circuit by lazy {
        Circuit.Builder()
            .addPresenter<HomeScreen, HomeScreen.State>(HomePresenter())
            .addUi<HomeScreen, HomeScreen.State> { state, modifier ->
                HomeUi(state = state, modifier = modifier)
            }
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesearchcircuitTheme {
                CircuitCompositionLocals(circuit) {
                    CircuitContent(
                        screen = HomeScreen,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
