package com.wjb.repository

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wjb.repository.ui.theme.Purple40
import kotlin.math.roundToInt

/**
 * ClassName: HideTopWhenScroll
 * Description:
 * Author: CSC
 */

/**
 * 向上滑动时会隐藏的头部内容
 *
 * @param topSize 头部的高度
 * @param topContent 头部内容
 * @param content 滑动内容
 */
@Composable
fun HideTopWhenScroll(topSize:Dp, topContent:@Composable ()->Unit, content:@Composable (paddingValues: PaddingValues)->Unit) {
    val maxUpPx = with(LocalDensity.current) {
        topSize.roundToPx().toFloat()
    }
    val minUpPx = 0f
    var topContentOffsetHeightPx by remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = topContentOffsetHeightPx + delta
                topContentOffsetHeightPx = newOffset.coerceIn(-maxUpPx, -minUpPx)
                return Offset.Zero
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        content(PaddingValues(top = topSize))
        Box(modifier = Modifier
            .height(topSize)
            .offset {
                IntOffset(x = 0, y = topContentOffsetHeightPx.roundToInt())
            }
            .fillMaxWidth()
        ) {
            topContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHideTopWhenScroll() {
    HideTopWhenScroll(topSize = 40.dp,topContent = {
        Row(Modifier.fillMaxSize().background(Purple40)) {
            Image(imageVector = Icons.Filled.MyLocation, contentDescription = "",modifier = Modifier.weight(1f).height(40.dp).clickable {  })
            Image(imageVector = Icons.Filled.Favorite, contentDescription = "",modifier = Modifier.weight(1f).height(40.dp).clickable {  })
            Image(imageVector = Icons.Filled.Share, contentDescription = "",modifier = Modifier.weight(1f).height(40.dp).clickable {  })
            Image(imageVector = Icons.Filled.Download, contentDescription = "",modifier = Modifier.weight(1f).height(40.dp).clickable {  })
        }
    }){
        LazyColumn(contentPadding = it) {
            items(100) { index ->
                Text("I'm item $index", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
            }
        }
    }
}
