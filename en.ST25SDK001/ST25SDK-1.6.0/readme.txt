**************************
***      Content       ***
**************************

This Zip file contains a release of STMicroelectronics's ST25 Software Development Kit (ST25SDK).

The ST25SDK is a set of tools aimed at accelerating the development process of Java applications based on STMicroelectronics's NFC Tags.
The application can be an Android Application or a PC Application (running on Microsoft Windows, Linux or Apple MacOS).


********************************
*** Quick Installation Guide ***
********************************

* Procedure for Android Application development:
	The ST25SDK installation procedure for Android is available in /integration/android/documentation/ST25AndroidDemoApp_software_guide.pdf.
	This tutorial explains how to create a NFC application from scratch. If you already have your own Android Application and only want to add NFC support, you can skip the Step 1 about creating a "Hello World" application.

	Step 2 explains how to add NFC support to your application. Your application will then be notified when a NFC tag is taped.
	Step 3 explains how to add the ST25SDK libraries to your Android Studio environment.
	Steps 4 and 5 give some examples for reading and writing some info from/to the NFC tag.

* Procedure for PC Application development:
	The ST25SDK installation procedure for PC application development is available in /integration/pc/documentation/ST25PcDemoApp_software_guide.pdf.

	Paragraph "2. Add external libraries for STReader support" explains how to add the libraries "st25sdk.jar" and "st25pc-model-readers.jar".
	The following paragraphs show how to call the st25sdk library from your Java application.


**************************
***    User Manual     ***
**************************

The ST25SDK User Manual is available at http://www.st.com/st25sdk
It contains a description of the SDK + some examples of how to use it to communicate with a NFC Tag.


**************************
***     File Tree      ***
**************************

Here are the main directories contained in this Zip file:

/changelog
    Descriptions of features and bug fixes introduced in each ST25 SDK version
/readme.txt
    This is the current file.

/st25sdk/lib/
    Directory containing the ST25 SDK Library (JAR file)

/st25sdk/documentation/javadoc/
    Javadoc documentation of ST25 SDK Library (contained in /st25sdk/lib/). It describes all the public API of the ST25 SDK library.
--

/readers/feig/lib/
    Java libraries used for FEIG ELECTRONIC readers

/readers/feig/resources/
    Dynamic libraries needed for FEIG readers

/readers/st/lib/
    Java library to be used to benefit from ST reader implementations (Demo boards based on CR95HF, ST25R3911B and ST25R3916 ICs).
    The ST25R3911B-Disco and ST25R3916 boards are only supported on a Windows 32-bit environment.

/readers/st/resources/
    Dynamic libraries needed for ST readers
--

/integration/android/lib/
    Directory containing the Android Reader Interface AAR file (= Android ARchive). This file is specific to Android. When using an Android Smartphone, the SDK uses this reader interface to communicate with Android's NFC API.

/integration/android/helper/
    Directory containing some helper classes that can be used when building an Android NFC Application.

/integration/android/documentation/
    Javadoc documentation of Android reader interface (available in /integration/android/lib/) + Documentation of Android examples

/integration/android/examples/
    Some examples of Android projects based on the ST25 SDK.
--

/integration/pc/lib/
    Directory containing the PC Reader Interface files. Several NFC Readers are supported so there are more than one Reader Interface.

/integration/pc/documentation/
    Documentation of PC examples

/integration/pc/examples/
    Some examples of PC projects based on the ST25 SDK.


**************************
***     Limitations    ***
**************************

    - The CR95HF, ST25R3911B-Disco and ST25R3916-Disco boards are only supported in 32-bit configuration on Windows.
    - The ST25R3911B-Disco board is not supported on Linux.
    - The CR95HF reader board is only supported in 64-bit configuration on Linux.
