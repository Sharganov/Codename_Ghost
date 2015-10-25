# Codename: Ghost  [![Build Status](https://travis-ci.org/MaKToff/Codename_Ghost.svg)](https://travis-ci.org/MaKToff/Codename_Ghost)
Codename: Ghost is a 2D [shoot 'em up](https://en.wikipedia.org/wiki/Shoot_%27em_up) platformer.

## Getting started
Here's a quick overview on how to build the game.

First of all you should download the project. Use
```bash
$ git clone https://github.com/maktoff/codename_ghost cg
$ cd cg
```
or click `Dowload ZIP`.

### Installing JDK
If you haven't installed JDK 1.7, install it. Use the following command
```bash
$ sudo apt-get update
$ sudo apt-get install openjdk-7-jdk
```
or download it from offical [website](http://www.oracle.com/technetwork/java/javase/downloads/1880260).

### Installing Andoid SDK
Our project uses Android SDK. For work you need to install it following the instructions below.

#### Linux
1. Download SDK using [link](http://dl.google.com/android/android-sdk_r24.4-linux.tgz).
2. After downloading extract files into directory `cg`.
3. Execute file `android`:

   ```bash
   $ ./android-sdk-linux/tools/android
   ```

4. Install Android API 20 and Build-tools 20.0.0.
5. Create file with information where SDK is located:

   ```bash
   $ touch local.properties
   $ echo "sdk.dir=android-sdk-linux" > local.properties
   ```

#### Windows
1. Download SDK using [link](http://dl.google.com/android/android-sdk_r24.4-windows.zip).
2. After downloading extract files into the root folder of the project.
3. Execute file `~/android-sdk-windows/SDK Manager.exe`.
4. Install Android API 20 and Build-tools 20.0.0.
5. Create file `local.properties` in the root folder of the project. This file should contain following text:

   ```
   sdk.dir=android-sdk-windows
   ```

### Building the project
To build the project run 
```bash
$ chmod +x gradlew
$ ./gradlew
```
or execute file `gradlew.bat` for Windows.

## Platforms
At the present game supports following platforms:
- Android 4.0.3+;
- Linux;
- Windows 7, 8, 8.1, 10 (32 or 64 bit).
