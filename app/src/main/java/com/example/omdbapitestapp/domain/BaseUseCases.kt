package com.example.omdbapitestapp.domain

interface CoUseCase<in I, out O> {
    suspend operator fun invoke(input: I): O
}

interface UseCase<in I, out O> {
    operator fun invoke(input: I): O
}

interface OutUseCase<out O> {
    operator fun invoke(): O
}
