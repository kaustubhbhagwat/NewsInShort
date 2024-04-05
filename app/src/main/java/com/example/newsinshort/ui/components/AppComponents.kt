package com.example.newsinshort.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsinshort.R
import com.example.newsinshort.data.entity.Article
import com.example.newsinshort.data.entity.Source
import com.example.newsinshort.ui.theme.Purple40
import com.example.newsinshort.ui.theme.ButtonColor


@Composable
fun Loader() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CircularProgressIndicator(
            modifier =
            Modifier
                .size(60.dp)
                .padding(10.dp),
            color = Purple40
        )
    }
}

@Composable
fun NormalTextComponent(textValue: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        text = textValue,
        color = Color.LightGray,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Monospace,
            color = Purple40
        )
    )
}

@Composable
fun NewsRowComponent(page: Int, article: Article) {
    Surface(
        modifier = Modifier
            .background(Color.DarkGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        )
        {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                model = article.urlToImage,
                contentDescription = article.content,
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.logo),
                error = painterResource(id = R.drawable.logo)
            )
            Spacer(modifier = Modifier.size(20.dp))
            HeadingTextComponent(textValue = article.title ?: "")
            Spacer(modifier = Modifier.size(10.dp))
            NormalTextComponent(textValue = article.description ?: "")
            Spacer(modifier = Modifier.size(20.dp))
            //Author Title
            AuthorSourceTitleComponent(
                authorName = "Author",
                sourceName = "Source"
            )
            // Author and Source
            AuthorDetailComponent(
                authorName = article.author,
                sourceName = article.source?.name
            )

            //Open News URL
            // on below line adding a spacer.
            val ctx = LocalContext.current
            // on below line adding a button to open URL
            Button(colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(article.url)
                    )
                    ctx.startActivity(urlIntent)
                }) {
                // on below line creating a text for our button.
                Text(
                    // on below line adding a text ,
                    // padding, color and font size.
                    text = "Read Full Story",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
}


@Composable
fun HeadingTextComponent(textValue: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        color = Color.White,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun AuthorDetailComponent(authorName: String?, sourceName: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 24.dp)
    ) {
        authorName?.also {
            Text(
                text = it, color = Color.LightGray,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        sourceName?.also {
            Text(
                text = it,
                color = Color.LightGray,
            )
        }
    }
}

@Composable
fun AuthorSourceTitleComponent(authorName: String?, sourceName: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
    ) {
        authorName?.also {
            Text(
                text = it, color = Color.LightGray,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        sourceName?.also {
            Text(
                text = it,
                color = Color.LightGray,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}
@Preview
@Composable
fun NewsRowComponentPreview() {
    val article = Article(
        author = "Kaustubh",
        title = "Title",
        "asdasd2iu3eoiasdkasdnkasnd asdojasd",
        "null",
        "https://img.freepik.com/free-vector/indian-flag-theme-independence-day-decorative-background-vector_1055-10866.jpg?w=1800&t=st=1711707331~exp=1711707931~hmac=78d732f7370925905e8160e19fdc89e51b865676a5613ea2b2ec8b46e47ef76c",
        "null",
        "null",
        Source("1", "asdads"),
    )
    NewsRowComponent(page = 0, article = article)
}

@Preview
@Composable
fun EmptySpaceComponentPreview() {
    EmptySpaceComponentPreview()
}
