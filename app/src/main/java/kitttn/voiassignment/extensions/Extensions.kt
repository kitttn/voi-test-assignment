package kitttn.voiassignment.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kitttn.voiassignment.DaggerComponentProvider
import kitttn.voiassignment.di.components.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

val FragmentActivity.component: AppComponent
    get() = (application as DaggerComponentProvider).component

val Fragment.component: AppComponent
    get() = this.activity?.component ?: throw NullPointerException("Activity is null while trying to get component!")

fun <T> LiveData<T>.observe(owner: LifecycleOwner, callback: (T) -> Unit) {
    this.observe(owner, Observer { callback(it) })
}

fun Fragment.hideKeyboard() {
    val fragmentView = this.view ?: return
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
    imm.hideSoftInputFromWindow(fragmentView.windowToken, 0)
}

// some SDD (Stackoverflow driven development)
inline fun <reified T : ViewModel, S> createViewModel(scope: S, noinline factory: () -> T): T {
    val factoryInstance = createFactory(T::class.java, factory)
    val scopeInstance = when (scope) {
        is FragmentActivity -> ViewModelProviders.of(scope, factoryInstance)
        is Fragment -> ViewModelProviders.of(scope, factoryInstance)
        else -> throw Error("Can't create instance of ${T::class.java.name}")
    }

    return scopeInstance[T::class.java]
}

fun <Z> createFactory(clazz: Class<Z>, factory: () -> Z) = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == clazz) {
            @Suppress("UNCHECKED_CAST")
            return factory() as T
        }
        throw IllegalArgumentException("Unexpected argument: $modelClass")
    }
}

