package com.example.wally.domain.usecases

import com.example.wally.data.api.model.PictureModel
import com.example.wally.domain.repository.PicturesRepository
import javax.inject.Inject

class UpdateFavorite @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(item: PictureModel) = picturesRepository.updateFavorite(item)

}