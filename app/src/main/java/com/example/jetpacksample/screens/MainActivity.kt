package com.example.jetpacksample.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetpacksample.MainViewModel
import com.example.jetpacksample.Navigation
import com.example.jetpacksample.R
import com.example.jetpacksample.model.Articles
import com.example.jetpacksample.navigation.Screen
import com.example.jetpacksample.ui.theme.JetpackSampleTheme
import com.example.jetpacksample.ui.theme.OffGrey
import com.example.jetpacksample.ui.theme.OffWhite
import com.example.jetpacksample.ui.theme.color_464646
import com.google.gson.Gson

class MainActivity : ComponentActivity() {

    private val articleViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = color_464646) {
                    Navigation(articleViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String,mainViewModel: MainViewModel, navController: NavController) {
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = name,
            fontSize = 29.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(12.dp)
        )

        showList(mainViewModel.articleListResponse,navController = navController)
        mainViewModel.getArticles()

}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackSampleTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = color_464646) {
//            Greeting("HEADLINES")

            val articles= Articles("Title 1","Description 1","https://i.gadgets360cdn.com/large/tesla_model_y_elon_musk_germany_gigafactory_reuters_1648011766646.jpg","Date1")
            val articles2= Articles("Title 2","Description 2","https://c.ndtvimg.com/2022-01/cf2bn2i_tesla-logo-bloomberg-650_625x300_18_January_22.jpg","Date2")
            val articles3= Articles("Title 3","Description 3","https://blogsmedia.lse.ac.uk/blogs.dir/99/files/2022/03/pipeline.jpg","Date3")

            val list= mutableListOf<Articles>()
            list.add(articles)
            list.add(articles2)
            list.add(articles3)
//            showList(list)
        }
    }
}

@Composable
fun showList(list: List<Articles?>?,navController: NavController){

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        items(list!!){messages->

            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.DetailScreen.route + "/${messages?.title}")
                    }
            ) {

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)){

                    val painter =
                        rememberImagePainter(
                            data = messages?.urlToImage,
                            builder = {

                            }
                        )

                    val painterstate = painter.state
                    Image(painter = painter,
                        contentDescription = "LOGO",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                        )

                    Column(
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {

                        messages?.title?.let {
                            Text(
                                text = it,
                                fontSize = 14.sp,
                                color = OffWhite,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.h1,
                                modifier = Modifier.padding(12.dp)
                            )
                        }

                        if (messages != null) {
                            messages.publishedAt?.let {
                                Text(
                                    text = it,
                                    fontSize = 12.sp,
                                    color = OffWhite,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h1,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun detailScreen(viewModel: MainViewModel,navController: NavController, newsTitle:String?){

    viewModel.getDetails(newsTitle)
    val articleDetails = viewModel.articleDetailResponse.observeAsState()

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()){


        val painter =
            rememberImagePainter(
                data =  articleDetails.value?.urlToImage,
                builder = {

                }
            )

        val painterstate = painter.state
        Image(painter = painter,
            contentDescription = "LOGO",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

        Image(
            painterResource(R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(62.dp)
                .width(62.dp)
                .padding(12.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ){

            articleDetails.value?.title?.let {
                Text(
                    text = it,
                    fontSize = 29.sp,
                    color = OffWhite,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(12.dp)
                )
            }

            articleDetails.value?.publishedAt?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    color = OffWhite,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(12.dp)
                        .align(Alignment.End)
                )
            }

            articleDetails.value?.description?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = OffGrey,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }


    }
}