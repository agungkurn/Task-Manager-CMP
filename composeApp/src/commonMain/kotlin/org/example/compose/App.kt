package org.example.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.compose.composables.create.CreateTaskScreen
import org.example.compose.composables.details.TaskDetailsScreen
import org.example.compose.composables.edit.EditTaskScreen
import org.example.compose.composables.home.HomeScreen
import org.example.compose.di.repositoryModule
import org.example.compose.di.viewModelModule
import org.example.compose.navigation.MainRoute
import org.example.compose.ui.AdjustSystemBar
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.module

@Composable
private fun AppTheme(appTheme: AppTheme, content: @Composable () -> Unit) {
    val colorScheme = when (appTheme) {
        AppTheme.Dark -> darkColorScheme()
        AppTheme.Light -> lightColorScheme()
        AppTheme.System -> if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
fun App(dataStore: DataStore<Preferences>) {
    val globalModule = remember(dataStore) {
        module {
            single { dataStore }
        }
    }

    KoinApplication(
        application = {
            modules(
                globalModule,
                repositoryModule,
                viewModelModule
            )
        }
    ) {
        val viewModel = koinViewModel<MainViewModel>()

        val currentTheme by viewModel.currentTheme.collectAsState()

        AdjustSystemBar(
            isDark = when (currentTheme) {
                AppTheme.Dark -> true
                AppTheme.Light -> false
                AppTheme.System -> isSystemInDarkTheme()
            }
        )

        AppTheme(appTheme = currentTheme) {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = MainRoute.Home) {
                composable<MainRoute.Home> {
                    HomeScreen(
                        onCreateTask = { navController.navigate(MainRoute.Create) },
                        onOpenTask = { navController.navigate(MainRoute.Details(it.id)) }
                    )
                }
                composable<MainRoute.Create> {
                    CreateTaskScreen(
                        onNavigateUp = { navController.popBackStack() },
                        onSaved = { navController.popBackStack() }
                    )
                }
                composable<MainRoute.Details> {
                    val taskId = it.toRoute<MainRoute.Details>().taskId

                    TaskDetailsScreen(
                        taskId = taskId,
                        onNavigateUp = { navController.popBackStack() },
                        onEdit = { task ->
                            navController.navigate(MainRoute.Edit(task.id))
                        },
                        onDeleted = {
                            navController.popBackStack()
                        }
                    )
                }
                composable<MainRoute.Edit> {
                    val taskId = it.toRoute<MainRoute.Edit>().taskId

                    EditTaskScreen(
                        taskId = taskId,
                        onNavigateUp = { navController.popBackStack() },
                        onSaved = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}