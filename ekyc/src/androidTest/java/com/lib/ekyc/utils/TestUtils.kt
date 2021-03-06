package com.lib.ekyc.utils

import android.app.Instrumentation
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import java.io.InputStream


class TestUtils {

    companion object {

        fun getBitmapFromTestAssets(fileName: String?): Bitmap {
            val testContext: Context = InstrumentationRegistry.getInstrumentation().getContext()
            val assetManager: AssetManager = testContext.assets
            val testInput: InputStream = assetManager.open(fileName!!)
            return BitmapFactory.decodeStream(testInput)
        }


        fun print(str: String) {
            val b = Bundle()
            b.putString(
                Instrumentation.REPORT_KEY_STREAMRESULT, """
         $str
         """.trimIndent()
            )
            InstrumentationRegistry.getInstrumentation().sendStatus(0, b)
        }



        fun setTextInTextView(value: String?): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return CoreMatchers.allOf(
                        ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(
                            EditText::class.java
                        )
                    )
                }

                override fun perform(uiController: UiController, view: View) {
                    (view as EditText).setText(value)
                }

                override fun getDescription(): String {
                    return "replace text"
                }
            }
        }


        fun getText(matcher: Matcher<View?>?): String? {
            val stringHolder = arrayOf<String?>(null)
            onView(matcher).perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return isAssignableFrom(TextView::class.java)
                }

                override fun getDescription(): String {
                    return "getting text from a TextView"
                }

                override fun perform(uiController: UiController, view: View) {
                    val tv = view as TextView //Save, because of check in getConstraints()
                    stringHolder[0] = tv.text.toString()
                }
            })
            return stringHolder[0]
        }

    }
}