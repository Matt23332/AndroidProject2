package com.example.myweather.di

import kotlin.reflect.KClass

annotation class InstallIn(val value: KClass<SingletonComponent>)

