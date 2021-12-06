package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.GiphyRepository
import kotlinx.coroutines.launch

sealed class GiphyState {
    object Idle : GiphyState()
    object Loading : GiphyState()
    class Success(val url: String?, val title: String?, val author: String?) : GiphyState()
    object Error : GiphyState()
}

class GiphyViewModel : ViewModel() {

    private var repository = GiphyRepository()
    val giphyState = MutableLiveData<GiphyState>(GiphyState.Idle)
    val username = MutableLiveData<String>()

    fun getGiphy() {
        if (giphyState.value is GiphyState.Success)
            return
        viewModelScope.launch {
            giphyState.postValue(GiphyState.Loading)
            val response = repository.getGiphy()
            if (response.meta?.status == 200) {
                val randomGif = response.data?.random()
                val model = GiphyState.Success(
                    randomGif?.images?.fixed_height?.url,
                    randomGif?.title,
                    randomGif?.user?.display_name
                )
                giphyState.postValue(model)
            } else {
                giphyState.postValue(GiphyState.Error)
            }
        }
    }

    fun setUsername(name: String) {
        username.postValue(name)
    }

}