package com.mendelin.catpediahilt.presentation.breeds_list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mendelin.catpediahilt.R
import com.mendelin.catpediahilt.presentation.main.CatpediaActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class BreedsListTest {
    @JvmField
    @Rule
    val activityScenario = ActivityScenarioRule(CatpediaActivity::class.java)

    @Test
    fun testActivity() {
        /*launchActivity<CatpediaActivity>().use {
            onView(withId(R.id.nav_host_fragment_content_main))
                .check(matches(hasDescendant(withId(R.id.recyclerBreeds))))

            onView(withId(R.id.recyclerBreeds))
                .perform(
                    RecyclerViewActions.actionOnItem<BreedsListAdapter.BreedsListViewHolder>(
                        withId(R.id.breedName), hasErrorText(""))
                )
                .check(
                    matches(
                         withText("Abyssinian"))
                    )
                )

        }

        with(launchFragment<BreedsListFragment>()) {
            onFragment { fragment ->
                assert(fragment.activity).isNotNull()
            }
        }*/

        onView(withId(R.id.recyclerBreeds))
            .check(matches(isDisplayed()))
    }
}