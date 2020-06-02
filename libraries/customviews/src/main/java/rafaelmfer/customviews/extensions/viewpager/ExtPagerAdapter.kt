package rafaelmfer.customviews.extensions.viewpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import rafaelmfer.customviews.extensions.get

@JvmOverloads
fun ViewPager.setupPagerAdapter(
    frags: Any,
    titles: Any? = null,
    tabLayout: TabLayout? = null,
    fragManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
) {
    tabLayout?.setupWithViewPager(this)
    adapter = PagerAdapterFrag(frags, titles, fragManager)
}

class PagerAdapterFrag(
    private val frags: Any,
    private val titles: Any?,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = when (frags) {
        is Collection<*> -> frags.get(position) as Fragment
        is Map<*, *> -> frags[position] as Fragment
        else -> (frags as Array<*>)[position] as Fragment
    }

    override fun getPageTitle(position: Int) = when (titles) {
        is Collection<*> -> titles.get(position) as String
        is Map<*, *> -> titles[position] as String
        is Array<*> -> titles[position] as String
        else -> null
    }

    override fun getCount() = when (frags) {
        is Collection<*> -> frags.size
        is Map<*, *> -> frags.size
        is Array<*> -> frags.size
        else -> when (titles) {
            is Collection<*> -> titles.size
            is Array<*> -> titles.size
            else -> 2
        }
    }
}
