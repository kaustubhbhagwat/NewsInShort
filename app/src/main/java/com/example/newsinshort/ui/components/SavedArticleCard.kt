package com.example.newsinshort.ui.components

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import com.example.newsinshort.R
import com.example.newsinshort.data.database.SavedNewsViewModel
import com.example.newsinshort.data.database.model.SavedArticle
import com.example.newsinshort.utils.dateFormatter

@Composable
fun SavedArticleCard(
    modifier: Modifier = Modifier,
    article: SavedArticle,
    navController: NavController,
    savedNewsViewModel: SavedNewsViewModel = hiltViewModel()
) {
    val argKey = "webUrl"
    val context = LocalContext.current

    if (article != null) {
        val date = dateFormatter(article.publishedAt)
        val animatable = remember {
            Animatable(0.5f)
        }
        LaunchedEffect(key1 = true) {
            animatable.animateTo(1f, tween(350, easing = LinearEasing))
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
                .clickable {
                    navController.navigate("article_screen?${argKey}=${article.url}")
                }) {
            if (article.urlToImage != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .scale(Scale.FILL)
                        .precision(Precision.EXACT)
                        .data(article.urlToImage)
                        .crossfade(500)
                        .build(),
                    placeholder = painterResource(R.mipmap.news_placeholder_img),
                    contentDescription = stringResource(R.string.image_description),
                    contentScale = ContentScale.FillWidth
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .scale(Scale.FILL)
                        .precision(Precision.EXACT)
                        .data(R.mipmap.news_placeholder_img)
                        .crossfade(500)
                        .build(),
                    contentDescription = stringResource(R.string.image_description),
                    contentScale = ContentScale.FillWidth
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .background(color = Color.White)
                )
                Text(
                    text = article.title.toString(),
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
                    Column {
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
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        Modifier.clickable {
                            savedNewsViewModel.deleteArticle(article.url)
                            navController.navigateUp()
                            Toast
                                .makeText(
                                    context,
                                    "Article deleted",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        })
                }
            }
        }
    }

}