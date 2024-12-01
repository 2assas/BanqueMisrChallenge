package banquemisr.challenge05.movies.ui.movieList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import banquemisr.challenge05.movies.ui.common.ChangeStatusBarColor
import banquemisr.challenge05.movies.ui.common.getListScreenIntentByTabIndex
import banquemisr.challenge05.movies.ui.movieList.components.MovieTabs
import banquemisr.challenge05.movies.ui.movieList.components.HorizontalMovieList
import banquemisr.challenge05.movies.ui.navigation.navigateToMovieDetailScreen

@Composable
fun MovieTabsScreen(
    viewModel: ListScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value
    ChangeStatusBarColor(MaterialTheme.colorScheme.primary)
    val lazyListState = rememberLazyListState(viewModel.selectedTabIndex)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = Color.Black,
        contentColor = Color.Black,

        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                MovieTabs(
                    selectedTabIndex = viewModel.selectedTabIndex,
                    onTabSelected = { index ->
                        viewModel.selectedTabIndex = index
                        viewModel.handleIntent(ListScreenIntent.ClearMovies)
                        viewModel.handleIntent(index.getListScreenIntentByTabIndex())
                    })
            }
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .padding(vertical = 30.dp)
            ) {
                when (state) {
                    is ListScreenState.Success -> {
                        HorizontalMovieList(
                            movies = state.movies,
                            modifier = Modifier
                                .padding(paddingValues)
                                .align(Alignment.TopCenter),
                            loadNextPage = {
                                viewModel.handleIntent(ListScreenIntent.LoadNextPage)
                            },
                            lazyListState = lazyListState,
                            isLoadingNextPage = state.isLoadingNextPage,
                            onItemClick = { id ->
                                navController.navigateToMovieDetailScreen(movieId = id)
                            }
                        )
                    }

                    is ListScreenState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
                    }

                    is ListScreenState.Error -> {
                        Text("Failed to load movies", modifier = Modifier.padding(paddingValues))
                    }
                }
            }
        })
}
