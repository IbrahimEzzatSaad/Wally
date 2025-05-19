package com.ibrahimezzat404.wally.domain.usecases

import com.ibrahimezzat404.wally.data.repositories.PicturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchFeatured @Inject constructor(private val picturesRepository: PicturesRepository) {

    suspend operator fun invoke() {
        return withContext(Dispatchers.IO) {
            val pictures = picturesRepository.requestFeatured()
            if (pictures.isNotEmpty()){
                picturesRepository.storeFeatured(pictures)
            }
        }
    }
}