package com.example.wally.domain.usecases

import com.example.wally.domain.repository.PicturesRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetCategoryList @Inject constructor(private val picturesRepository: PicturesRepository) {

    suspend operator fun invoke(id: String) = picturesRepository.getCategoryList(id)

}