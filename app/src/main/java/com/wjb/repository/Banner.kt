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
 * @param indicatorColor 指示器的颜色，默认红色
 * @param pageCount 轮播页数
 * @param waitingTime 轮播间隔，默认两秒
 * @param content 轮播内容
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    indicatorColor:Color=Color.Red,
    pageCount: Int,
    waitingTime: Long = 2000L,
    content: @Composable PagerScope.(page: Int) -> Unit
) {
    val startIndex = 1000000
    val state = rememberPagerState(startIndex)
    rememberPagerState()

    var currentIndex by remember {
        mutableStateOf(startIndex)
    }
    var currentPage by remember {
        mutableStateOf(0)
    }
    var lastIndex by remember {
        mutableStateOf(startIndex)
    }
    var wait by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(currentPage, wait) {
        if (!state.isScrollInProgress) {
            delay(waitingTime)
            if (!state.isScrollInProgress) {
                state.animateScrollToPage(state.currentPage + 1)
            } else {
                wait = !wait
            }
        } else {
            delay(100)
            wait = !wait
        }
    }

    LaunchedEffect(state.currentPage) {
        delay(200)
        when {
            lastIndex < state.currentPage -> currentIndex++
            lastIndex > state.currentPage -> currentIndex--
        }
        lastIndex = state.currentPage
        currentPage = (currentIndex-startIndex).floorMod(pageCount)
    }

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(count = 2000000, state = state) { pager ->
            val page = (pager - startIndex).floorMod(pageCount)
            content(page)
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp), horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) {
                Spacer(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(if (it == currentPage) indicatorColor else Color.White)
                )
                Spacer(modifier = Modifier.size(4.dp))
            }
        }
    }
}

private fun Int.toPage(pageCount: Int): Int = when {
    this >= 0 -> this % pageCount
    else -> pageCount - this.absoluteValue % pageCount
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewRotation() {
    Banner(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        pageCount = 6
    ) {
        when (it) {
            0 -> Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f)).border(BorderStroke(1.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) { Text("第一张图") }
            1 -> Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f)).border(BorderStroke(1.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) { Text("第二张图") }
            2 -> Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f)).border(BorderStroke(1.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) { Text("第三张图") }
            3 -> Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f)).border(BorderStroke(1.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) { Text("第四张图") }
            4 -> Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f)).border(BorderStroke(1.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) { Text("第五张图") }
            5 -> Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f)).border(BorderStroke(1.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) { Text("第六张图") }
            else -> Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f)).border(BorderStroke(1.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) { Text("其他图") }
        }
    }
}

