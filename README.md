# Smart Table Indicator 📱🍽️

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-orange.svg)](https://firebase.google.com/)
[![ESP32](https://img.shields.io/badge/Hardware-ESP32-blue.svg)](https://www.espressif.com/en/products/socs/esp32)
[![Development](https://img.shields.io/badge/Status-Work%20in%20Progress-orange.svg)](#work-in-progress)
[![Thesis](https://img.shields.io/badge/Academic-Thesis%20Project-blue.svg)](#academic-context)
[![License](https://img.shields.io/badge/License-Academic-yellow.svg)](#)

> **Smart Table Status Indicator Berbasis ESP32 untuk Efisiensi Manajemen Meja pada Restoran Self-Serve**

An IoT-based table management system for self-serve restaurants combining ESP32 hardware indicators with Android mobile application and Firebase cloud integration.

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Work in Progress](#work-in-progress)
- [System Architecture](#system-architecture)
- [Research Methodology](#research-methodology)
- [Technical Implementation](#technical-implementation)
- [Intended Functionality](#intended-functionality)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Academic Context](#academic-context)
- [Author](#author)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## 📖 About

The Smart Table Indicator addresses table management inefficiencies in self-serve restaurants through automated status monitoring. The system integrates ESP32-based hardware indicators with an Android application, enabling real-time table status tracking and staff workflow optimization.

### Problem Statement
Traditional restaurants face operational challenges including:
- Manual table status tracking leading to inefficiencies
- Poor visibility of table availability for both staff and customers
- Delayed response to table cleaning requirements
- Suboptimal table turnover management

### Solution Approach
The system provides automated table status monitoring through:
- Real-time status indication via ESP32-based hardware
- Centralized mobile application for staff management
- Cloud-based data synchronization and storage
- Automated notification system for status changes

## ✨ Features

### Core Functionality
- Real-time table status monitoring and updates
- Staff authentication and profile management
- Push notifications for status changes
- Network connectivity management and error handling
- Responsive user interface with Material Design principles

### Technical Features
- Firebase Realtime Database integration
- ESP32 hardware communication protocols
- Comprehensive error handling and validation
- Offline capability with data synchronization
- Modular architecture for scalability

## 🚧 Work in Progress

This repository contains an undergraduate thesis project currently under development at Universitas Gunadarma. The Smart Table Indicator represents research into IoT applications for restaurant management optimization.

### Development Status
- **Research Phase**: System design and feasibility analysis
- **Implementation**: Android application and ESP32 integration development
- **Testing**: Performance evaluation and system validation
- **Documentation**: Academic documentation and thesis preparation

### Project Scope
The system is designed to demonstrate the integration of IoT sensors with mobile applications for restaurant management. Development focuses on research objectives and academic requirements rather than commercial deployment.

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

### Development Approach
- Agile methodology with iterative development cycles
- User-centered design focusing on restaurant staff workflows
- Integration testing between hardware and software components
- Performance analysis and system optimization

### Validation Methods
- Controlled environment testing of core functionalities
- System response time and reliability measurements
- User interface usability assessment
- Multi-device synchronization validation

## 🏗️ Technical Implementation

### System Architecture
Three-tier architecture connecting IoT sensors, cloud infrastructure, and mobile interfaces. The design ensures real-time data synchronization, scalability, and system reliability.

### Key Technologies
- **Frontend**: Android SDK with Material Design Components
- **Backend**: Firebase Realtime Database and Cloud Messaging
- **Hardware**: ESP32 microcontrollers with WiFi connectivity
- **Communication**: RESTful APIs and WebSocket protocols

### Database Design
NoSQL database structure optimized for real-time operations:
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

### Security Implementation
- Firebase Authentication for user management
- HTTPS/WSS protocols for secure data transmission
- Input validation and sanitization throughout the system

## 🎯 Intended Functionality

### Staff Interface
- Real-time dashboard displaying all table statuses
- User authentication and profile management
- Push notifications for immediate status alerts
- Settings configuration and system preferences

### Table Status Management
- Visual status indicators with LED hardware integration:
  - 🟢 **Green LED**: Table occupied
  - 🔴 **Red LED**: Table requires cleaning
  - **No Light**: Table available for seating
- Automatic status updates across all connected devices
- Historical data collection for usage analysis

### System Administration
- Cloud-based data management through Firebase Console
- Real-time monitoring of hardware device connectivity
- Performance analytics and system health monitoring

## 📁 Project Structure

```
SmartTableIndicator/
├── app/
│   ├── src/main/
│   │   ├── java/com/smarttableindicator/app/
│   │   │   ├── activities/          # Activity classes
│   │   │   ├── adapters/           # RecyclerView adapters
│   │   │   ├── config/             # Configuration constants
│   │   │   ├── models/             # Data models
│   │   │   ├── services/           # Background services
│   │   │   └── utils/              # Utility classes
│   │   ├── res/                    # Resources (layouts, drawables, values)
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts           # App-level build configuration
│   └── google-services.json       # Firebase configuration
├── build.gradle.kts               # Project-level build configuration
├── settings.gradle.kts            # Project settings
└── README.md                      # Project documentation
```

## 🛠️ Technologies Used

### Android Development
- **Language**: Java
- **UI Framework**: Android SDK, Material Design Components
- **Architecture**: MVP pattern with lifecycle-aware components
- **Build System**: Gradle with Kotlin DSL

### Backend Services
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Push Notifications**: Firebase Cloud Messaging (FCM)
- **Analytics**: Firebase Analytics

### Hardware Integration
- **Microcontroller**: ESP32
- **Communication**: WiFi, HTTP/HTTPS protocols
- **Sensors**: Custom table status indicators

### Development Tools
- **IDE**: Android Studio
- **Version Control**: Git & GitHub
- **Dependency Management**: Gradle
- **Testing**: JUnit, Espresso

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
- User interface design following Material Design principles
- Comprehensive error handling and network management

## 👨‍💻 Author

**Vanya Alzenaya Agam**
- Email: [avynala@gmail.com](mailto:avynala@gmail.com)
- GitHub: [@avynalaa](https://github.com/avynalaa)
- Institution: Universitas Gunadarma

## 📄 License

This project is developed for academic purposes as part of a university thesis. All rights reserved to the author and Universitas Gunadarma.

© 2025 Vanya Alzenaya Agam. Universitas Gunadarma.

## 🌟 Acknowledgments

- **Universitas Gunadarma** for academic support and resources
- **Firebase** for cloud infrastructure and development tools
- **Android Developer Community** for documentation and best practices
- **ESP32 Community** for hardware integration guidance and support 