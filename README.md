# Smart Table Indicator 📱🍽️

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-orange.svg)](https://firebase.google.com/)
[![ESP32](https://img.shields.io/badge/Hardware-ESP32-blue.svg)](https://www.espressif.com/en/products/socs/esp32)
[![Development](https://img.shields.io/badge/Status-Work%20in%20Progress-orange.svg)](#work-in-progress)
[![Thesis](https://img.shields.io/badge/Academic-Thesis%20Project-blue.svg)](#academic-context)
[![License](https://img.shields.io/badge/License-Academic-yellow.svg)](#)

> **Smart Table Status Indicator Berbasis ESP32 untuk Efisiensi Manajemen Meja pada Restoran Self-Serve**

An innovative Android application designed to revolutionize table management in self-serve restaurants through real-time status monitoring using ESP32-based indicators and Firebase cloud integration.

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Work in Progress](#work-in-progress)
- [Screenshots](#screenshots)
- [System Architecture](#system-architecture)
- [Research Methodology](#research-methodology)
- [Technical Implementation](#technical-implementation)
- [Exploring the Code](#exploring-the-code)
- [Intended Functionality](#intended-functionality)
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

## 🚧 Work in Progress

> **This repository showcases an ongoing undergraduate thesis project currently under development.**

### Project Overview
This is an **active research and development project** for my undergraduate thesis at Universitas Gunadarma. The Smart Table Indicator represents an innovative approach to restaurant management technology, combining IoT hardware with modern mobile applications.

### Current Development Phase
- 🔬 **Research Stage**: Conducting feasibility studies and system design
- 💻 **Implementation**: Building the Android application and ESP32 integration
- 📊 **Testing**: Evaluating system performance and user experience
- 📝 **Documentation**: Preparing academic documentation and thesis materials

### What You Can Expect
- **Regular Updates**: This repository is actively maintained with frequent commits
- **Evolving Features**: New capabilities are continuously being added and refined
- **Academic Focus**: Development prioritizes research objectives and educational value
- **Future Completion**: A fully functional system will be available upon thesis completion

### 🎯 Final Product Vision
Upon completion, this project will demonstrate:
- Real-time table status monitoring in restaurant environments
- Seamless integration between IoT sensors and mobile applications
- Efficient staff workflow management through digital solutions
- Scalable architecture suitable for various restaurant sizes

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

## 🎓 Research Methodology

This project employs a comprehensive research approach combining theoretical analysis with practical implementation:

### Development Approach
- **Agile Methodology**: Iterative development with regular testing and refinement
- **User-Centered Design**: Focus on restaurant staff workflow and usability
- **IoT Integration**: Seamless connection between hardware sensors and software systems
- **Academic Rigor**: Documented processes and measurable outcomes for thesis evaluation

### Testing & Validation
- **Prototype Testing**: Controlled environment validation of core functionalities
- **Performance Analysis**: System response time and reliability measurements
- **User Experience Studies**: Staff interaction and workflow efficiency assessment
- **Scalability Testing**: Multi-device and multi-location capability evaluation

## 🔍 Exploring the Code

This repository contains the complete source code for the Smart Table Indicator system. The codebase is organized following Android development best practices and includes comprehensive documentation for academic review.

### Repository Structure
The project follows standard Android application architecture with clear separation of concerns and modular design principles. All source code is available for academic review and educational purposes.

### Code Quality & Standards
- **Professional Structure**: Clean, well-organized codebase following industry standards
- **Comprehensive Documentation**: Detailed code comments and documentation
- **Modern Technologies**: Implementation using current Android development practices
- **Academic Rigor**: Code developed with thesis requirements and academic review in mind

## 🏗️ Technical Implementation

### System Architecture
The Smart Table Indicator employs a three-tier architecture connecting IoT sensors, cloud infrastructure, and mobile interfaces. This design ensures scalability, reliability, and real-time data synchronization.

### Key Technologies
- **Frontend**: Android SDK with Material Design principles
- **Backend**: Firebase Realtime Database for instantaneous updates
- **Hardware**: ESP32 microcontrollers for sensor data collection
- **Communication**: RESTful APIs and WebSocket connections for real-time sync

### Database Design
The system utilizes a NoSQL database structure optimized for real-time operations:
```json
{
  "tables": {
    "table1": {
      "number": 1,
      "status": "available",
      "timestamp": "2025-01-01T12:00:00Z"
    }
  }
}
```

### Security & Authentication
- User authentication through Firebase Auth
- Secure data transmission with HTTPS/WSS protocols
- Role-based access control for staff management

## 🎯 Intended Functionality

Upon project completion, the Smart Table Indicator will provide comprehensive restaurant management capabilities:

### Staff Interface
- **Real-time Dashboard**: Live monitoring of all table statuses
- **Intuitive Controls**: Simple, user-friendly interface for staff operations
- **Smart Notifications**: Automated alerts for table status changes
- **Profile Management**: Personalized staff accounts and preferences

### Table Status Management
- **Visual Indicators**: Color-coded system for immediate status recognition
  - 🟢 **Available**: Ready for new customers
  - 🔴 **Occupied**: Currently in use
  - 🟡 **Cleaning Required**: Needs attention
- **Automatic Updates**: Real-time synchronization across all devices
- **Historical Data**: Usage patterns and analytics for optimization

### System Administration
- **Cloud Dashboard**: Web-based management interface
- **Performance Analytics**: Data-driven insights for restaurant operations
- **Hardware Monitoring**: ESP32 device status and connectivity tracking
- **Scalable Infrastructure**: Support for multiple restaurant locations

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