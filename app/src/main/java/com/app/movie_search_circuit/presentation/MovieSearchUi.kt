package com.app.movie_search_circuit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchUi(
    state: MovieSearchScreen.State,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("TMDB Movie Search") }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = state.selectedTab == MovieSearchScreen.Tab.HOME,
                    onClick = { state.eventSink(MovieSearchScreen.Event.TabChanged(MovieSearchScreen.Tab.HOME)) },
                    icon = { Text("●") },
                    label = { Text("홈") },
                )
                NavigationBarItem(
                    selected = state.selectedTab == MovieSearchScreen.Tab.SEARCH,
                    onClick = { state.eventSink(MovieSearchScreen.Event.TabChanged(MovieSearchScreen.Tab.SEARCH)) },
                    icon = { Text("●") },
                    label = { Text("검색") },
                )
                NavigationBarItem(
                    selected = state.selectedTab == MovieSearchScreen.Tab.LIKES,
                    onClick = { state.eventSink(MovieSearchScreen.Event.TabChanged(MovieSearchScreen.Tab.LIKES)) },
                    icon = { Text("●") },
                    label = { Text("좋아요") },
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { state.eventSink(MovieSearchScreen.Event.QueryChanged(it)) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("영화 제목") },
                    singleLine = true,
                )
                Button(onClick = { state.eventSink(MovieSearchScreen.Event.SearchClicked) }) {
                    Text("검색")
                }
            }

            if (state.isInitialLoading) {
                CircularProgressIndicator()
            }

            if (state.isSearching) {
                Text(
                    text = "검색 중...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            state.errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            MovieSection(title = "인기 영화", movies = state.popularMovies)
            MovieSection(title = "최신 영화", movies = state.latestMovies)
            if (state.searchMovies.isNotEmpty()) {
                MovieSection(title = "검색 결과", movies = state.searchMovies)
            }
        }
    }
}

@Composable
private fun MovieSection(
    title: String,
    movies: List<MovieSearchScreen.MovieItem>,
) {
    Text(text = title, style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(4.dp))
    LazyColumn(
        modifier = Modifier.height(180.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(movies, key = { it.id }) { movie ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 2.dp,
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                    if (movie.releaseDate.isNotBlank()) {
                        Text(
                            text = movie.releaseDate,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}
