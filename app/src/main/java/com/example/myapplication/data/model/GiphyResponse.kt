package com.example.myapplication.data.model

import java.util.ArrayList

class GiphyResponse {

    val meta: Meta? = null
    val data: ArrayList<Data>? = null

    class Meta {
        val status: Int? = null
    }

    class Data {
        val title: String? = null
        val user: User? = null
        val images: Images? = null

        class Images {
            val fixed_height: FixedHeight? = null
        }

        class FixedHeight {
            val url: String? = null
        }

        class User {
            val display_name: String? = null
        }
    }

}