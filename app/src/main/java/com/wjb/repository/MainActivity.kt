package com.wjb.repository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wjb.repository.ui.theme.Purple40
import com.wjb.repository.ui.theme.RepositoryTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepositoryTheme {
                NavHost()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Preview(showBackground = true)
    @Composable
    fun PreviewContent() {
        Main(navController = rememberAnimatedNavController())
    }


    @Composable
    private fun Main(navController: NavHostController) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            item {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(route = RouteConfig.ROUTE_BOTTOM_SELECTOR)
                }) {
                    Text(text = "BottomSelectorSample")
                }
            }
            item {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(route = RouteConfig.ROUTE_NAVIGATION_WITH_INDICATOR)
                }) {
                    Text(text = "NavigationWithIndicatorSample")
                }
            }
            item {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(route = RouteConfig.ROUTE_BANNER)
                }) {
                    Text(text = "BannerSample")
                }
            }
            item {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(route = RouteConfig.ROUTE_FADE_EDGE)
                }) {
                    Text(text = "FadeEdgeSample")
                }
            }
            item {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(route = RouteConfig.ROUTE_HIDE_TOP_WHEN_SCROLL)
                }) {
                    Text(text = "HideTopWhenScrollSample")
                }
            }
            item {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(route = RouteConfig.ROUTE_LONG_PRESS_ICON_WITH_PROGRESS)
                }) {
                    Text(text = "LongPressIconWithProgressSample")
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun NavHost() {
        val navController = rememberAnimatedNavController()
        AnimatedNavHost(
            navController = navController,
            startDestination = RouteConfig.ROUTE_MAIN
        ) {
            composable(RouteConfig.ROUTE_MAIN,
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }) {
                Main(navController)
            }
            composable(RouteConfig.ROUTE_BOTTOM_SELECTOR,
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }) {
                BottomSelectorSample(navController)
            }
            composable(RouteConfig.ROUTE_NAVIGATION_WITH_INDICATOR,
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }) {
                NavigationWithIndicatorSample(navController)
            }
            composable(RouteConfig.ROUTE_BANNER,
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }) {
                BannerSample(navController)
            }
            composable(RouteConfig.ROUTE_FADE_EDGE,
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }) {
                FadeEdgeSample(navController)
            }
            composable(RouteConfig.ROUTE_HIDE_TOP_WHEN_SCROLL,
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }) {
                HideTopWhenScrollSample(navController)
            }

            composable(RouteConfig.ROUTE_LONG_PRESS_ICON_WITH_PROGRESS,
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }) {
                LongPressIconWithProgressSample(navController)
            }
        }
    }

    @Composable
    private fun FadeEdgeSample(navController: NavHostController) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .horizontalFadeEdge(fadeWidth = 50.dp)){
                items(20){
                    Text(text = it.toString(),modifier=Modifier.width(50.dp))
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun HideTopWhenScrollSample(navController: NavHostController) {
        val pagerState = rememberPagerState(2)
        val scope = rememberCoroutineScope()
        val list = listOf("??????1", "??????2", "??????3", "??????4", "??????5")
        HideTopWhenScroll(topSize = 40.dp, topContent = {
            NavigationWithIndicator(
                backgroundColor = Color.Red,
                size = 40.dp,
                titleList = list,
                pagerState = pagerState,
                scope = scope,
            )
        }) { paddingValues ->
            HorizontalPager(count = list.size, state = pagerState) {
                LazyColumn(contentPadding = paddingValues) {
                    items(100) { index ->
                        androidx.compose.material3.Text(
                            "I'm item $index", modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun LongPressIconWithProgressSample(navController: NavHostController) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            var finished by remember {
                mutableStateOf(false)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LongPressIconWithProgress(modifier = Modifier.size(100.dp),finished = finished, imageVector = Icons.Filled.Star, iconColor = Color.Red) {
                    finished=true
                }
                Button(onClick = { finished=false }) {
                    Text(text = "????????????")
                }
            }

        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun BannerSample(navController: NavHostController) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Banner(
                pageCount = 3,
                modifier = Modifier
                    .size(200.dp, 100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = it.toString())
                }
            }
        }

    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun NavigationWithIndicatorSample(navController: NavHostController) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {
            val pagerState = rememberPagerState(2)
            val scope = rememberCoroutineScope()
            val list = listOf("??????1", "??????2", "??????3", "??????4", "??????5")
            NavigationWithIndicator(
                titleList = list,
                pagerState = pagerState,
                scope = scope,
            )
            HorizontalPager(count = list.size, state = pagerState) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = list[it])
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun BottomSelectorSample(navController: NavHostController) {
        val list = listOf("?????????", "?????????", "?????????")
        val map1 = hashMapOf(
            Pair("?????????", listOf("?????????", "?????????", "?????????")),
            Pair("?????????", listOf("?????????", "?????????")),
            Pair("?????????", listOf("?????????", "?????????", "?????????", "?????????"))
        )

        val map2 = hashMapOf(
            Pair("?????????", listOf("?????????", "?????????")),
            Pair("?????????", listOf("?????????1", "?????????2")),
            Pair("?????????", listOf("?????????1")),
            Pair("?????????", listOf("?????????1")),
            Pair("?????????", listOf("?????????1")),
            Pair("?????????", listOf("?????????1")),
            Pair("?????????", listOf("?????????1")),
            Pair("?????????", listOf("?????????1")),
            Pair("?????????", listOf("?????????1")),
        )
        var result by remember {
            mutableStateOf("")
        }
        BottomSelector(
            onAllSelected = { str, pos -> result = "str:$str\npos:$pos" },
            "????????????",
            first = list,
            other = arrayOf(map1, map2)
        ) { state ->
            LaunchedEffect(Unit) {
                state.show()
            }
            LaunchedEffect(result) {
                delay(10000)
                navController.navigateUp()
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = result, textAlign = TextAlign.Center, fontSize = 20.sp)
            }

        }
    }
}

