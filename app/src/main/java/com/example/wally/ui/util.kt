package com.example.wally.ui

import com.example.wally.data.api.model.PicturesItem

typealias OnPictureItemClicked = (PicturesItem) -> Unit
typealias OnRetryClicked = () -> Unit
typealias OnFeaturedItemClicked = (List<PicturesItem>, Int) -> Unit
typealias OnFavoriteClicked = (PicturesItem) -> Unit
typealias OnCategoryClicked = (String) -> Unit

