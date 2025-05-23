package org.example.compose.di

import org.example.compose.MainViewModel
import org.example.compose.composables.create.CreateTaskViewModel
import org.example.compose.composables.details.TaskDetailsViewModel
import org.example.compose.composables.edit.EditTaskViewModel
import org.example.compose.composables.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CreateTaskViewModel)
    viewModelOf(::EditTaskViewModel)
    viewModelOf(::TaskDetailsViewModel)
}