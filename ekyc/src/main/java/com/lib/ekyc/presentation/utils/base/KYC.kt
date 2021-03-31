package com.lib.ekyc.presentation.utils.base

class KYC {
    companion object{
        const val NFC_TIMEOUT  = 10 * 1000
        const val FACE_DETECTION_REQUEST_CODE = 10000
        const val SCAN_DOCUMENT_REQUEST_CODE = 10001
        const val SCAN_DOCUMENT_RESULTS_REQUEST_CODE = 10002
        const val SCAN_PASSPORT_NFC_RESULTS_REQUEST_CODE = 10003
        const val IMAGE_URL = "image_url"
        const val MANDATORY_LIST = "mandatory_list"
        const val RESULTS = "results"
        const val PASSPORT_NUMBER = "PASSPORT_NUMBER"
        const val EXPIRY = "EXPIRY"
        const val BIRTHDATE = "BIRTHDATE"
    }
}