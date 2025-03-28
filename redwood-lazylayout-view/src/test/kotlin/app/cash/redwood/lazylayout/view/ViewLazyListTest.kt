/*
 * Copyright (C) 2023 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.cash.redwood.lazylayout.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.redwood.Modifier
import app.cash.redwood.layout.AbstractFlexContainerTest
import app.cash.redwood.layout.TestFlexContainer
import app.cash.redwood.layout.api.Constraint
import app.cash.redwood.layout.api.CrossAxisAlignment
import app.cash.redwood.ui.Margin
import app.cash.redwood.widget.Widget
import app.cash.redwood.yoga.AlignItems
import app.cash.redwood.yoga.FlexDirection
import app.cash.redwood.yoga.JustifyContent
import com.android.resources.LayoutDirection
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ViewLazyListTest(
  @TestParameter layoutDirection: LayoutDirection,
) : AbstractFlexContainerTest<View>() {

  @get:Rule
  val paparazzi = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_6.copy(layoutDirection = layoutDirection),
    theme = "android:Theme.Material.Light.NoActionBar",
    supportsRtl = true,
  )

  override fun flexContainer(direction: FlexDirection) = ViewTestFlexContainer(paparazzi.context, direction)

  override fun widget(text: String, modifier: Modifier) = object : Widget<View> {
    override val value = TextView(paparazzi.context).apply {
      background = ColorDrawable(Color.GREEN)
      textSize = 18f
      setTextColor(Color.BLACK)
      this.text = text
    }
    override var modifier = modifier
  }

  override fun verifySnapshot(container: TestFlexContainer<View>, name: String?) {
    paparazzi.snapshot(container.value, name)
  }

  class ViewTestFlexContainer(context: Context, direction: FlexDirection) : TestFlexContainer<View> {
    private val delegate = ViewLazyList(context).apply {
      isVertical(direction == FlexDirection.Column)
      value.background = ColorDrawable(Color.BLUE)
    }
    private var childCount = 0

    override val value get() = delegate.value

    override fun width(constraint: Constraint) {
      delegate.width(constraint)
    }

    override fun height(constraint: Constraint) {
      delegate.height(constraint)
    }

    override fun alignItems(alignItems: AlignItems) {
      val crossAxisAlignment = when (alignItems) {
        AlignItems.FlexStart -> CrossAxisAlignment.Start
        AlignItems.Center -> CrossAxisAlignment.Center
        AlignItems.FlexEnd -> CrossAxisAlignment.End
        AlignItems.Stretch -> CrossAxisAlignment.Stretch
        else -> throw AssertionError()
      }
      delegate.crossAxisAlignment(crossAxisAlignment)
    }

    override fun justifyContent(justifyContent: JustifyContent) {
    }

    override fun margin(margin: Margin) {
      delegate.margin(margin)
    }

    override fun add(widget: Widget<View>) {
      delegate.items.insert(childCount++, widget)
    }
  }
}
