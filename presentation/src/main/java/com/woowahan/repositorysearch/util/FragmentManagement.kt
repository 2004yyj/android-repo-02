package com.woowahan.repositorysearch.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.lang.reflect.Constructor

// 번들이 없는 프래그먼트를 올리고 싶을 때
fun <T: Fragment> FragmentManager.add(fragmentName: Class<T>, containerViewId: Int, tag: String) {
    this.beginTransaction().apply {
        val constructor: Constructor<T> = fragmentName.getConstructor()
        val fragment: Fragment = constructor.newInstance()
        add(containerViewId, fragment, tag)
        commit()
    }
}

// 번들이 추가 된 프래그먼트를 올리고 싶을 때
fun <T: Fragment> FragmentManager.add(fragmentName: Class<T>, containerViewId: Int, tag: String, bundle: Bundle) {
    this.beginTransaction().apply {
        val constructor: Constructor<T> = fragmentName.getConstructor(Bundle::class.java)
        val fragment: Fragment = constructor.newInstance(bundle)
        add(containerViewId, fragment, tag)
        commit()
    }
}

// 번들이 없는 프래그먼트를 replace 하고 싶을 때
fun <T: Fragment> FragmentManager.replace(fragmentName: Class<T>, containerViewId: Int, tag: String) {
    this.beginTransaction().apply {
        val constructor: Constructor<T> = fragmentName.getConstructor()
        val fragment: Fragment = constructor.newInstance()
        replace(containerViewId, fragment, tag)
        addToBackStack(tag)
        commit()
    }
}

// 번들이 추가 된 프래그먼트를 replace 하고 싶을 때
fun <T: Fragment> FragmentManager.replace(fragmentName: Class<T>, containerViewId: Int, tag: String, bundle: Bundle) {
    this.beginTransaction().apply {
        val constructor: Constructor<T> = fragmentName.getConstructor(Bundle::class.java)
        val fragment: Fragment = constructor.newInstance(bundle)
        replace(containerViewId, fragment, tag)
        addToBackStack(tag)
        commit()
    }
}

fun FragmentManager.onBackPressed(finishActivity: () -> Unit) {
    this.beginTransaction().apply {
        if (backStackEntryCount > 1) {
            popBackStack()
        } else {
            finishActivity()
        }
    }
}