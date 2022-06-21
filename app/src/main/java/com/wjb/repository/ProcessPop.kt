package com.wjb.repository

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.compose.DialogHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * ClassName: WaitingDialog
 * Description:
 * Author: CSC
 */

@Composable
fun ProcessSurface(hint: String = "", content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp)),
        color = Color.Black.copy(alpha = 0.7f)//背景半透明
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 6.dp)
        ) {
            content()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = hint,
                color = Color.White,
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WaitingPop(hint: String = "加载中...", isShow: Boolean) {
    if (isShow) {
        Popup(
            Alignment.Center,
            properties = PopupProperties(
                focusable = isShow,
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            WaitingPopContent(hint)
        }
    }

}

@Composable
private fun WaitingPopContent(hint: String = "加载中...") {
    ProcessSurface(hint) {
        CircularProgressIndicator(
            color = Color.White,
            strokeWidth = 2.dp,
            modifier = Modifier.size(60.dp)
        )
    }
}

@Preview
@Composable
fun PreviewWaitingPop() {
    WaitingPopContent()
}

@Composable
fun ErrorPop(hint: String = "错误", onDismissListener: () -> Unit) {
    var isShowPop by remember {
        mutableStateOf(true)
    }
    Popup(
        Alignment.Center,
        properties = PopupProperties(focusable = isShowPop, dismissOnClickOutside = true),
        onDismissRequest = {
            isShowPop = false
            onDismissListener()
        }
    ) {
        ErrorPopContent(hint)
    }
}

@Preview
@Composable
fun PreviewErrorPop() {
    ErrorPopContent()
}


@Composable
private fun ErrorPopContent(hint: String = "错误") {
    ProcessSurface(hint) {
        Icon(
            imageVector = Icons.Filled.Error,
            contentDescription = "",
            modifier = Modifier.size(60.dp),
            tint = Color.White
        )
    }
}

@Composable
fun FinishPop(hint: String = "完成", onDismissListener: () -> Unit) {
    var isShowPop by remember {
        mutableStateOf(true)
    }
    Popup(
        Alignment.Center,
        properties = PopupProperties(focusable = isShowPop, dismissOnClickOutside = true),
        onDismissRequest = {
            isShowPop = false
            onDismissListener()
        }
    ) {
        FinishPopContent(hint)
    }
}

@Preview
@Composable
fun PreviewFinishPop() {
    FinishPopContent()
}


@Composable
private fun FinishPopContent(hint: String = "完成") {
    ProcessSurface(hint) {
        Icon(
            imageVector = Icons.Filled.Done,
            contentDescription = "",
            modifier = Modifier.size(60.dp),
            tint = Color.White
        )
    }
}

/**
 * 开关按钮
 *
 * @param size 高度，宽度为高度两倍
 * @param padding 缩进
 * @param color 选择时的底色
 * @param enabled 是否可用，不可用时为灰色底色
 * @param checked 是否选择了
 * @param onCheckedChange 选择状态回调
 */
@Composable
fun Switch(
    size: Dp = 20.dp,
    padding: Dp = size / 10,
    color: Color = Color(0xff00ee00),
    enabled: Boolean = true,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier
            .size(size * 2 + padding * 2, size * 1 + padding * 2)
            .padding(padding)
            .clip(CircleShape)
            .background(
                if (!enabled) Color.Gray else {
                    if (!checked) Color.White else color
                }
            )
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                onCheckedChange(!checked)
            }) {
        AnimatedVisibility(
            visible = checked,
            enter = expandHorizontally(expandFrom = Alignment.Start),
            exit = shrinkHorizontally(shrinkTowards = Alignment.Start)
        ) {
            Spacer(modifier = Modifier.size(size))
        }
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Color.White),
            border = BorderStroke(
                size / 50, if (!enabled) Color.Gray else {
                    if (!checked) Color.LightGray else color
                }
            )
        ) {}
        AnimatedVisibility(
            visible = !checked,
            enter = expandHorizontally(expandFrom = Alignment.End),
            exit = shrinkHorizontally(shrinkTowards = Alignment.End)
        ) {
            Spacer(modifier = Modifier.size(size))
        }
    }
}

@Preview
@Composable
fun PreviewSwitch() {
    Switch {}
}
