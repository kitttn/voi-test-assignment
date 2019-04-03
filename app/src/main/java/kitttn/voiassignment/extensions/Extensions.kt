package kitttn.voiassignment.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kitttn.voiassignment.DaggerComponentProvider
import kitttn.voiassignment.di.components.AppComponent
import java.lang.NullPointerException

val FragmentActivity.component: AppComponent
    get() = (application as DaggerComponentProvider).component

val Fragment.component: AppComponent
    get() = this.activity?.component ?: throw NullPointerException("Activity is null while trying to get component!")