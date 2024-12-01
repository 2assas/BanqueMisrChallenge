package banquemisr.challenge05.movies.ui.movieList.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import banquemisr.challenge05.domain.entities.Movie
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun HorizontalMovieList(
    movies: List<Movie>,
    isLoadingNextPage: Boolean,
    modifier: Modifier,
    loadNextPage: () -> Unit,
    lazyListState: LazyListState,
    onItemClick: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(movies, key = { it.id }) { movie ->
           AnimatedVisibility(true) {
                MovieItem(movie) {
                    onItemClick(movie.id)
                }
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
    // Handle infinite scrolling
    InfiniteRowHandler(listState = lazyListState, isLoadingNextPage) {
        loadNextPage()
    }

}

@Composable
fun InfiniteRowHandler(
    listState: LazyListState,
    isLoadingNextPage: Boolean,
    buffer: Int = 5,
    onLoadMore: () -> Unit
) {

    // Derived state to determine when to load more items
    val shouldLoadMore = remember {
        derivedStateOf {
            // Total number of items in the list
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            // Index of the last visible item
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            // Check if we have scrolled near the end
            lastVisibleItemIndex >= (totalItemsCount - buffer)
        }
    }
    // Launch a coroutine when shouldLoadMore becomes true
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value && !isLoadingNextPage }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                onLoadMore()
            }
    }
}
