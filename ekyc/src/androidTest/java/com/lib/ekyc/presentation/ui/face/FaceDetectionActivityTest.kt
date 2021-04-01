package com.lib.ekyc.presentation.ui.face

import android.os.Build
import androidx.test.InstrumentationRegistry
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.google.firebase.FirebaseApp
import com.lib.ekyc.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FaceDetectionActivityTest {


    @Before
    fun grantPhonePermission() {
        // In M+, trying to call a number will trigger a runtime dialog. Make sure
        // the permission is granted before running this test.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                "pm grant " + getTargetContext().packageName
                        + " android.permission.CAMERA"
            )
        }
    }

    @Before
    fun init() {
    }

    @Test
    fun PERMISSION_GRANTED_FIREBASE_OK_SHOULD_GET_OK_WITH_FACE_EMPTY_MSG() {
        val context = InstrumentationRegistry.getContext()
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context);
        }

        val scenario = ActivityScenario.launch(FaceDetectionActivity::class.java)

        onView(
            allOf(
                withId(R.id.message),
                withText(R.string.face_empty)
            )
        )
            .check(matches(isDisplayed()));


    }
}

