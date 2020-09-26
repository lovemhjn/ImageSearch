package com.app.imagesearch.ui.search

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.app.imagesearch.R
import com.app.imagesearch.util.EspressoIdlingResource

import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert

import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ImageSearchActivityTest {
    @get:Rule
    var activityTestRule: ActivityTestRule<ImageSearchActivity> =
        ActivityTestRule(ImageSearchActivity::class.java)

    @Before
    fun setUp() {
        val activity: ImageSearchActivity = activityTestRule.activity
        Assert.assertThat(activity, Matchers.notNullValue())
        Espresso.onView(withId(R.id.etSearch)).perform(typeText("cars"))
        IdlingRegistry.getInstance()
            .register(EspressoIdlingResource.countingIdlingResource)

    }

    @After
    fun unRegisterIdlingRes() {
        IdlingRegistry.getInstance()
            .unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun recycler_view_test() {

        Espresso.onView(withId(R.id.rvImages))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvImages))
            .check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(1)))
    }

    @Test
    fun test_recyclerview_click() {
        Espresso.onView(withId(R.id.rvImages))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rvImages))
            .check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(1)))
        Espresso.onView(withId(R.id.rvImages))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    ViewActions.click()
                )
            )
        Espresso.onView(withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}