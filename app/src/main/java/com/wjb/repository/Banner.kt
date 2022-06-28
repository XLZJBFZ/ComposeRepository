package com.wjb.repository

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

/**
 * ClassName: Rotation
 * Description:
 * Author: CSC
 */
/**
 *
 *
 * @param modifier
 * @param indicatorSize 指示器的大小，默认4dp
 * @param indicatorColor 指示器的颜色，默认红色
 * @param pageCount 轮播页数
 * @param waitingTime 轮播间隔，默认两秒
 * @param content 轮播内容
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 4.dp,
    indicatorColor: Color = Color.Red,
    pageCount: Int,
    waitingTime: Long = 2000L,
    content: @Composable PagerScope.(page: Int) -> Unit
) {
    val state = rememberPagerState(1)
    var wait by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(wait) {
        if (!state.isScrollInProgress) {
            delay(waitingTime)
            if (!state.isScrollInProgress) {
                state.animateScrollToPage(state.currentPage + 1)
            }
        }
        wait = !wait
    }

    LaunchedEffect(state.currentPage) {
        when (state.currentPage) {
            0 -> {
                state.scrollToPage(pageCount)
                wait = !wait
            }
            pageCount + 1 -> {
                state.scrollToPage(1)
                wait = !wait
            }
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(count = pageCount + 2, state = state) {
            when (it) {
                0 -> content(pageCount)
                pageCount + 1 -> content(1)
                else -> content(it)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp), horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) {
                Spacer(
                    modifier = Modifier
                        .size(indicatorSize)
                        .clip(CircleShape)
                        .background(
                            if (it + 1 == when (state.currentPage) {
                                    0 -> pageCount
                                    pageCount + 1 -> 1
                                    else -> state.currentPage
                                }
                            ) indicatorColor else Color.White
                        )
                )
                if (it != pageCount - 1) {
                    Spacer(modifier = Modifier.size(indicatorSize))
                }

            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewBanner() {
    Banner(
        modifier = Modifier
            .width(200.dp)
            .height(100.dp),
        pageCount = 4
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Green.copy(alpha = 0.5f))
                .border(BorderStroke(1.dp, Color.Black)),
            contentAlignment = Alignment.Center
        ) { Text("第${it}张图") }
    }
}