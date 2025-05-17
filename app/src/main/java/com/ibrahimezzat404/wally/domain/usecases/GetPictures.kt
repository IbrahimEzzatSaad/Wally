package com.ibrahimezzat404.wally.domain.usecases

import com.ibrahimezzat404.wally.data.repositories.PicturesRepository
import javax.inject.Inject

class GetPictures @Inject constructor(private val picturesRepository: PicturesRepository) {

    suspend operator fun invoke() = picturesRepository.requestPictures()

}