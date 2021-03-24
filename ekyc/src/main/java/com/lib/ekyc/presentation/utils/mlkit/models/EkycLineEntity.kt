package com.lib.ekyc.presentation.utils.mlkit.models

data class EkycLineEntity(
    val lineCornerPoints: String?,
    val lineFrame: String?,
    val lineText: String?,
    val elements: List<EkycElementEntity>?
)
