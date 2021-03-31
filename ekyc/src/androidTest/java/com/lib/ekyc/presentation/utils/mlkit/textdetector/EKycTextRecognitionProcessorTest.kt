package com.lib.ekyc.presentation.utils.mlkit.textdetector

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EKycTextRecognitionProcessorTest {

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

//    @Before
//    fun registerIdlingResource() {
//        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
//    }
//
//    @After
//    fun unRegisterIdlingResource() {
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
//    }

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun useAppContext() {
        Assert.assertEquals("com.lib.ekyc", appContext.packageName)
    }
//
//    @Test
//    fun testShowProfile() = runBlocking {
//
//        val fakeRepository = FakeProfileRepository()
//        val viewModel = ShowProfileViewModel(GetProfile(fakeRepository))
//        viewModel.getProfile()
//
//        val result = viewModel.profileState.getOrAwaitValueAndroidTest()
//
//        TestCase.assertNotSame(result, Resource.Failure.Generic(null))
//        TestCase.assertNotSame(result, Resource.Failure.NetworkException(null))
//
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun avatarIsVisible() {
//
//        launchFragmentInHiltContainer<ShowProfileFragment>()
//
//        Espresso.onView(withId(R.id.avatar))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//
//        Espresso.onView(withId(R.id.loading))
//            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
//
//        Espresso.onView(withId(R.id.lastUpdate))
//            .check(
//                ViewAssertions.matches(
//                    ViewMatchers.withText(
//                        CoreMatchers.startsWith(
//                            appContext.getString(
//                                R.string.last_update
//                            )
//                        )
//                    )
//                )
//            )
//
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun checkProfileListSize() {
//
//        launchFragmentInHiltContainer<ShowProfileFragment>()
//
//        Espresso.onView(withId(R.id.detailsLv))
//            .check(hasItemCount(10))
//
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun checkShowingItems() {
//
//        launchFragmentInHiltContainer<ShowProfileFragment>(){
//            //do whatever we want in the Fragment(with variables and methods)
//        }
//
//        Espresso.onView(
//            CoreMatchers.allOf(
//                withId(R.id.title),
//                ViewMatchers.withText(CoreMatchers.startsWith(appContext.getString(R.string.display_name)))
//            )
//        ).check(
//            ViewAssertions.matches(
//                ViewMatchers.isDisplayed()
//            )
//        )
//
//        Espresso.onView(
//            CoreMatchers.allOf(
//                withId(R.id.title),
//                ViewMatchers.withText(CoreMatchers.startsWith(appContext.getString(R.string.religion)))
//            )
//        ).check(
//            ViewAssertions.matches(
//                ViewMatchers.isDisplayed()
//            )
//        )
//
//        Espresso.onView(
//            CoreMatchers.allOf(
//                withId(R.id.title),
//                ViewMatchers.withText(CoreMatchers.startsWith(appContext.getString(R.string.occupation)))
//            )
//        ).check(
//            ViewAssertions.matches(
//                ViewMatchers.isDisplayed()
//            )
//        )
//
//        Espresso.onView(
//            CoreMatchers.allOf(
//                withId(R.id.title),
//                ViewMatchers.withText(CoreMatchers.startsWith(appContext.getString(R.string.location)))
//            )
//        ).check(
//            ViewAssertions.matches(
//                ViewMatchers.isDisplayed()
//            )
//        )
//
//        Espresso.onView(
//            CoreMatchers.allOf(
//                withId(R.id.title),
//                ViewMatchers.withText(CoreMatchers.startsWith(appContext.getString(R.string.marital_status)))
//            )
//        ).check(
//            ViewAssertions.matches(
//                ViewMatchers.isDisplayed()
//            )
//        )
//
//
//
//    }




}