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
package app.cash.redwood.lazylayout.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.LayoutScopeMarker
import app.cash.redwood.Modifier
import app.cash.redwood.layout.api.Constraint
import app.cash.redwood.layout.api.CrossAxisAlignment
import app.cash.redwood.ui.Margin

@LayoutScopeMarker
public interface LazyListScope {
  public fun item(
    content: @Composable () -> Unit,
  )

  public fun items(
    count: Int,
    itemContent: @Composable (index: Int) -> Unit,
  )
}

public inline fun <T> LazyListScope.items(
  items: List<T>,
  crossinline itemContent: @Composable (item: T) -> Unit,
): Unit = items(items.size) {
  itemContent(items[it])
}

public inline fun <T> LazyListScope.itemsIndexed(
  items: List<T>,
  crossinline itemContent: @Composable (index: Int, item: T) -> Unit,
): Unit = items(
  items.size,
) {
  itemContent(it, items[it])
}

public inline fun <T> LazyListScope.items(
  items: Array<T>,
  crossinline itemContent: @Composable (item: T) -> Unit,
): Unit = items(
  items.size,
) {
  itemContent(items[it])
}

public inline fun <T> LazyListScope.itemsIndexed(
  items: Array<T>,
  crossinline itemContent: @Composable (index: Int, item: T) -> Unit,
): Unit = items(
  items.size,
) {
  itemContent(it, items[it])
}

@RequiresOptIn("This Redwood LazyLayout API is experimental and may change in the future.")
public annotation class ExperimentalRedwoodLazyLayoutApi

@Composable
public fun LazyRow(
  state: LazyListState = rememberLazyListState(),
  width: Constraint = Constraint.Wrap,
  height: Constraint = Constraint.Wrap,
  margin: Margin = Margin.Zero,
  verticalAlignment: CrossAxisAlignment = CrossAxisAlignment.Start,
  modifier: Modifier = Modifier,
  placeholder: @Composable () -> Unit,
  content: LazyListScope.() -> Unit,
) {
  LazyList(
    isVertical = false,
    state = state,
    width = width,
    height = height,
    margin = margin,
    crossAxisAlignment = verticalAlignment,
    modifier = modifier,
    placeholder = placeholder,
    content = content,
  )
}

@ExperimentalRedwoodLazyLayoutApi
@Composable
public fun LazyRow(
  refreshing: Boolean,
  onRefresh: (() -> Unit)?,
  state: LazyListState = rememberLazyListState(),
  width: Constraint = Constraint.Wrap,
  height: Constraint = Constraint.Wrap,
  margin: Margin = Margin.Zero,
  verticalAlignment: CrossAxisAlignment = CrossAxisAlignment.Start,
  pullRefreshContentColor: UInt = 0xFF000000u,
  modifier: Modifier = Modifier,
  placeholder: @Composable () -> Unit,
  content: LazyListScope.() -> Unit,
) {
  RefreshableLazyList(
    isVertical = false,
    refreshing = refreshing,
    onRefresh = onRefresh,
    state = state,
    width = width,
    height = height,
    margin = margin,
    crossAxisAlignment = verticalAlignment,
    pullRefreshContentColor = pullRefreshContentColor,
    modifier = modifier,
    placeholder = placeholder,
    content = content,
  )
}

@Composable
public fun LazyColumn(
  state: LazyListState = rememberLazyListState(),
  width: Constraint = Constraint.Wrap,
  height: Constraint = Constraint.Wrap,
  margin: Margin = Margin.Zero,
  horizontalAlignment: CrossAxisAlignment = CrossAxisAlignment.Start,
  modifier: Modifier = Modifier,
  placeholder: @Composable () -> Unit,
  content: LazyListScope.() -> Unit,
) {
  LazyList(
    isVertical = true,
    state = state,
    width = width,
    height = height,
    margin = margin,
    crossAxisAlignment = horizontalAlignment,
    modifier = modifier,
    placeholder = placeholder,
    content = content,
  )
}

@ExperimentalRedwoodLazyLayoutApi
@Composable
public fun LazyColumn(
  refreshing: Boolean,
  onRefresh: (() -> Unit)?,
  state: LazyListState = rememberLazyListState(),
  width: Constraint = Constraint.Wrap,
  height: Constraint = Constraint.Wrap,
  margin: Margin = Margin.Zero,
  horizontalAlignment: CrossAxisAlignment = CrossAxisAlignment.Start,
  pullRefreshContentColor: UInt = 0xFF000000u,
  modifier: Modifier = Modifier,
  placeholder: @Composable () -> Unit,
  content: LazyListScope.() -> Unit,
) {
  RefreshableLazyList(
    isVertical = true,
    refreshing = refreshing,
    onRefresh = onRefresh,
    state = state,
    width = width,
    height = height,
    margin = margin,
    crossAxisAlignment = horizontalAlignment,
    pullRefreshContentColor = pullRefreshContentColor,
    modifier = modifier,
    placeholder = placeholder,
    content = content,
  )
}
