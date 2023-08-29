# WearOS-Stopwatch

## Screenshots
![repo-header][def]

[def]: /ss.png "repo-header"

## Description
A simple stopwatch app for WearOS. It has a start/stop button and a reset button. Nothing more, nothing less.
Tested on a Ticwatch C2 running WearOS 2.44. If your watch is running a different version of WearOS, please let me know if it works in the issues tab.

## Compile
1. Clone the repo
2. Open the repo in your terminal of choice
3. Run `./gradlew build`
4. The apk will be in `app/build/outputs/apk/release/app-release-unsigned.apk`

On Windows, use `gradlew.bat` instead of `./gradlew`.

**After compiling, you will need to sign the APK.** Attempting to install the unsigned APK will result in an error.

## Signing
_If you already have a key, skip to step 3._

1. Generate a key with `keytool -genkey -v -keystore keystore_name.keystore -alias alias_name -keyalg RSA -keysize 4096 -validity 10000`
2. Move the key to a safe place, as this is the only way to sign the app in the future
3. Run `jarsigner -verbose -sigalg SHA512withRSA -digestalg SHA-512 -keystore /wherever/you/put/keystore_name.keystore ./app/build/outputs/apk/release/app-release-unsigned.apk alias_name`
4. Run `zipalign -v 4 app-release-unsigned.apk app-release.apk`

## Installing
1. Enable developer mode on your watch (Settings > System > About > Tap build number 7 times)
2. Enable ADB debugging (Settings > System > Developer options > ADB debugging)
3. Connect your watch to your computer (see note below)
4. Run `adb install app-release.apk`

**Note:** If your watch does not have a USB interface, you will need to connect to your watch via Bluetooth. See [this guide](https://developer.android.com/training/wearables/apps/debugging) for more information.

## Final Notes
- If you run into any issues, feel free to start a thread in the "[Q&A](https://github.com/collins-corner/WearOS-Stopwatch-sdk28/discussions/categories/q-a)" section of the discussions tab.
- If the app works on your watch, please let me know in the "[It Works for Me](https://github.com/collins-corner/WearOS-Stopwatch-sdk28/discussions/categories/it-works-for-me)" section of the discussions tab.