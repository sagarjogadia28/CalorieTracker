package com.sagarjogadia28.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.onboarding_presentation.gender.GenderScreen
import com.sagarjogadia28.onboarding_presentation.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalorieTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Route.Welcome,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Route.Welcome> {
                            WelcomeScreen(
                                onNavigate = { event ->
                                    navController.navigate(event.route)
                                }
                            )
                        }
                        composable<Route.Gender> {
                            GenderScreen(
                                onNavigate = { event ->
                                    navController.navigate(event.route)
                                }
                            )
                        }
                        composable<Route.Age> {

                        }
                        composable<Route.Height> {

                        }
                        composable<Route.Weight> {

                        }
                        composable<Route.NutrientGoal> {

                        }
                        composable<Route.Activity> {

                        }
                        composable<Route.Goal> {

                        }
                        composable<Route.TrackerOverview> {

                        }
                        composable<Route.Search> {

                        }
                    }
                }
            }
        }
    }
}

