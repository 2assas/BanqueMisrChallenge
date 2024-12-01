package banquemisr.challenge05.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import banquemisr.challenge05.movies.ui.navigation.Destinations
import banquemisr.challenge05.movies.ui.navigation.movieDetailScreen
import banquemisr.challenge05.movies.ui.navigation.movieTabsScreen
import banquemisr.challenge05.movies.ui.theme.BanqueMisrChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BanqueMisrChallengeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.MOVIE_TABS_SCREEN) {
        // Setting up the navigation graph
        movieTabsScreen(navController = navController)
        movieDetailScreen(navController = navController)
    }
}