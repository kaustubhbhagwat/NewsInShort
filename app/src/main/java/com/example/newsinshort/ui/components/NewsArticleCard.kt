package com.example.newsinshort.ui.components

import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun NewsArticleCard(
    modifier: Modifier = Modifier, article: Article, onCardClicked: (Article) -> Unit,
    savedNewsViewModel: SavedNewsViewModel = hiltViewModel()

) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val allUrls: ArrayList<String> = ArrayList()
    val checkedState = remember { mutableStateOf(false) }

    savedNewsViewModel.allSavedNews.observeForever {
        for (i in it) {
            allUrls.add(i.url)
        }
    }
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
                        .precision(Precision.EXACT).data(article.urlToImage).crossfade(100).build(),
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
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                        Text(
                            text = date,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
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
                    // on below line we are creating icon toggle button.
                        if (allUrls.contains(article.url)) {
                            checkedState.value = true
                        }
                    IconToggleButton(
                        // on below line we are
                        // specifying default check state
                        checked = checkedState.value,
                        // on below line we are adding on check change
                        onCheckedChange = {
                            checkedState.value = !checkedState.value
                            if (allUrls.isEmpty()) {
                                savedNewsViewModel.saveNews(savedArticle)
                                Toast
                                    .makeText(
                                        context,
                                        "Article has been saved",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }

                            if (allUrls.contains(savedArticle.url)) {
                                Toast
                                    .makeText(
                                        context,
                                        "Article already saved",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        "Article has been saved",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                                savedNewsViewModel.saveNews(savedArticle)
                            }
                        },
                        // on below line we are adding a padding
                        modifier = Modifier.padding(0.dp)
                    ) {
                            // on below line we are creating a
                            // variable for our transition
                            val transition = updateTransition(checkedState.value)

                            // on below line we are creating a variable for
                            // color of our icon
                            val tint by transition.animateColor(label = "iconColor") { isChecked ->
                                // if toggle button is checked we are setting color as red.
                                // in else condition we are setting color as black
                                if (isChecked) Color.Red else Color.Black
                            }
                            // om below line we are specifying transition
                            val size by transition.animateDp(
                                transitionSpec = {
                                    // on below line we are specifying transition
                                    if (false isTransitioningTo true) {
                                        // on below line we are specifying key frames
                                        keyframes {
                                            // on below line we are specifying animation duration
                                            durationMillis = 250
                                            // on below line we are specifying animations.
                                            30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                                            35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                                            40.dp at 75 // ms
                                            35.dp at 150 // ms
                                        }
                                    } else {
                                        spring(stiffness = Spring.StiffnessVeryLow)
                                    }
                                },
                                label = "Size"
                            ) { 30.dp }

                            // on below line we are creating icon for our toggle button.
                            Icon(
                                // on below line we are specifying icon for our image vector.
                                imageVector = if (checkedState.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Icon",
                                // on below line we are specifying
                                // tint for our icon.
                                tint = tint,
                                // on below line we are specifying
                                // size for our icon.
                                modifier = Modifier.size(size)
                            )
                        }
                    }
                }
            }
        }
    }
}