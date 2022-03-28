package com.example.jetpacksample.model

data class News(var articles:List<Articles>)
data class Articles(var title:String?,var description:String?,var urlToImage:String?, var publishedAt:String?)

