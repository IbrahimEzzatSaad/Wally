package com.ibrahimezzat404.wally.domain.usecases

import com.ibrahimezzat404.wally.domain.repository.PicturesRepository
import javax.inject.Inject

class GetFavorite @Inject constructor(private val picturesRepository: PicturesRepository) {

    suspend operator fun invoke() = picturesRepository.getFavorite()

}