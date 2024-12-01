package banquemisr.challenge05.movies.ui.movieList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import banquemisr.challenge05.movies.ui.common.getListScreenEventByTabIndex
import banquemisr.challenge05.movies.ui.movieList.components.HorizontalMovieList
import banquemisr.challenge05.movies.ui.movieList.components.MovieTabs

@Composable
fun MovieTabsScreen(
    viewModel: ListScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value // Collect the ViewModel state
    val lazyListState = rememberLazyListState()
    ChangeStatusBarColor()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = Color.Black,
        contentColor = Color.Black,

        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp), // Ensure the Box takes full width
                contentAlignment = Alignment.Center // Center content horizontally
            ) {
                MovieTabs(
                    selectedTabIndex = viewModel.selectedTabIndex,
                    onTabSelected = { index ->
                        viewModel.selectedTabIndex = index
                        viewModel.handleEvent(index.getListScreenEventByTabIndex())
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
                            isLoadingNextPage = state.isLoadingNextPage,
                            loadNextPage = { viewModel.handleEvent(ListScreenEvent.LoadNextPage) },
                            lazyListState = lazyListState
                        )
                    }

                    is ListScreenState.Loading -> {
                        // Show a loading screen (initial loading)
                        CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
                    }

                    is ListScreenState.Error -> {
                        // Show an error message
                        Text("Failed to load movies", modifier = Modifier.padding(paddingValues))
                    }
                }
            }
        })
}
