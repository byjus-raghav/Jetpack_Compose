package com.example.jetpacksample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpacksample.model.Articles
import com.example.jetpacksample.network.ApiService
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    var articleListResponse:List<Articles> by mutableStateOf(listOf())
    var errorMessage:String by mutableStateOf("")

    var articleDetailResponse = MutableLiveData<Articles>()

    fun getArticles(){
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val articleList =apiService.getMovies()
                articleListResponse = articleList.articles
            }
            catch (e:Exception){
                errorMessage=e.message.toString()
            }
        }
    }

    fun getDetails(title:String?){
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val articleList =apiService.getMovies()

                for (item in articleList.articles){
                    if (item.title.equals(title)){
                        articleDetailResponse.postValue(item)
                        break
                    }
                }
            }
            catch (e:Exception){
                errorMessage=e.message.toString()
            }
        }
    }
}


