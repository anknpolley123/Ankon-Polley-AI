# Ankon-Polley-AI


[![Release](https://img.shields.io/badge/Release-v1.0.0-success?style=flat-square)](https://github.com/anknpolley123/Ankon-Polley-AI/releases/tag/v1.0.0)
[![Platform](https://img.shields.io/badge/Platform-Android-brightgreen?style=flat-square)](#)
[![Environment](https://img.shields.io/badge/Environment-Google%20IDX-blue?style=flat-square)](#)


A high-performance Android application integrated with local AI capabilities. Built and compiled within a declarative cloud-based development environment utilizing customized build tools and optimized model execution layer.

---

## 🚀 Features

* **Local AI Processing:** Runs logic and inference directly on-device without relying on continuous external API data.
* **Declarative Environment Setup:** Locked-down dependency management ensuring consistent builds across any workstation.
* **Streamlined Asset Pipeline:** Efficient integration handles large embedded localized model weights.

---

## 📲 Installation

Since the compiled application is 1.3 GB due to the embedded local AI models, downloading it directly from standard cloud workspaces can face device or connection timeouts. The recommended stable installation source is through GitHub Releases.

1. Go to the **[Releases Section](https://github.com/anknpolley123/Ankon-Polley-AI/releases)** of this repository.
2. Expand the **Assets** dropdown under the latest version (`v1.0.0`).
3. Tap on **`app-debug.apk`** to begin downloading the file straight to your device downloads folder.
4. Open your Android file manager, select the downloaded APK, and tap **Install**. *(If prompted, allow your browser or file manager permission to install apps from unknown sources).*

---

## 🛠️ How to Build the Application

This project uses a specialized system package configuration via `dev.nix` to natively automate system packages within Google IDX or Firebase Studio.

### 1. Prerequisites
The build environment configuration automatically ensures the correct versions of the development suite are loaded:
* **Java Development Kit:** JDK 17 (`pkgs.jdk17`)
* **Build Automation:** Gradle (`pkgs.gradle`)
* **GitHub CLI Deployment Link:** (`pkgs.gh`)

### 2. Compilation
To compile a fresh standalone Android Package (APK), open your environment's terminal container and execute the Gradle build script from your project root:

```bash
./gradlew assembleDebug
