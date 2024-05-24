package com.example.newsinshort.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.newsinshort.data.entity.Article
import com.example.newsinshort.utils.dateFormatter


@Composable
fun NewsArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onCardClicked: (Article) -> Unit
) {
    val date = dateFormatter(article.publishedAt)
    val animatable = remember {
        Animatable(0.5f)
    }
    LaunchedEffect(key1 = true) {
        animatable.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
    }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        ),
        modifier = modifier
            .padding(12.dp)
            .background(Color.Black)
            .shadow(elevation = 50.dp, spotColor = Color.White)
            .graphicsLayer {
                this.scaleX = animatable.value
                this.scaleY = animatable.value
            }
            .clickable { onCardClicked(article) }) {
        ImageHolder(imageUrl = article.urlToImage)
        Column(modifier = Modifier.padding(12.dp)) {
            Spacer(modifier = Modifier.height(8.dp)
                .background(color = Color.White)
            )
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.source?.name ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }
        }
    }

}