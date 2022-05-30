package com.example.hungrywolfscompose.shared.utils

import androidx.lifecycle.MutableLiveData
import kotlin.properties.Delegates

object Variables {
    var loadingScreen: MutableLiveData<Boolean> by Delegates.observable(MutableLiveData(false)) { _, _, _ -> }
}