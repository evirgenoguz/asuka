package com.evirgenoguz.asuka.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

/**
 * Created by Oguz Evirgen on 19.06.2023.
 */
fun <T> Fragment.observeLiveData(liveData: LiveData<T>, block: (T) -> Unit){

    liveData.observe(viewLifecycleOwner){
        it?.let(block)
    }
}