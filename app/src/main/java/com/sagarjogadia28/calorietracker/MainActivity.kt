package com.sagarjogadia28.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sagarjogadia28.core.domain.preferences.UserInfoPreferences
import com.sagarjogadia28.core.navigation.Route
import com.sagarjogadia28.core_ui.ui.theme.CalorieTrackerTheme
import com.sagarjogadia28.onboarding_presentation.activity.ActivityLevelScreen
import com.sagarjogadia28.onboarding_presentation.age.AgeScreen
import com.sagarjogadia28.onboarding_presentation.gender.GenderScreen
import com.sagarjogadia28.onboarding_presentation.goal.GoalTypeScreen
import com.sagarjogadia28.onboarding_presentation.height.HeightScreen
import com.sagarjogadia28.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.sagarjogadia28.onboarding_presentation.weight.WeightScreen
import com.sagarjogadia28.onboarding_presentation.welcome.WelcomeScreen
import com.sagarjogadia28.tracker_presentation.search.SearchScreen
import com.sagarjogadia28.tracker_presentation.tracker_overview.TrackerOverviewScreen
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val preferences: UserInfoPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val snackBarHostState = remember { SnackbarHostState() }

                val shouldShowOnboarding by preferences.loadShouldShowOnboarding()
                    .collectAsState(null)

                Scaffold(
                    snackbarHost = { SnackbarHost(snackBarHostState) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    when (shouldShowOnboarding) {
                        null -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        else -> {
                            val startDestination = if (shouldShowOnboarding == true) {
                                Route.Welcome
                            } else {
                                Route.TrackerOverview
                            }
                            NavHost(
                                navController = navController,
                                startDestination = startDestination,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable<Route.Welcome> {
                                    WelcomeScreen(
                                        onNextClick = {
                                            navController.navigate(Route.Gender)
                                        }
                                    )
                                }
                                composable<Route.Gender> {
                                    GenderScreen(
                                        onNextClick = {
                                            navController.navigate(Route.Age)
                                        }
                                    )
                                }
                                composable<Route.Age> {
                                    AgeScreen(
                                        snackBarHostState = snackBarHostState,
                                        onNextClick = {
                                            navController.navigate(Route.Height)
                                        }
                                    )
                                }
                                composable<Route.Height> {
                                    HeightScreen(
                                        snackBarHostState = snackBarHostState,
                                        onNextClick = {
                                            navController.navigate(Route.Weight)
                                        }
                                    )
                                }
                                composable<Route.Weight> {
                                    WeightScreen(
                                        snackBarHostState = snackBarHostState,
                                        onNextClick = {
                                            navController.navigate(Route.NutrientGoal)
                                        }
                                    )
                                }
                                composable<Route.NutrientGoal> {
                                    NutrientGoalScreen(
                                        snackBarHostState = snackBarHostState,
                                        onNextClick = {
                                            navController.navigate(Route.Activity)
                                        }
                                    )
                                }
                                composable<Route.Activity> {
                                    ActivityLevelScreen(
                                        onNextClick = {
                                            navController.navigate(Route.Goal)
                                        }
                                    )
                                }
                                composable<Route.Goal> {
                                    GoalTypeScreen(
                                        onNextClick = {
                                            navController.navigate(Route.TrackerOverview)
                                        }
                                    )
                                }
                                composable<Route.TrackerOverview> {
                                    TrackerOverviewScreen(
                                        onNavigate = { mealName, day, month, year ->
                                            navController.navigate(
                                                Route.Search(
                                                    mealName,
                                                    day,
                                                    month,
                                                    year
                                                )
                                            )
                                        }
                                    )
                                }
                                composable<Route.Search> { backStackEntry ->
                                    val search = backStackEntry.toRoute<Route.Search>()
                                    SearchScreen(
                                        snackBarHostState = snackBarHostState,
                                        mealName = search.mealName,
                                        dayOfMonth = search.dayOfMonth,
                                        month = search.monthValue,
                                        year = search.year,
                                        onNavigateUp = { navController.navigateUp() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

