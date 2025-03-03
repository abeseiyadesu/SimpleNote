package com.example.simplenoteapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplenoteapp.R
import com.example.simplenoteapp.data.NoteViewModel

// フリースペース用画面
@Composable
fun FreeSpaceScreen(
    navController: NavController,
    viewModel: NoteViewModel = viewModel()
) {
    // LiveData から フリースペース の 情報 を 監視
    val freeSpace by viewModel.freeSpace.observeAsState()

    // ユーザー入力用
    var inputHeader by remember { mutableStateOf(freeSpace?.header ?: "") }
    var inputDetail by remember { mutableStateOf(freeSpace?.detail ?: "") }

    LaunchedEffect(freeSpace) {
        freeSpace?.let {
            inputHeader = it.header
            inputDetail = it.detail
        }
    }

    // topBarを利用したいため
    Scaffold(
        // トップバー
        topBar = {
            FreeSpaceScreenTopBar(
                navController = navController,
                inputHeader = inputHeader,
                inputDetail = inputDetail
            )
        },
        // メインコンテンツ
        content = { paddingValue ->
            FreeSpaceScreenLayout(
                paddingValues = paddingValue,
                inputHeader = inputHeader,
                inputDetail = inputDetail,
                onHeaderChange = { inputHeader = it },
                onDetailChange = { inputDetail = it }
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeSpaceScreenTopBar(
    navController: NavController,
    noteViewModel: NoteViewModel = viewModel(),
    inputHeader: String,
    inputDetail: String
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text("")
        },
        // topBar 色を指定する
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(id = R.color.top_bar_color),    // 背景色を指定
            titleContentColor = Color.White,            // タイトルの色を指定
            navigationIconContentColor = Color.White,   // ナビゲーションアイコンの色
            actionIconContentColor = Color.White        // アクションアイコンの色
        ),
        // 左上 戻るボタン
        navigationIcon = {
            IconButton(
                // 前の画面に戻る
                onClick = {
                    noteViewModel.updateFreeSpace(inputHeader, inputDetail)
                    // 戻れる画面があるかを確認
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        navController.navigate("home") // ホーム画面に戻る
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back To Home"
                )
            }
        }
    )
}


@Composable
fun FreeSpaceScreenLayout(
    paddingValues: PaddingValues,
    inputHeader: String,
    inputDetail: String,
    onHeaderChange: (String) -> Unit,
    onDetailChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(paddingValues),
    ) {
        // 見出し
        TextField(
            value = inputHeader,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { newHeader -> onHeaderChange(newHeader) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,       // 背景色（フォーカス外）
                focusedContainerColor = Color.White,         // 背景色（フォーカス時）
            ),
            textStyle = TextStyle(fontSize = 24.sp),         // 文字サイズを大きく
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        // 詳細
        // メモ
        TextField(
            value = inputDetail,
            onValueChange = { newDetail -> onDetailChange(newDetail) },
            modifier = Modifier.fillMaxSize(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,      // 背景色（フォーカス外）
                focusedContainerColor = Color.White,        // 背景色（フォーカス時）
            ),

            )
    }
}