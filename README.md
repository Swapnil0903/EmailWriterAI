# 📧 Email Writer AI

A **full-stack application** that generates professional email replies based on user input and tone.  
Built with **Spring Boot (backend)** and **React + Material UI (frontend)**.

---

## 🚀 Features
- ✍️ Enter an email and select a tone (Professional, Casual, Friendly, etc.)
- ⚡ Generate a suggested reply using backend logic/AI
- 🔄 Smooth loading state with spinner
- 📋 Copy the generated reply to clipboard
- 🛠 Full-stack project (Spring Boot + React)

---

## 🗂 Project Structure
EmailWriterAI/
│
├── email-writer-sb/ # Backend (Spring Boot)
│ ├── src/main/java/... # Controllers, Services
│ ├── src/main/resources/ # application.properties
│ └── pom.xml # Maven config
│
├── email-writer-frontend/ # Frontend (React + Vite)
│ ├── src/ # React components
│ ├── package.json # NPM config
│ └── vite.config.js
│
└── README.md # Project documentation

---

## 🖥️ Backend (Spring Boot)

### Prerequisites
- Java 17+
- Maven or Gradle  

## 💻 Frontend (React + Vite)

### Prerequisites
- Node.js 18+
- npm or yarn

### Run Frontend
```bash
cd email-writer-frontend
npm install
npm run dev
