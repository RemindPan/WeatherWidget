package com.tinyfight.weather.widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tinyfight.weather.widget.ui.theme.*
import com.tinyfight.weather.widget.viewmodel.MainState
import com.tinyfight.weather.widget.viewmodel.MainViewModel
import com.tinyfight.weather.widget.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels { ViewModelFactory(this) }
    private var state: MainState by mutableStateOf(MainState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherWidgetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                    contentColor = LightBlue600
                ) {
                    WeatherScreen(
                        state = state,
                        onBackButtonPressed = { finish() }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.mainState.observe(this) {
            state = it
        }
        mainViewModel.requestWeather()
    }

}

@Composable
fun WeatherScreen(
    state: MainState,
    onBackButtonPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "天气预报",
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .clickable {
                                onBackButtonPressed()
                            }
                    )
                },
                backgroundColor = LightBlue200
            )
        },
        backgroundColor = LightBlue600
    ) { paddingValues ->
        if (state.isRequesting) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Teal200,
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 6.dp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues.withMinimum(minimumPadding = 32.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "常州",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 38.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "${state.temp}°C",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 38.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = state.weather,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )

//            LazyRow(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 40.dp)
//            ) {
//                items(count = state.hourWeatherList.size) { index ->
//                    val modifier = if (index == 0) {
//                        Modifier
//                    } else {
//                        Modifier.padding(start = 20.dp)
//                    }
//                    Column(
//                        modifier = modifier,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(text = state.hourWeatherList[index].temp, color = Color.White)
//                        Text(text = state.hourWeatherList[index].text, color = Color.White)
//                    }
//                }
//            }

                LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                    items(count = state.dailyWeatherList.size + 1) { index ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.weight(1F)) {
                                Text(
                                    text = if (index == 0) {
                                        "日期"
                                    } else {
                                        dayOfWeek(index - 1)
                                    },
                                    color = Color.White,
                                )
                            }

                            Box(modifier = Modifier.weight(1F)) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd),
                                    text = if (index == 0) {
                                        "天气情况"
                                    } else {
                                        state.dailyWeatherList[index - 1].textDay
                                    },
                                    color = Color.White
                                )
                            }

                            Box(modifier = Modifier.weight(1F)) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd),
                                    text = if (index == 0) {
                                        "最低温度"
                                    } else {
                                        "${state.dailyWeatherList[index - 1].tempMin}°C"
                                    },
                                    color = Color.White
                                )

                            }

                            Box(modifier = Modifier.weight(1F)) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd),
                                    text = if (index == 0) {
                                        "最高温度"
                                    } else {
                                        "${state.dailyWeatherList[index - 1].tempMax}°C"
                                    },
                                    color = Color.White
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
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherWidgetTheme {
        WeatherScreen(
            state = MainState(),
            onBackButtonPressed = {}
        )
    }
}