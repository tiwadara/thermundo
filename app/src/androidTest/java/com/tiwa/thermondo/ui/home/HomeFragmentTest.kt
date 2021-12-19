package com.tiwa.thermondo.ui.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiwa.thermondo.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)

class HomeFragmentTest {

    @Test
    fun testNoOffersFragment() {

        val scenario = launchFragmentInContainer<HomeFragment>()
        onView(withId(R.id.button_shorten))
            .check(matches(withText(containsString("SHORTEN IT!"))))

    }
}