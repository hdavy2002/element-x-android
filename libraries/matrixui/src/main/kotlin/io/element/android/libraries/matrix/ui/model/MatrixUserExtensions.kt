/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2023-2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.libraries.matrix.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.element.android.libraries.designsystem.components.avatar.AvatarData
import io.element.android.libraries.designsystem.components.avatar.AvatarSize
import io.element.android.libraries.matrix.api.core.UserId
import io.element.android.libraries.matrix.api.user.MatrixUser
import io.element.android.libraries.ui.strings.CommonStrings

/**
 * AvaTok: format a Matrix ID in email style for display, e.g. "@hum:avatok.ai" -> "hum@avatok.ai".
 * Display only — never use this for logic or as an actual identifier (the canonical id is [UserId.value]).
 */
fun UserId.toDisplayId(): String {
    val raw = value.removePrefix("@")
    val local = raw.substringBefore(":")
    val domain = raw.substringAfter(":", missingDelimiterValue = "")
    return if (domain.isEmpty()) local else "$local@$domain"
}

fun MatrixUser.getAvatarData(size: AvatarSize) = AvatarData(
    id = userId.value,
    name = displayName,
    url = avatarUrl,
    size = size,
)

fun MatrixUser.getBestName(): String {
    return displayName?.takeIf { it.isNotEmpty() } ?: userId.toDisplayId()
}

@Composable
fun MatrixUser.getFullName(): String {
    return displayName.let { name ->
        if (name.isNullOrBlank()) {
            userId.toDisplayId()
        } else {
            stringResource(CommonStrings.common_name_and_id, name, userId.toDisplayId())
        }
    }
}
