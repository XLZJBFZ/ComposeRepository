package com.wjb.repository

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

/**
 * ClassName: Segment
 * Description:
 * Author: CSC
 */


/**
 * stickyHeader
 *
 */
@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun Segment() {
    val items = remember {
        mutableStateListOf(1,2,3,4,5,6,7,8,9,10 )
    }

    LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Image(imageVector = Icons.Filled.Image, contentDescription = "", modifier = Modifier.size(200.dp))
        }
        stickyHeader { Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Red)
            .clickable { items.add(10) }) }
        item {
            val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp-40.dp) / 3
            Row {
                Spacer(modifier = Modifier.width(10.dp))
                FlowColumn(mainAxisSize = SizeMode.Wrap, mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly) {
                    items.forEachIndexed { index, _ ->
                        Box(
                            Modifier
                                .border(BorderStroke(1.dp, Color.Black))
                                .size(width = itemSize, height = 20.dp + 10.dp * index), contentAlignment = Alignment.Center) {
                            Text(text = index.toString(), textAlign = TextAlign.Center)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                FlowColumn(mainAxisSize = SizeMode.Wrap, mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly) {
                    items.reversed().forEachIndexed { index, _ ->
                        Box(
                            Modifier
                                .border(BorderStroke(1.dp, Color.Black))
                                .size(width = itemSize, height = 220.dp - 10.dp * index), contentAlignment = Alignment.Center) {
                            Text(text = index.toString(), textAlign = TextAlign.Center)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                FlowColumn(mainAxisSize = SizeMode.Wrap, mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly) {
                    items.forEachIndexed { index, _ ->
                        Box(
                            Modifier
                                .border(BorderStroke(1.dp, Color.Black))
                                .size(
                                    width = itemSize,
                                    height = 10.dp * items
                                        .shuffled()
                                        .first() + 20.dp
                                ), contentAlignment = Alignment.Center
                        ) {
                            Text(text = index.toString(), textAlign = TextAlign.Center)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }



        }
    }
}