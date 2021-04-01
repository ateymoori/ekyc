package com.lib.ekyc.presentation.ui.nfc

import android.app.Application
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lib.ekyc.R
import com.lib.ekyc.utils.TestUtils.Companion.setTextInTextView
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GetDataForNFCEncryptionActivityTest {

    val application: Application = ApplicationProvider.getApplicationContext()


    @Test
    fun CORRECT_PAN_SHOULD_GO_NEXT_STEP() {
        val scenario = ActivityScenario.launch(GetDataForNFCEncryptionActivity::class.java)
        Intents.init()

        onView(withId(R.id.passportNumber))
            .perform(setTextInTextView("N464098876"))

        onView(withId(R.id.nextBtn)).perform(click())

        intended(hasComponent(NFCReaderActivity::class.java.name))

    }

    @Test
    fun INCORRECT_PAN_SHOULD_GET_ERROR() {
        val scenario = ActivityScenario.launch(GetDataForNFCEncryptionActivity::class.java)

        onView(withId(R.id.passportNumber))
            .perform(setTextInTextView("2342424234"))

        onView(withId(R.id.nextBtn)).perform(click())

        onView(withId(R.id.passportNumber))
            .check(matches(hasErrorText("Passport number is invalid")))
    }

    @Test
    fun EMPTY_EXPIRY_GET_ERROR() {
        val scenario = ActivityScenario.launch(GetDataForNFCEncryptionActivity::class.java)

        onView(withId(R.id.expiry))
            .perform(setTextInTextView(""))

        onView(withId(R.id.nextBtn)).perform(click())

        onView(withId(R.id.expiry))
            .check(matches(hasErrorText("This fields is mandatory")))
    }
    @Test
    fun EMPTY_BIRTHDATE_GET_ERROR() {
        val scenario = ActivityScenario.launch(GetDataForNFCEncryptionActivity::class.java)

        onView(withId(R.id.birthday))
            .perform(setTextInTextView(""))

        onView(withId(R.id.nextBtn)).perform(click())

        onView(withId(R.id.birthday))
            .check(matches(hasErrorText("This fields is mandatory")))
    }

}

