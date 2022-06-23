package com.wjb.repository

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ClassName: FadeEdge
 * Description:
 * Author: CSC
 */
@Preview(showBackground = true)
@Composable
fun PreviewFadeEdge() {
    Column {
        LazyRow(
            Modifier
                .fillMaxWidth()
                .horizontalFadeEdge()){
            items(listOf(1,2,3,4,5,6,7,8,9,10)){
                Text(text = it.toString(),modifier=Modifier.width(50.dp))
            }
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .verticalFadeEdge()){
            items(listOf(11111,22222,33333,44444,55555,66666,77777,88888,99999,1010101010)){
                Text(text = it.toString(),modifier=Modifier.height(150.dp))
            }
        }
    }

}

/**
 *
 *
 * @param fadeWidth 淡化的距离
 * @param edgeColor 淡化颜色
 * @return
 */
fun Modifier.horizontalFadeEdge(fadeWidth: Dp = 10.dp, edgeColor: Color = Color.White) =
    this.then(drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(edgeColor, Color.Transparent),
                startX = 0.0f,
                endX = fadeWidth.toPx()
            ), size = Size(fadeWidth.toPx(), this.size.height)
        )
        drawRect(
            brush=Brush.horizontalGradient(colors = listOf(Color.Transparent,edgeColor),
                startX = this.size.width-fadeWidth.toPx(),
                endX = this.size.width),
            size = Size(fadeWidth.toPx(), this.size.height),
            topLeft = Offset(this.size.width-fadeWidth.toPx(), 0.0f)
        )
    })

/**
 *
 *
 * @param fadeHeight 淡化的距离
 * @param edgeColor 淡化颜色
 * @return
 */
fun Modifier.verticalFadeEdge(fadeHeight: Dp = 10.dp, edgeColor: Color = Color.White) =
    this.then(drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(edgeColor, Color.Transparent),
                startY = 0.0f,
                endY = fadeHeight.toPx()
            ), size = Size(this.size.width,fadeHeight.toPx())
        )
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent,edgeColor),
                startY = this.size.width-fadeHeight.toPx(),
                endY = this.size.width
            ), size = Size(this.size.width-fadeHeight.toPx(),this.size.width)
        )
    })

