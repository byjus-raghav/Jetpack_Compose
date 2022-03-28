package com.example.jetpacksample

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.jetpacksample.navigation.Screen
import com.example.jetpacksample.screens.Greeting
import com.example.jetpacksample.screens.detailScreen

@Composable
fun Navigation(viewModel: MainViewModel){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            Greeting("HEADLINES",viewModel,navController = navController)
        }

        composable(route = Screen.DetailScreen.route+"/{newsDetails}",
            arguments = listOf(navArgument(name = "newsDetails") {
                type = NavType.StringType
            })

        ){entry->
            detailScreen(viewModel,navController = navController , entry.arguments?.getString("newsDetails"))
        }
    }
}
