package rafaelmfer.customviews.extensions

import android.annotation.SuppressLint
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.FragmentTransaction
import rafaelmfer.customviews.R

@SuppressLint("CommitTransaction")
fun FragmentManager.ensureTransaction() = beginTransaction()

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> FragmentManager.findFragment(tag: String) = findFragmentByTag(tag) as T

fun <T> FragmentManager.getFragment(id: T) = when (id) {
    is String -> findFragmentByTag(id)
    is Int -> findFragmentById(id)
    else -> null
}

fun FragmentManager.attachFragmentWithAnimation(container: Int, frag: Fragment, stackAdd: Boolean) {
    ensureTransaction().run {
        setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        if (stackAdd) addToBackStack(frag.tag)
        attachFragment(container, frag)
    }
}

fun FragmentManager.attachFragment(container: Int, frag: Fragment, stackAdd: Boolean) = ensureTransaction().run {
    if (stackAdd) addToBackStack(frag.tag)
    attachFragment(container, frag)
}

fun FragmentTransaction.attachFragment(content: Int, fragment: Fragment?) = fragment?.also {
    if (it.isDetached) attach(it) else if (!it.isAdded) add(content, it, it.tag)
    commit()
}

fun FragmentManager.removeFragment(fragment: Fragment, stackAdd: Boolean) = ensureTransaction().run {
    remove(fragment)
    commitTransactions(stackAdd)
}

fun FragmentManager.replaceFragmentWithAnimation(
    fragment: Fragment,
    containerToReplace: Int,
    stackAdd: Boolean,
    @AnimRes animationEnter: Int,
    @AnimRes animationExit: Int
) {
    ensureTransaction().run {
        if (stackAdd) addToBackStack(fragment.tag)
        setCustomAnimations(animationEnter, animationExit)
        replace(containerToReplace, fragment, fragment.tag).commit()
    }
}

fun FragmentManager.replaceFragmentWithManyAnimations(
    fragment: Fragment,
    containerToReplace: Int,
    stackAdd: Boolean,
    tag: String,
    @AnimRes animationEnter: Int,
    @AnimRes animationExit: Int,
    @AnimRes animationPopEnter: Int,
    @AnimRes animationPopExit: Int
) {
    ensureTransaction().run {
        if (stackAdd) addToBackStack(tag)
        setCustomAnimations(animationEnter, animationExit, animationPopEnter, animationPopExit)
        replace(containerToReplace, fragment, tag).commit()
    }
}

fun FragmentManager.setFragmentsVisibleHint(fragment: Fragment, containerToReplace: Any) =
    getFragment(containerToReplace).run {
        if (this != null) userVisibleHint = false
        fragment.userVisibleHint = true
    }

fun FragmentManager.replaceFragment(fragment: Fragment, containerToReplace: Int, stackAdd: Boolean, tag: String) {
    ensureTransaction().run {
        replace(containerToReplace, fragment, tag)
        commitTransactions(stackAdd)
        setFragmentsVisibleHint(fragment, containerToReplace)
    }
}

fun FragmentTransaction.commitTransactions(stackAdd: Boolean) {
    if (!isEmpty) {
        if (stackAdd) addToBackStack(null)
        commit()
    }
}

fun FragmentManager.backToFirstFragment() {
    if (backStackEntryCount > 0) {
        if (!isDestroyed) popBackStackImmediate(getBackStackEntryAt(0).id, POP_BACK_STACK_INCLUSIVE)
    }
}

fun FragmentManager.getCurrentFragment() = if (backStackEntryCount <= 0) null else {
    findFragmentByTag(getBackStackEntryAt(backStackEntryCount - 1).name)
}