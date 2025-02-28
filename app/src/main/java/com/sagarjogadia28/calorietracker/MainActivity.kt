package com.sagarjogadia28.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.onboarding_presentation.activity.ActivityLevelScreen
import com.sagarjogadia28.onboarding_presentation.age.AgeScreen
import com.sagarjogadia28.onboarding_presentation.gender.GenderScreen
import com.sagarjogadia28.onboarding_presentation.goal.GoalTypeScreen
import com.sagarjogadia28.onboarding_presentation.height.HeightScreen
import com.sagarjogadia28.onboarding_presentation.weight.WeightScreen
import com.sagarjogadia28.onboarding_presentation.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val snackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackBarHostState) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
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
                            AgeScreen(
                                snackBarHostState = snackBarHostState,
                                onNavigate = { event ->
                                    navController.navigate(event.route)
                                }
                            )
                        }
                        composable<Route.Height> {
                            HeightScreen(
                                snackBarHostState = snackBarHostState,
                                onNavigate =  { event ->
                                    navController.navigate(event.route)
                                }
                            )
                        }
                        composable<Route.Weight> {
                            WeightScreen(
                                snackBarHostState = snackBarHostState,
                                onNavigate =  { event ->
                                    navController.navigate(event.route)
                                }
                            )
                        }
                        composable<Route.NutrientGoal> {

                        }
                        composable<Route.Activity> {
                            ActivityLevelScreen(
                                onNavigate = { event ->
                                    navController.navigate(event.route)
                                }
                            )
                        }
                        composable<Route.Goal> {
                            GoalTypeScreen(
                                onNavigate = { event ->
                                    navController.navigate(event.route)
                                }
                            )
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

