package org.example.compose.di

import org.example.compose.data.TaskRepository
import org.example.compose.data.TaskRepositoryImpl
import org.example.compose.data.ThemeRepository
import org.example.compose.data.ThemeRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::TaskRepositoryImpl) { bind<TaskRepository>() }
    singleOf(::ThemeRepositoryImpl) { bind<ThemeRepository>() }
}