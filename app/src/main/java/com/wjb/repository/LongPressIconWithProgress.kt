package com.wjb.repository

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * ClassName: LongPress
 * Description:
 * Author: CSC
 */

/**
 * 带进度的长按Icon，进度完成后Icon会弹一下
 *
 * @param modifier 应用在最外层的modifier，Icon占0.7f
 * @param originalSize 初始Icon占比，0~1f
 * @param bounceSize 弹跳时Icon占比，0~1f
 * @param finished 是否进度完成，false时进度条会显示，true时进度条不显示
 * @param imageVector Icon图片来源
 * @param iconColor Icon颜色
 * @param progressTime 进度时间长度，单位为ms
 * @param progressIndicatorColor 进度条颜色
 * @param snapWhenCancel 取消时是否直接清零进度
 * @param onProgressFinished 进度完成回调
 */
@Composable
fun LongPressIconWithProgress(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0)
    originalSize: Float = 0.7f,
    @FloatRange(from = 0.0, to = 1.0)
    bounceSize: Float = 0.8f,
    finished: Boolean,
    imageVector: ImageVector,
    iconColor: Color = LocalContentColor.current,
    progressTime: Int = 2000,
    progressIndicatorColor: Color = Color.Red.copy(0.7f),
    snapWhenCancel: Boolean = true,
    onProgressFinished: () -> Unit
) {
    var targetSize by remember {
        mutableStateOf(originalSize)
    }
    var targetProcess by remember {
        mutableStateOf(0f)
    }
    var forward by remember {
        mutableStateOf(false)
    }
    val size by animateFloatAsState(targetValue = targetSize)
    val progress by animateFloatAsState(
        targetValue = targetProcess,
        animationSpec = if (forward) tween(progressTime) else snap()
    )
    LaunchedEffect(progress) {
        if (progress == 1f && !finished) {
            targetSize = bounceSize
            onProgressFinished()
        }
    }
    if (size == bounceSize) {
        targetSize = originalSize
    }
    Box(modifier, contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier
                .fillMaxSize(size)
                .pointerInput(Unit) {
                    detectTapGestures(onPress = {
                        forward = true
                        targetProcess = 1f
                        awaitRelease()
                        targetProcess = 0f
                        if (snapWhenCancel) {
                            forward = false
                        }
                    })
                },
            imageVector = imageVector,
            contentDescription = "",
            tint = iconColor
        )
        if (!finished) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(0.8f),
                color = progressIndicatorColor,
                strokeWidth = 1.dp,
                progress = progress
            )
        }
    }
}

@Composable
fun LongPressIconWithProgress(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0)
    originalSize: Float = 0.7f,
    @FloatRange(from = 0.0, to = 1.0)
    bounceSize: Float = 0.8f,
    finished: Boolean,
    bitmap: ImageBitmap,
    progressTime: Int = 2000,
    iconColor: Color = LocalContentColor.current,
    progressIndicatorColor: Color = Color.Red.copy(0.7f),
    snapWhenCancel: Boolean = true,
    onProgressFinished: () -> Unit
) {
    var targetSize by remember {
        mutableStateOf(originalSize)
    }
    var targetProcess by remember {
        mutableStateOf(0f)
    }
    var forward by remember {
        mutableStateOf(false)
    }
    val size by animateFloatAsState(targetValue = targetSize)
    val progress by animateFloatAsState(
        targetValue = targetProcess,
        animationSpec = if (forward) tween(progressTime) else snap()
    )
    LaunchedEffect(progress) {
        if (progress == 1f && !finished) {
            targetSize = bounceSize
            onProgressFinished()
        }
    }
    if (size == bounceSize) {
        targetSize = originalSize
    }
    Box(modifier, contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier
                .fillMaxSize(size)
                .pointerInput(Unit) {
                    if (!finished) {
                        detectTapGestures(onPress = {
                            forward = true
                            targetProcess = 1f
                            awaitRelease()
                            targetProcess = 0f
                            if (snapWhenCancel) {
                                forward = false
                            }
                        })
                    }
                },
            bitmap = bitmap,
            contentDescription = "",
            tint = iconColor
        )
        if (!finished) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(0.8f),
                color = progressIndicatorColor,
                strokeWidth = 1.dp,
                progress = progress
            )
        }
    }
}

@Composable
fun LongPressIconWithProgress(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0)
    originalSize: Float = 0.7f,
    @FloatRange(from = 0.0, to = 1.0)
    bounceSize: Float = 0.8f,
    finished: Boolean,
    painter: Painter,
    progressTime: Int = 2000,
    iconColor: Color = LocalContentColor.current,
    progressIndicatorColor: Color = Color.Red.copy(0.7f),
    snapWhenCancel: Boolean = true,
    onProgressFinished: () -> Unit
) {
    var targetSize by remember {
        mutableStateOf(originalSize)
    }
    var targetProcess by remember {
        mutableStateOf(0f)
    }
    var forward by remember {
        mutableStateOf(false)
    }
    val size by animateFloatAsState(targetValue = targetSize)
    val progress by animateFloatAsState(
        targetValue = targetProcess,
        animationSpec = if (forward) tween(progressTime) else snap()
    )
    LaunchedEffect(progress) {
        if (progress == 1f && !finished) {
            targetSize = bounceSize
            onProgressFinished()
        }
    }
    if (size == bounceSize) {
        targetSize = originalSize
    }
    Box(modifier, contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier
                .fillMaxSize(size)
                .pointerInput(Unit) {
                    if (!finished) {
                        detectTapGestures(onPress = {
                            forward = true
                            targetProcess = 1f
                            awaitRelease()
                            targetProcess = 0f
                            if (snapWhenCancel) {
                                forward = false
                            }
                        })
                    }
                },
            painter = painter,
            contentDescription = "",
            tint = iconColor
        )
        if (!finished) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(0.8f),
                color = progressIndicatorColor,
                strokeWidth = 1.dp,
                progress = progress
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLongPressWithProgress() {
    var finished by remember {
        mutableStateOf(false)
    }
    Column(Modifier.fillMaxSize()) {
        LongPressIconWithProgress(
            modifier = Modifier.size(50.dp),
            finished = finished,
            imageVector = Icons.Filled.Favorite,
            iconColor = Color.Red.copy(0.7f)
        ) {
            finished = true
        }
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Black)
                .clickable { finished = false })
    }
}
