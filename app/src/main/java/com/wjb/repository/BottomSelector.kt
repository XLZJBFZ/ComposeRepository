package com.wjb.repository

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.lang.StringBuilder

/**
 * ClassName: BottomSelector
 * Description:
 * Author: CSC
 */

/**
 * 底部选择菜单
 *
 * @param onAllSelected 全部选择后返回的结果，有选择的str和str的pos
 * @param title 标题
 * @param first 第一个选项
 * @param other 其它选项
 * @param content 除了底部菜单的其他部分
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSelector(
    onAllSelected: (str: List<String>, pos: List<Int>) -> Unit,
    title: String,
    first: List<String>,
    vararg other: Map<String, List<String>>,
    content: @Composable (state: ModalBottomSheetState) -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetContent = {
            Selector(
                title = title,
                modalBottomSheetState = modalBottomSheetState,
                first = first,
                other = other,
            ) { str, pos ->
                onAllSelected(str, pos)
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = modalBottomSheetState,
    ) {
        content(modalBottomSheetState)
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
private fun Selector(
    title: String,
    modalBottomSheetState: ModalBottomSheetState,
    first: List<String>,
    vararg other: Map<String, List<String>>,
    onAllSelected: (str: List<String>, pos: List<Int>) -> Unit,
) {
    val selectedColor: Color = Color.Blue.copy(alpha = 0.7f)//todo
    val unSelectedColor = Color.Gray
    val scope = rememberCoroutineScope()
    val state = rememberPagerState()
    var currentIndex by remember {
        mutableStateOf(0)
    }
    val titleList = remember {
        mutableStateListOf(*(Array(other.size + 1) { "请选择" }))
    }
    val selectedIndices = remember {
        mutableStateListOf(*(Array(other.size + 1) { 0 }))
    }
    Column(
        Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(thickness = 0.5.dp)
        Column(Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp)) {
            LazyRow(Modifier.fillMaxWidth()) {
                itemsIndexed(titleList) { index, item ->
                    Column(
                        Modifier.height(50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedVisibility(
                            visible = index <= currentIndex, enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Column {
                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .clickable {
                                            currentIndex = index
                                            scope.launch {
                                                state.animateScrollToPage(index)
                                                repeat(other.size + 1) {
                                                    if (it >= index) {
                                                        titleList[it] = "请选择"
                                                        selectedIndices[it] = 0
                                                    }
                                                }
                                            }
                                        },
                                    color = if (index == currentIndex) selectedColor else unSelectedColor
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                        AnimatedVisibility(
                            visible = index == currentIndex,
                            enter = fadeIn() + slideInVertically { it },
                            exit = fadeOut() + slideOutVertically { it }) {
                            Spacer(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(selectedColor)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            HorizontalPager(
                count = other.size + 1,
                modifier = Modifier.fillMaxWidth(),
                state = state,
                userScrollEnabled = false
            ) { page ->
                val items = run {
                    var list = first
                    repeat(page) {
                        list = other[it][list[selectedIndices[it]]]!!
                    }
                    list
                }
                LazyColumn(Modifier.fillMaxHeight()) {
                    itemsIndexed(items) { index, item ->
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    titleList[page] = item
                                    selectedIndices[page] = index
                                    if (page == other.size) {
                                        onAllSelected(titleList.toList(), selectedIndices.toList())
                                        scope.launch { modalBottomSheetState.hide() }
                                    } else {
                                        currentIndex = page + 1
                                        scope.launch { state.animateScrollToPage(page + 1) }
                                    }
                                }, contentAlignment = Alignment.CenterStart
                        ) {
                            Text(text = item, modifier = Modifier)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewSelector() {
    val list = listOf("广东省", "江苏省", "山东省")
    val map1 = hashMapOf(
        Pair("广东省", listOf("广州市", "深圳市", "东莞市")),
        Pair("江苏省", listOf("南京市", "无锡市")),
        Pair("山东省", listOf("济南市", "青岛市", "枣庄市", "烟台市"))
    )

    val map2 = hashMapOf(
        Pair("广州市", listOf("越秀区", "海珠区")),
        Pair("深圳市", listOf("深圳区1", "深圳区2")),
        Pair("东莞市", listOf("东莞区1")),
        Pair("南京市", listOf("南京区1")),
        Pair("无锡市", listOf("无锡区1")),
        Pair("济南市", listOf("济南区1")),
        Pair("青岛市", listOf("青岛区1")),
        Pair("枣庄市", listOf("枣庄区1")),
        Pair("烟台市", listOf("烟台区1")),
    )
    Selector(
        title = "请选择地区",
        modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
        first = list,
        other = arrayOf(map1, map2)
    ) { _, _ -> }
}