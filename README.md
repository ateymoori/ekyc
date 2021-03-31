# Android KYC SDK, Read and Extract Documents, Face Detection and Extract Passport by NFC tag

 

### This is an SDK to determine how to Extract document details by ML kit, Detect and extract Face by camera and How to extract and decrypt Passport details by NFC tag
<br><br>
I've separated it into 3 major fanctionality
#### 1- Face Detection by ML Kit
    - Extract Face by front camera
    - Check if just one face exists in the frame and it is in the correct area
#### 2- Extract Document details
    - Scan and extract Texts from documents by back camera
    - Developer can define mandatory strings list (name, family or ...) to check
#### 3- Extract Passport details by NFC tag
    - Extract and Decrypt Passport details by NFC tag
<br><br>
 [![IMAGE ALT TEXT HERE](http://i3.ytimg.com/vi/HEFzfg3Fzj8/hqdefault.jpg)](https://www.youtube.com/watch?v=HEFzfg3Fzj8)
 <br>
 <iframe width="400" height="800"
src="https://www.youtube.com/embed/HEFzfg3Fzj8" 
frameborder="0" 
allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" 
allowfullscreen></iframe>


<br><br>
### Technologies
Full Kotlin
ML KIT
Camera
NFC reader
Junit/Espresso
 
 ### Usage
 1- Add EKYC library to your project
 
 2- For Extract simple documents :

```java
ExtractDocumentActivity.start(
    activity = this
)
```
 3- For Extract documents with mandatory fields :

```java
ExtractDocumentActivity.start(
    activity = this
    mandatoryFields = arrayListOf("name , family")
)
```
 4- For face detection :

```java
FaceDetectionActivity.start(this)
```
 5- For Extract Passport by NFC tag :

```java
GetDataForNFCEncryptionActivity.start(this)
```
 6- For get the results :

```java
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //face detection
        if (requestCode == KYC.FACE_DETECTION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //face detected
                val fileAddress = data?.getStringExtra(IMAGE_URL)
                fileAddress.toast(this)
            }
        }

        //document extraction
        if (requestCode == KYC.SCAN_DOCUMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val fileAddress = data?.getStringExtra(IMAGE_URL)
                val results = data?.getStringExtra(RESULTS)
                "$fileAddress $results".toast(this)
            }
        }
        //NFC extraction
        if (requestCode == KYC.SCAN_PASSPORT_NFC_RESULTS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val results = data?.getSerializableExtra(RESULTS)
                "$results".toast(this)
            }
        }
    }
```
 
<br><br><br>

----------------------------
AmirHossein Teymoori
teymoori.net@gmail.com
