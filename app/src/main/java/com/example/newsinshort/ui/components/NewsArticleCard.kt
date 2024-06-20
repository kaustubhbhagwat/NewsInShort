package com.example.newsinshort.ui.components

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Observer
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import com.example.newsinshort.R
import com.example.newsinshort.data.database.SavedNewsViewModel
import com.example.newsinshort.data.database.entities.Article
import com.example.newsinshort.data.database.model.SavedArticle
import com.example.newsinshort.data.database.model.Source
import com.example.newsinshort.utils.dateFormatter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun NewsArticleCard(
    modifier: Modifier = Modifier, article: Article, onCardClicked: (Article) -> Unit
) {
    val savedNewsViewModel: SavedNewsViewModel = hiltViewModel()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()





    if (article.content != null) {
        val date = dateFormatter(article.publishedAt)
        val animatable = remember {
            Animatable(0.5f)
        }
        LaunchedEffect(key1 = true) {
            animatable.animateTo(1f, tween(350, easing = LinearEasing))
        }

        ElevatedCard(colors = CardDefaults.cardColors(
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
            if (article.urlToImage != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .diskCachePolicy(CachePolicy.ENABLED).scale(Scale.FILL)
                        .precision(Precision.EXACT).data(article.urlToImage).crossfade(500).build(),
                    placeholder = painterResource(R.mipmap.news_placeholder_img),
                    contentDescription = stringResource(R.string.image_description),
                    contentScale = ContentScale.FillWidth
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .diskCachePolicy(CachePolicy.ENABLED).scale(Scale.FILL)
                        .precision(Precision.EXACT).data(R.mipmap.news_placeholder_img)
                        .crossfade(500).build(),
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
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
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
                            color = Color.Gray
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_favorite_unselected),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                val savedArticle: SavedArticle
                                article.let {
                                    savedArticle = SavedArticle(
                                        title = it.title,
                                        author = it.author,
                                        description = it.description,
                                        content = it.content,
                                        publishedAt = it.publishedAt,
                                        source = Source(
                                            name = it.source?.name.toString(),
                                            id = it.source?.id.toString()
                                        ),
                                        url = it.url,
                                        urlToImage = it.urlToImage,
                                    )
                                }
                                savedNewsViewModel.saveNews(savedArticle)

                                coroutineScope.launch {
                                    val ifExist1 = savedNewsViewModel.isRowExist(savedArticle.url).start()
                                    val ifExist2 = savedNewsViewModel.isRowExist(savedArticle.url).start()
                                }


//                                savedNewsViewModel.allSavedNews.observe(lifecycleOwner) {
//                                    it.forEachIndexed { index, newArticle ->
//                                        if (savedArticle.url == it[index].url) {
//                                            Toast
//                                                .makeText(
//                                                    context,
//                                                    "Article Already Saved",
//                                                    Toast.LENGTH_LONG
//                                                )
//                                                .show()
//                                        } else {
//                                            Toast
//                                                .makeText(
//                                                    context,
//                                                    "Article Saved",
//                                                    Toast.LENGTH_LONG
//                                                )
//                                                .show()
//                                        }
//                                    }
//                                }
                            })
                }
            }
        }
    }
}