# Smart Table Indicator 📱🍽️

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-orange.svg)](https://firebase.google.com/)
[![ESP32](https://img.shields.io/badge/Hardware-ESP32-blue.svg)](https://www.espressif.com/en/products/socs/esp32)
[![Development](https://img.shields.io/badge/Status-In%20Development-red.svg)](#project-status)
[![Updates](https://img.shields.io/badge/Updates-Periodic-brightgreen.svg)](#project-status)
[![License](https://img.shields.io/badge/License-Academic-yellow.svg)](#)

> **Smart Table Status Indicator Berbasis ESP32 untuk Efisiensi Manajemen Meja pada Restoran Self-Serve**

An innovative Android application designed to revolutionize table management in self-serve restaurants through real-time status monitoring using ESP32-based indicators and Firebase cloud integration.

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Project Status](#project-status)
- [Screenshots](#screenshots)
- [System Architecture](#system-architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [Academic Context](#academic-context)
- [Author](#author)
- [License](#license)

## 📖 About

The **Smart Table Indicator** is a comprehensive solution for modern restaurant management, specifically designed for self-serve establishments. This Android application works in conjunction with ESP32-based hardware indicators to provide real-time table status monitoring, enabling efficient table turnover and enhanced customer experience.

### Problem Statement
Traditional restaurants face challenges in:
- Manual table status tracking
- Inefficient table turnover management
- Poor customer experience due to unavailable seating information
- Staff workload in monitoring table availability

### Solution
Our smart system provides:
- **Real-time monitoring** of table statuses (Available, Occupied, Dirty)
- **Automated status updates** through IoT integration
- **Staff dashboard** for centralized management
- **Firebase integration** for reliable cloud synchronization
- **Push notifications** for immediate status alerts

## ✨ Features

### 🎯 Core Features
- **Real-time Table Monitoring**: Live status updates for all restaurant tables
- **Multiple Status Types**: Available, Occupied, and Dirty status indicators
- **Staff Authentication**: Secure login system for restaurant staff
- **Profile Management**: Customizable staff profiles with image upload
- **Responsive Grid Layout**: Optimized table view with configurable columns
- **Network Status Monitoring**: Automatic network connectivity management

### 🔧 Technical Features
- **Firebase Integration**: Real-time database synchronization
- **Push Notifications**: FCM (Firebase Cloud Messaging) support
- **Material Design**: Modern and intuitive user interface
- **Navigation Drawer**: Easy access to all app sections
- **Settings Management**: Configurable app preferences
- **Error Handling**: Comprehensive error management system

### 📱 User Interface
- **Dashboard**: Central hub for table status overview
- **Statistics**: Data visualization and analytics (placeholder)
- **Help Section**: Comprehensive app information and thesis details
- **Settings**: App configuration and Firebase connection testing
- **Profile**: Staff profile management with image upload

## 🚧 Project Status

> **⚠️ This is an ongoing academic project under active development!**

### Current Status
- 🔄 **In Development**: This project is actively being developed as part of a thesis research
- 📱 **Prototype Stage**: The Android application is functional but may contain experimental features
- 🔧 **Periodic Updates**: The codebase is regularly updated with improvements and new features
- 📋 **Academic Purpose**: Primary focus is on research and educational objectives

### 🎯 For Users & Reviewers
**Want to try the app without the complexity of Android Studio setup?**
- 📱 **APK Releases**: Check the [Releases](https://github.com/avynalaa/smarttableindicator/releases) section for downloadable APK files *(coming soon)*
- 🎥 **Demo Videos**: Watch functionality demonstrations *(coming soon)*
- 📊 **Live Demo**: Contact the author for demonstration sessions

Setting up Android Studio, SDK, and emulators just to test this app is honestly overkill for most users! I'll be adding easier ways to experience the project as development progresses.

### 📅 Update Schedule
- **Weekly**: Bug fixes and minor improvements
- **Bi-weekly**: Feature additions and enhancements
- **Monthly**: Major updates and documentation improvements

## 📸 Screenshots

*Screenshots and demo materials coming soon...*

## 🏗️ System Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   ESP32 Nodes   │───▶│   Firebase      │◀───│  Android App    │
│   (Hardware)    │    │   (Cloud DB)    │    │   (Mobile)      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
        │                       │                       │
        ▼                       ▼                       ▼
   Table Sensors          Real-time Sync         Staff Dashboard
   Status Updates         Push Notifications     Management Tools
```

## 🔧 Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio** (Latest version recommended)
- **JDK 11** or higher
- **Android SDK** (API level 31-35)
- **Google Services** plugin
- **Firebase project** setup
- **Internet connection** for Firebase synchronization

## 📦 Installation

> **💡 New Here?** If you just want to try the app, skip the complex setup below and check the [Project Status](#project-status) section for easier alternatives!

### For Developers & Contributors

#### 1. Clone the Repository
```bash
git clone https://github.com/avynalaa/smarttableindicator.git
cd smarttableindicator
```

#### 2. Open in Android Studio
1. Launch Android Studio
2. Select "Open an existing project"
3. Navigate to the cloned repository folder
4. Select the `SmartTableIndicator` directory

#### 3. Sync Dependencies
- Android Studio will automatically sync Gradle dependencies
- If not, click "Sync Now" when prompted

#### 4. Build the Project
```bash
./gradlew build
```

## ⚙️ Configuration

### Firebase Setup

1. **Create Firebase Project**:
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project or use existing one

2. **Add Android App**:
   - Package name: `com.smarttableindicator.app`
   - Download `google-services.json`
   - Place it in `app/` directory (already included)

3. **Enable Services**:
   - **Realtime Database**: Enable and configure rules
   - **Cloud Messaging**: Enable for push notifications
   - **Analytics**: Optional but recommended

4. **Database Structure**:
```json
{
  "tables": {
    "table1": {
      "number": 1,
      "status": "available"
    },
    "table2": {
      "number": 2,
      "status": "occupied"
    }
  }
}
```

### App Configuration

1. **Constants Configuration** (`app/src/main/java/com/smarttableindicator/app/config/Constants.java`):
   - Update Firebase database URL if needed
   - Configure table grid columns
   - Set notification channel IDs

2. **Staff Credentials**:
   - Configure initial staff login credentials
   - Set up authentication rules in Firebase

## 🚀 Usage

### For Restaurant Staff

1. **Login**:
   - Enter Staff ID and Password
   - Access granted based on Firebase authentication

2. **Dashboard Navigation**:
   - **Dashboard**: View all table statuses in real-time
   - **Statistics**: Analyze table usage patterns
   - **Settings**: Configure app preferences and test connections
   - **Help**: View app information and thesis details

3. **Profile Management**:
   - Click profile image to upload custom picture
   - Images are stored locally with URI persistence

4. **Table Monitoring**:
   - Tables display in grid layout
   - Color-coded status indicators:
     - 🟢 **Green**: Available
     - 🔴 **Red**: Occupied
     - 🟡 **Yellow**: Dirty/Needs Cleaning

### For System Administrators

1. **Firebase Management**:
   - Monitor database in Firebase Console
   - Configure push notification campaigns
   - Analyze usage through Firebase Analytics

2. **Hardware Integration**:
   - ESP32 nodes automatically update table statuses
   - Real-time synchronization with mobile app
   - Network connectivity monitoring

## 📁 Project Structure

```
SmartTableIndicator/
├── app/
│   ├── src/main/
│   │   ├── java/com/smarttableindicator/app/
│   │   │   ├── activities/          # Activity classes
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── LoginActivity.java
│   │   │   │   ├── SettingsActivity.java
│   │   │   │   └── HelpActivity.java
│   │   │   ├── adapters/           # RecyclerView adapters
│   │   │   │   └── TableAdapter.java
│   │   │   ├── config/             # Configuration constants
│   │   │   │   └── Constants.java
│   │   │   ├── models/             # Data models
│   │   │   │   ├── TableModel.java
│   │   │   │   └── FirebaseTableData.java
│   │   │   ├── services/           # Background services
│   │   │   │   ├── NotificationHelper.java
│   │   │   │   └── SmartTableMessagingService.java
│   │   │   └── utils/              # Utility classes
│   │   │       ├── NetworkManager.java
│   │   │       ├── FirebaseErrorHandler.java
│   │   │       └── ValidationUtils.java
│   │   ├── res/                    # Resources
│   │   │   ├── layout/             # XML layouts
│   │   │   ├── drawable/           # Icons and graphics
│   │   │   ├── values/             # Strings, colors, themes
│   │   │   └── menu/               # Menu resources
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts           # App-level build configuration
│   └── google-services.json       # Firebase configuration
├── build.gradle.kts               # Project-level build configuration
├── settings.gradle.kts            # Project settings
└── README.md                      # This file
```

## 🛠️ Technologies Used

### Android Development
- **Language**: Java
- **UI Framework**: Android SDK, Material Design Components
- **Architecture**: MVP pattern with lifecycle-aware components
- **Build System**: Gradle with Kotlin DSL

### Backend & Cloud Services
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Push Notifications**: Firebase Cloud Messaging (FCM)
- **Analytics**: Firebase Analytics

### Hardware Integration
- **Microcontroller**: ESP32
- **Communication**: WiFi, HTTP/HTTPS
- **Sensors**: Custom table status indicators

### Development Tools
- **IDE**: Android Studio
- **Version Control**: Git & GitHub
- **Dependency Management**: Gradle
- **Testing**: JUnit, Espresso

## 🤝 Contributing

> **📝 Active Development Notice**: This project is under active development with regular updates. Check the latest commits before contributing to avoid conflicts!

This project is part of an academic thesis. While contributions are welcome for educational purposes, please note:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

### Code Standards
- Follow Java coding conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Ensure compatibility with Android API 31+

## 🎓 Academic Context

### Thesis Information
- **Title**: "Rancang Bangun Smart Table Status Indicator Berbasis ESP32 untuk Efisiensi Manajemen Meja pada Restoran Self-Serve"
- **Author**: Vanya Alzenaya Agam
- **Institution**: Universitas Gunadarma
- **Year**: 2025
- **Type**: Undergraduate Thesis (Skripsi)

### Research Objectives
1. Design and implement IoT-based table status monitoring system
2. Develop Android application for real-time table management
3. Evaluate system effectiveness in restaurant environments
4. Analyze impact on operational efficiency and customer satisfaction

### Key Contributions
- Integration of ESP32 hardware with Android mobile application
- Real-time synchronization using Firebase cloud services
- User-friendly interface design following Material Design principles
- Comprehensive error handling and network management

## 👨‍💻 Author

**Vanya Alzenaya Agam**
- Email: [avynala@gmail.com](mailto:avynala@gmail.com)
- GitHub: [@avynalaa](https://github.com/avynalaa)
- Institution: Universitas Gunadarma

## 📄 License

This project is developed for academic purposes as part of a university thesis. All rights reserved to the author and Universitas Gunadarma.

© 2025 Vanya Alzenaya Agam. Universitas Gunadarma.

---

### 🌟 Acknowledgments

- **Universitas Gunadarma** for academic support
- **Firebase** for cloud infrastructure
- **Android Developer Community** for resources and documentation
- **ESP32 Community** for hardware integration guidance

### 📞 Support

For questions, issues, or academic inquiries, please:
1. **Create an issue** in this repository (fastest response for bugs/features)
2. **Contact the author** via email for academic or collaboration inquiries
3. **Check recent commits** - your issue might already be fixed in the latest update!
4. **Refer to the Help section** within the app for basic troubleshooting

> **⏰ Response Time**: Since this is an active project, expect faster responses than typical academic repositories!

---

**Made with ❤️ for academic excellence and innovation in restaurant technology** 