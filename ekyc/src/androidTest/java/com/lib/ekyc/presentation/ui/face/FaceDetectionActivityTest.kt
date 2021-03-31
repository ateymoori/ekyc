package com.lib.ekyc.presentation.ui.face

import androidx.test.core.app.ActivityScenario

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.lib.ekyc.R
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not


@RunWith(AndroidJUnit4::class)
class FaceDetectionActivityTest {
//    @Rule
//    var permissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @Test
    fun extract_Correct_card_details_by_MLKIT_processor() {
        val scenario = ActivityScenario.launch(FaceDetectionActivity::class.java)

//        Espresso.onView(ViewMatchers.withId(R.id.expiry))
//            .perform(TestUtils.setTextInTextView(""))



    }
}

