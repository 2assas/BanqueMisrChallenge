package banquemisr.challenge05.movies.ui.movieDetail.components

import android.annotation.SuppressLint
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import banquemisr.challenge05.domain.entities.MovieDetail
import coil.compose.rememberAsyncImagePainter

@Composable
fun MovieDetailsContent(movie: MovieDetail) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val imageHeight = screenHeight * 0.4f
    val cardHeight = imageHeight * 0.30f
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            movie.posterPath?.let {
                MoviePosterWithBackButton(
                    posterPath = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f)
                        .height(imageHeight)
                ) {
                    backPressedDispatcher?.onBackPressed()
                }
            }

            MovieTitleAndRating(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primary)
                    .offset(y = (-cardHeight * 0.40f)),
                movie = movie
            )

            DetailsSection(movie = movie)

        }
    }
}

@Composable
fun MoviePosterWithBackButton(posterPath: String, modifier: Modifier, onBackPressed: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = rememberAsyncImagePainter(posterPath),
            contentDescription = null,
            modifier = modifier
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 24.dp)
        ) {
            Box(
                Modifier.background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 10.dp)
                )
            ) {
                IconButton(
                    onClick = onBackPressed,
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
        }
    }
}

@Composable
fun MovieTitleAndRating(modifier: Modifier, movie: MovieDetail) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.tertiary),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inversePrimary

                )
                Text(
                    text = "${movie.runtime} • ${movie.releaseDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
                Box(
                    modifier = Modifier.fillMaxWidth(), // Ensures the box spans the full width
                    contentAlignment = Alignment.Center // Centers the column inside the box
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RatingText(rating = movie.rating ?: 0.0, votes = movie.voteCount ?: 0)
                    }
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun RatingText(rating: Double, votes: Int) {
    val yellowStar = "\u2605" // Unicode for ★

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Review")
        }
        append("  ")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp
            )
        ) {
            append(yellowStar)
        }
        append(" ")

        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(String.format("%.1f", rating))
        }
        append(" ")

        withStyle(style = SpanStyle(color = Color.Gray, fontSize = 14.sp)) {
            append("($votes)")
        }
    }

    Text(
        text = annotatedString,
        modifier = Modifier.padding(8.dp),
        color = MaterialTheme.colorScheme.inversePrimary
    )
}

@Composable
fun DetailsSection(movie: MovieDetail) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Genre and Language
        Text(
            text = "Genre: ${movie.genres}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.inversePrimary
        )
        Text(
            text = "Language: ${movie.language}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Story Line", style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.inversePrimary
        )
        movie.overview?.let { overview ->
            ExpandableOverviewText(
                overview = overview,
                isExpanded = isExpanded,
                onExpandToggle = { isExpanded = !isExpanded },
                maxLines = 3
            )
        }
    }
}
