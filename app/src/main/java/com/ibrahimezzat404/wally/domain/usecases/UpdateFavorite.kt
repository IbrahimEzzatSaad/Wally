package com.ibrahimezzat404.wally.domain.usecases

import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.data.repositories.PicturesRepository
import javax.inject.Inject

class UpdateFavorite @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(item: PictureModel) = picturesRepository.updateFavorite(item)

}