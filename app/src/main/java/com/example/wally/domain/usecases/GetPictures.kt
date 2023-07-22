package com.example.wally.domain.usecases

import com.example.wally.domain.repository.PicturesRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetPictures @Inject constructor(private val picturesRepository: PicturesRepository) {

    operator fun invoke() = picturesRepository.getPictures()
        .filter { it.isNotEmpty() }
}