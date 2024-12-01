package banquemisr.challenge05.movies.ui.movieList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MovieTabs(onTabSelected: (Int) -> Unit, selectedTabIndex: Int) {
    val tabs = listOf("Now Playing", "Popular", "Upcoming")
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val width = screenWidth * 0.9f
    Box(
        modifier = Modifier
            .width(width)
            .height(55.dp)
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(4.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.tertiary,
            indicator = {},
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                val selected = selectedTabIndex == index
                Tab(
                    selected = selected,
                    onClick = { if (!selected) onTabSelected(index) }
                ) {
                    Box(
                        modifier = Modifier
                            .width(130.dp)
                            .height(48.dp)
                            .background(
                                color = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 22.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selected) Color.Black else Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}
