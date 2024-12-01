package banquemisr.challenge05.movies.ui.movieList.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import banquemisr.challenge05.domain.entities.Movie

@Composable
fun HorizontalMovieList(
    movies: List<Movie>,
    isLoadingNextPage: Boolean,
    modifier: Modifier,
    loadNextPage: () -> Unit,
    lazyListState: LazyListState
) {
    LaunchedEffect(lazyListState) {
        Log.e("Pagination", "state changed")
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastItemIndex = visibleItems.lastOrNull()?.index
                Log.e("Pagination", "Last visible item index: $lastItemIndex" + " movieSize: ${movies.size} ")

                lastItemIndex?.let {
                    if (it >= movies.size - 3 && !isLoadingNextPage) {
                        loadNextPage()
                    }
                }
            }
    }
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState ,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(movies) { movie ->
            MovieItem(movie) {

            }
        }

        // Progress bar at the end of the list for pagination
        if (isLoadingNextPage) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
