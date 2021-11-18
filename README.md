
# Android KYC SDK, Read and Extract Documents, Face Detection and Extract Passport by NFC tag


### This is an SDK to determine how to Extract document details by ML kit, Detect and extract Face by camera and How to extract and decrypt Passport details by NFC tag
<br>
<img src="https://parkup.app/website/screens/dashboard.png" width="350" height="700">
<br>

### ٰDemo Videos : 
[<img src="https://parkup.app/website/screens/youtube_face.png" width="250" height="500">](https://www.youtube.com/watch?v=SdR3FgrJIkY)
[<img src="https://parkup.app/website/screens/youtube_document.png" width="250" height="500">](https://www.youtube.com/watch?v=x1BGmbAZ-iw) 
[<img src="https://parkup.app/website/screens/youtube_nfc.png" width="250" height="500">](https://www.youtube.com/watch?v=xHlW9XRWfY) 

<br><br>

[Download Sample APK](https://parkup.app/website/screens/ekyc.apk)

<br>

I've separated it into 3 major functionality
#### 1- Face Detection by ML Kit
    - Extract Face by front camera
    - Check if just one face exists in the frame and it is in the correct area
#### 2- Extract Document details
    - Scan and extract Texts from documents by back camera
    - Developer can define mandatory strings list (name, family or ...) to check
#### 3- Extract Passport details by NFC tag
    - Extract and Decrypt Passport details by NFC tag
<br><br>

### Technologies
Full Kotlin, 
ML KIT, 
Camera, 
NFC reader, 
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
 
<br><br>
### TEST/CI CD
Also I'm using GitHub Actions as CI/CD. As I defined before, If I push the codes into any branch except master/release, All tests will run, Then If all of them passed, one APK will be built and uploads into the Github actions artifact.

<img src="https://parkup.app/website/screens/8.png" alt="Android Architecture " width=530 /> <img src="https://parkup.app/website/screens/9.png" alt="Android Architecture " width=450 />

<br><br>

----------------------------
AmirHossein Teymoori
teymoori.net@gmail.com
