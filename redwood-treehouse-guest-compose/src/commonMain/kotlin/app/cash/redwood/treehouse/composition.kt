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
package app.cash.redwood.treehouse

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.runtime.saveable.SaveableStateRegistry
import app.cash.redwood.compose.LocalUiConfiguration
import app.cash.redwood.compose.RedwoodComposition
import app.cash.redwood.ui.UiConfiguration
import kotlinx.coroutines.flow.StateFlow

// Inline at callsite once https://github.com/Kotlin/kotlinx.serialization/issues/1454 is fixed.
public fun RedwoodComposition.bind(
  treehouseUi: TreehouseUi,
  uiConfigurations: StateFlow<UiConfiguration>,
  saveableStateRegistry: SaveableStateRegistry,
) {
  setContent {
    val uiConfiguration by uiConfigurations.collectAsState()
    CompositionLocalProvider(
      LocalUiConfiguration provides uiConfiguration,
      LocalSaveableStateRegistry provides saveableStateRegistry,
    ) {
      treehouseUi.Show()
    }
  }
}
