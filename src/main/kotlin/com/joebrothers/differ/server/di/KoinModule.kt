package com.joebrothers.differ.server.di

import com.joebrothers.differ.server.domain.auth.AuthService
import com.joebrothers.differ.server.domain.user.UserRepository
import com.joebrothers.differ.server.domain.user.UserRepositoryImpl
import com.joebrothers.differ.server.domain.user.UserService
import com.joebrothers.differ.server.utils.Argon2HashingService
import com.joebrothers.differ.server.utils.HashingService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoryModule = module {
    singleOf(::UserRepositoryImpl) bind UserRepository::class
}

private val utilModule = module {
    singleOf(::Argon2HashingService) bind HashingService::class
}

private val serviceModule = module {
    singleOf(::UserService)
    singleOf(::AuthService)
}

val appModules = listOf(repositoryModule, utilModule, serviceModule)
