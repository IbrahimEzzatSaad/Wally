package com.example.wally.domain.usecases

import com.example.wally.domain.repository.PicturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchPics @Inject constructor(private val picturesRepository: PicturesRepository) {

    suspend operator fun invoke(page: String) {
        return withContext(Dispatchers.IO) {
            val articles = picturesRepository.requestPictures(page)
            if (articles.isEmpty())
                throw NoMoreArticlesException("No more articles")
            picturesRepository.storePictures(articles)
        }
    }
}