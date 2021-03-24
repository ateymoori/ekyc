package com.lib.ekyc.presentation.utils.mlkit.models

data class EkycBlockEntity(
    val blockCornerPoints: String?,
    val blockFrame: String?,
    val lines: List<EkycLineEntity>?
)

