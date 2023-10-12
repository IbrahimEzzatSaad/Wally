package com.example.wally.domain.usecases

import com.example.wally.domain.repository.PicturesRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetFavorite @Inject constructor(private val picturesRepository: PicturesRepository) {

    suspend operator fun invoke() = picturesRepository.getFavorite()

}