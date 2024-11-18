package com.ibrahimezzat404.wally.ui.model

data class Category(
    val title: String,
    val subTitle: String,
    val color: String,
    val slug: String,
    val link: String
)

 val categories = listOf(
    Category(
        "Nature",
        "Nature's wonders take center stage in this category, where photographers capture the breathtaking landscapes, diverse flora and fauna, and mesmerizing natural phenomena that adorn our planet.",
        "#c0d4de",
        "nature",
        "https://images.unsplash.com/photo-1697450300645-faf3676bb7b5?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max"
    ),
    Category(
        "Film",
        "Embracing the nostalgia and artistry of analog photography, this category pays tribute to the timeless beauty of film.",
        "#d4d4d4",
        "film",
        "https://images.unsplash.com/photo-1697206447994-a187e5c8e17c?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max"
    ),
    Category(
        "Animals",
        "This category pays homage to the fascinating world of animals.",
        "#70a2ca",
        "animals",
        "https://images.unsplash.com/photo-1697179426932-f9b744ca0c1d?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max"
    ),
    Category(
        "Travel",
        "Embark on a visual journey around the globe with this category, as photographers capture the essence of exploration and wanderlust.",
        "#bdab84",
        "travel",
        "https://images.unsplash.com/photo-1696960594920-1ca9a1f250bc?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max"
    ),
    Category(
        "Fashion & Beauty",
        "In this category, photography becomes a canvas for artistic expressions of fashion and beauty.",
        "#bfbfbb",
        "fashion-beauty",
        "https://images.unsplash.com/photo-1696774690902-6e2057307e20?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max"
    ),
    Category(
        "People",
        "People are the focal point of this category, where photographers skillfully depict the human experience.",
        "#0582c2",
        "people",
        "https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max"
    ),
    Category(
        "Architecture & Interiors",
        "Celebrating the artistry of spaces, this category recognizes exceptional photography that captures the essence of architectural marvels and interior designs.",
        "#754832",
        "architecture-interior",
        "https://images.unsplash.com/photo-1490644658840-3f2e3f8c5625?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max"
    )
)