package com.example.wally.utils

import com.example.wally.data.api.model.PictureModel

typealias OnPictureItemClicked = (PictureModel) -> Unit
typealias OnRetryClicked = () -> Unit
typealias OnFeaturedItemClicked = (PictureModel) -> Unit
typealias OnFavoriteClicked = (PictureModel) -> Unit
typealias OnCategoryClicked = (String) -> Unit

