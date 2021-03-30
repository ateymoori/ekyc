package com.lib.ekyc.presentation.utils.base

import android.graphics.Bitmap
import org.jmrtd.lds.icao.MRZInfo
import java.io.Serializable

data class PassportDetailsEntity(
    var dateOfBirth: String?,
    var gender: String?,
    var dateOfExpiry: String?,
    var issuingState: String?,
    var documentNumber: String?,
    var personalNumber: String?,
    var nationality: String?,
    var secondaryIdentifier: String?,
    var primaryIdentifier: String?,
    var imageURI: String? = null
//    ,
//    var imageBitmap: Bitmap? = null
) : Serializable

fun MRZInfo.toPassportDetail(): PassportDetailsEntity {
    return PassportDetailsEntity(
        dateOfBirth = this.dateOfBirth,
        gender = this.gender.name,
        dateOfExpiry = this.dateOfExpiry,
        issuingState = this.issuingState,
        documentNumber = this.documentNumber,
        personalNumber = this.personalNumber,
        nationality = this.nationality,
        secondaryIdentifier = this.secondaryIdentifier.replace("<", " ").trim { it <= ' ' },
        primaryIdentifier = this.primaryIdentifier.replace("<", " ").trim { it <= ' ' },
    )
}

fun PassportDetailsEntity.toList(): List<KeyValueEntity> {
    return listOf(
        KeyValueEntity(
            key = "Name",
            value = this.secondaryIdentifier
        ),
        KeyValueEntity(
            key = "Family",
            value = this.primaryIdentifier
        ),
        KeyValueEntity(
            key = "Birthday",
            value = this.dateOfBirth
        ),
        KeyValueEntity(
            key = "nationality",
            value = this.nationality
        ),
        KeyValueEntity(
            key = "gender",
            value = this.gender
        ),
        KeyValueEntity(
            key = "dateOfExpiry",
            value = this.dateOfExpiry
        ),
        KeyValueEntity(
            key = "issuingState",
            value = this.issuingState
        ),
        KeyValueEntity(
            key = "documentNumber",
            value = this.documentNumber
        )
    )

}



