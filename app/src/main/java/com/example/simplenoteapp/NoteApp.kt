package com.example.simplenoteapp

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.simplenoteapp.ui.home.HomeScreen
import com.example.simplenoteapp.ui.screen.EditScreen
import com.example.simplenoteapp.ui.screen.FreeSpaceScreen

@Composable
fun NoteApp() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "home") {
            composable(
                route = "home",
            ) {
                HomeScreen(
                    navController = navController
                )
            }

            // どの漫画かを　特定　するため　Id　を渡す必要がある
            composable(
                route = "edit/{noteId}",
                // Idは　Int型のため  Int  に変換する必要がある
                arguments = listOf(
                    androidx.navigation.navArgument("noteId") {
                        type = androidx.navigation.NavType.IntType
                    }
                ),
                // 移動する方に書く
                enterTransition = {
                    slideIntoContainer(
                        animationSpec = tween(100, easing = EaseIn),
                        // アニメーションが進行する方向を指定するプロパティ
                        // ➙
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },  // 左から右へスライドイン
                exitTransition = {
                    slideOutOfContainer(
                        animationSpec = tween(100, easing = EaseOut),
                        // アニメーションが進行する方向を指定するプロパティ
                        // ←
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }   // 右から左へスライドアウト
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId")
                // nullチェック
                if (noteId != null) {
                    EditScreen(
                        navController = navController,
                        noteId = noteId
                    )
                }
            }

            composable(
                route="freespace",

                // 移動する方に書く
                // 左から右へスライドイン
                enterTransition = {
                    slideIntoContainer(
                        animationSpec = tween(100, easing = EaseIn),
                        // アニメーションが進行する方向を指定するプロパティ
                        // ➙
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                // 右から左へスライドアウト
                exitTransition = {
                    slideOutOfContainer(
                        animationSpec = tween(100, easing = EaseOut),
                        // アニメーションが進行する方向を指定するプロパティ
                        // ←
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ){
                FreeSpaceScreen(
                    navController = navController,
                )
            }

        }
    }
}