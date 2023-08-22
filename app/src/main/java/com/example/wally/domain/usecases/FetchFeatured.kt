package com.example.wally.domain.usecases

import com.example.wally.domain.repository.PicturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchFeatured @Inject constructor(private val picturesRepository: PicturesRepository) {

    suspend operator fun invoke() {
        return withContext(Dispatchers.IO) {
            val pictures = picturesRepository.requestFeatured()
            if (pictures.isEmpty())
                throw NoMorePicturesException("No more pictures")
            picturesRepository.storeFeatured(pictures)
        }
    }
}