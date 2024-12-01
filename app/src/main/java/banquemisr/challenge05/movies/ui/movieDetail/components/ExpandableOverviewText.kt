package banquemisr.challenge05.movies.ui.movieDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

@Composable
fun ExpandableOverviewText(
    overview: String,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    maxLines: Int = 4
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val annotatedText = buildAnnotatedString {
            append(overview)
            append(" ")
            pushStringAnnotation(tag = "EXPAND_TOGGLE", annotation = "ExpandToggle")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                append(if (isExpanded) "See Less" else "See More")
            }
            pop()
        }

        Text(
            text = annotatedText,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier.clickable { onExpandToggle() }
        )
    }
}
