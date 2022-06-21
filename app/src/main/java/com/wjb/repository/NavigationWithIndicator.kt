package com.wjb.repository

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * ClassName: NavigationWithIndicator
 * Description:
 * Author: CSC
 */

enum class Direction {
    RIGHT, LEFT, NONE
}

/**
 * 带滑块的导航指示器，可与HorizontalPager配合使用
 *
 * @param titleList 标题列表
 * @param pagerState 滑动状态
 * @param scope 协程
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationWithIndicator(
    titleList: List<String>,
    indicatorColor: Color = Color.Blue,
    pagerState: PagerState,
    scope: CoroutineScope
) {//todo:多次滑动后指示器失效
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp//不一定是全屏
    val one = screenWidth / titleList.size / 2
    val slideWidth = one / 3
    var offset by remember {
        mutableStateOf(one + one * 2 * pagerState.currentPage - slideWidth / 2)
    }
    var offsetTarget by remember {
        mutableStateOf(one + one * 2 * pagerState.currentPage - slideWidth / 2)
    }
    var currentDirection by remember {
        mutableStateOf(Direction.NONE)
    }
    var currentIndexOffset by remember {
        mutableStateOf(one + one * 2 * pagerState.currentPage - slideWidth / 2)
    }
    val offsetAnimated by animateDpAsState(targetValue = offsetTarget)
    var clickScroll by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(pagerState.currentPageOffset) {
        if (!clickScroll) {
            when {
                pagerState.currentPageOffset > 0.05f -> {
                    if (currentDirection == Direction.NONE) {
                        currentDirection = Direction.RIGHT
                    }
                    offset = when (currentDirection) {
                        Direction.RIGHT -> {
                            currentIndexOffset + screenWidth / titleList.size * pagerState.currentPageOffset
                        }
                        else -> {
                            currentIndexOffset + screenWidth / titleList.size * (pagerState.currentPageOffset - 1)
                        }
                    }
                }
                pagerState.currentPageOffset < -0.05f -> {
                    if (currentDirection == Direction.NONE) {
                        currentDirection = Direction.LEFT
                    }
                    offset = when (currentDirection) {
                        Direction.RIGHT -> {
                            currentIndexOffset + screenWidth / titleList.size * (1 + pagerState.currentPageOffset)
                        }
                        else -> {
                            currentIndexOffset + screenWidth / titleList.size * pagerState.currentPageOffset
                        }
                    }
                }
                else -> {
                    offset = when (currentDirection) {
                        Direction.RIGHT -> {
                            whichIsNearer(
                                currentIndexOffset,
                                currentIndexOffset + screenWidth / titleList.size,
                                offset
                            )
                        }
                        Direction.LEFT -> {
                            whichIsNearer(
                                currentIndexOffset - screenWidth / titleList.size,
                                currentIndexOffset,
                                offset
                            )
                        }
                        else -> offset
                    }
                    currentDirection = Direction.NONE
                    currentIndexOffset = offset
                    offsetTarget=offset
                }
            }
        }
    }

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            repeat(titleList.size) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        ) {
                            scope.launch {
                                offsetTarget = one + one * it * 2 - slideWidth / 2
                                clickScroll = true
                                pagerState.animateScrollToPage(it)
                                clickScroll = false
                                currentDirection = Direction.NONE
                                currentIndexOffset = offsetTarget
                                offset = offsetTarget
                            }
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(text = titleList[it])
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Spacer(
            modifier = Modifier
                .offset(x = if (clickScroll) offsetAnimated else offset)
                .size(slideWidth, 4.dp)
                .background(indicatorColor)
        )
    }
}

private fun whichIsNearer(left: Dp, right: Dp, it: Dp): Dp {
    return when {
        (it - left) > (right - it) -> right
        else -> left
    }
}


@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewNavigationWithIndicator() {
    Column(Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(2)
        val scope = rememberCoroutineScope()
        val list = listOf("文件上传", "文件下载", "文件备份", "文件4", "文件5", "文件6")
        NavigationWithIndicator(
            titleList = list,
            pagerState = pagerState,
            scope = scope,
        )
        HorizontalPager(count = list.size, state = pagerState) {
            Surface(border = BorderStroke(1.dp, Color.Black)) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = list[it])
                }
            }
        }
    }
}