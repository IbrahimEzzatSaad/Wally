package com.example.wally.domain.usecases

import com.example.wally.domain.repository.PicturesRepository
import javax.inject.Inject

class QuerySearch @Inject constructor(private val picturesRepository: PicturesRepository) {

    suspend operator fun invoke(query: String) = picturesRepository.getSearch(query)

}