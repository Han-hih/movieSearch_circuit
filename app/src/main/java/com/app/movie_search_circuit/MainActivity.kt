package com.app.movie_search_circuit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.app.movie_search_circuit.data.repository.MovieRepository
import com.app.movie_search_circuit.presentation.MovieSearchPresenter
import com.app.movie_search_circuit.presentation.MovieSearchScreen
import com.app.movie_search_circuit.presentation.MovieSearchUi
import com.app.movie_search_circuit.ui.theme.MoviesearchcircuitTheme
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var movieRepository: MovieRepository

    private val circuit: Circuit by lazy {
        Circuit.Builder()
            .addPresenter<MovieSearchScreen, MovieSearchScreen.State>(
                MovieSearchPresenter(movieRepository)
            )
            .addUi<MovieSearchScreen, MovieSearchScreen.State> { state, modifier ->
                MovieSearchUi(state = state, modifier = modifier)
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
                        screen = MovieSearchScreen,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
