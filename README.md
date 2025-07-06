# 💸 Cashi Mobile App Challenge V1 Solution    

## 🚀 Project Overview

A cross-platform FinTech app built using Kotlin Multiplatform Mobile (KMM) that demonstrates a modern payment flow architecture, secure data handling, Jetpack Compose UI, Firestore storage, and thorough automated testing practices (BDD, Unit and JMeter).

## 🌐 Platforms Supported
•	Android (primary target)
•	iOS-ready shared logic (KMM-compatible)  

## 🧱 Tech Stack
### 🛠 Core Frameworks & Libraries
- Kotlin Multiplatform (KMP) – Shared business logic across Android and potential iOS
- Jetpack Compose – Declarative UI for Android
- Ktor – HTTP client for backend API communication
- Kotlinx Serialization – JSON parsing and encoding
- Kotlinx Datetime – Handling timestamps in a cross-platform way
- Koin – Lightweight dependency injection

### ☁️ Data Layer
- Firebase Firestore – Cloud NoSQL database for storing and syncing transactions
- JSON Server – Local mock backend for simulating API responses

### 🧪 Testing & Quality
- JUnit – Unit testing for core business logic
- Cucumber (BDD) – Behavior-driven tests with Gherkin feature files
- JMeter – API load/performance testing

## 📱 Features Implemented
- ✉ Send Payment screen with form validation and currency picker
- 📅 Transaction History screen (payment list from Firestore)
- ✔ Payment data synced to:
  - API (via Ktor to JSON Server)
  - Firebase Firestore
- ✨ Modern UI with Jetpack Compose
- ⚙ Dependency Injection with Koin
- ✔ Form validation using shared business logic

## 🧱 Architecture Overview
### Project Structure  

<pre>composeApp/ → Android-specific 
├── di/                    # Android-specific Koin modules
├── datasource/firestore/  # Firestore-based local datasource
├── navigation/            # Jetpack Navigation setup
├── ui/                    # UI screens and theme
│   ├── screens/           # SendPayment, TransactionHistory
│   └── theme/             # Color.kt, Theme.kt
├── viewmodel/ → Android ViewModels
├── MainActivity.kt        # Entry point of the Android app
└── FintechApp.kt          # Application class (starts Koin)

shared/ 
├── data/
│   ├── local/             # Local data source interfaces
│   ├── remote/            # Remote API service + interface
│   └── repository/        # Repository pattern abstraction
├── domain/
│   └── usecase/           # Business logic (SendPayment, GetPayments)
├── model/                 # Shared models (e.g., Payment)
├── state/                 # UI state management (sealed UiState)
├── util/                  # Timestamp helpers
└── validation/            # Form validation logic  
</pre>

## 🧭 Visual Diagram – Clean Architecture + MVVM
![Untitled spreadsheet - Sheet1_page-0001](https://github.com/user-attachments/assets/bace6041-ab89-4b0a-9720-ac3bba1c428e)


## ⚙ Setup Instructions

###  ✅ Prerequisites  
- Android Studio Hedgehog+
- Kotlin Multiplatform + Compose Multiplatform support
- Emulator or real Android device
- Firebase account with Firestore (add `google-services.json` in `composeApp/`)

### ✅ Steps  
#### 1.	Clone the Repository  

<pre>git clone <repo-url>
cd FintechPaymentApp </pre>

#### 2.	Start Mock API Server (JSON Server)  

a. Install JSON Server globally (if not already installed):
<pre>npm install -g json-server</pre>

b. Create a db.json file at the root of your project (or in a dedicated backend/ folder) with the following basic structure:
<pre>JSON
{
  "payments": []
}</pre>

c. Start the JSON Server from the terminal in the directory containing
db.json:

<pre>json-server --watch db.json --port 3000</pre>

🔗 API Base URL
- For Android Emulator: http://10.0.2.2:3000
- For local tools like JMeter: http://localhost:3000

Ensure your Ktor client (in shared/) is configured to use the emulator-safe base URL (10.0.2.2).  

#### 3.	Setup Firebase

Used to persist and retrieve transaction history in the cloud.

Steps:  
I.	Create a Firebase Project  
Visit Firebase Console and create a new project.

II.	Enable Firestore Database
- Go to Build → Firestore Database
- Click Create Database

III.	Add Android App to Firebase
- Click the Android icon (⚙️) to register your app
- Enter your package name (com.jetbrains.fintechpayment)
- Download the generated google-services.json  

IV.	Place the file  
Put *google-services.json* in:
<pre>composeApp/src/androidMain/</pre>  

V.	Set Firestore Rules (Development Only)  

Navigate to Firestore → Rules, and replace with:  
<pre>
rules_version = '2';  

service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true; 
      // ⚠️ PUBLIC access — use only for testing. Lock down before deploying!
    }
  }
}  
</pre>
Click Publish.  

#### 4.	Run the App
- Open in Android Studio (latest Hedgehog+ preferred)
- Select Android target
- Hit Run ▶

## 🧪 Testing Guide  

### ✅ Unit Testing  

Unit tests are written using Kotlin's cross-platform *kotlin.test* library and placed under the *commonTest* source set.  

**📄 Example: PaymentValidatorTest.kt**  

Validates core business rules such as:
- Email format
- Positive amount
- Supported currencies

@Test  
<pre>fun `valid email should pass`(){ 
    assertTrue(PaymentValidator.isValidEmail("test@example.com")) 
}</pre>  

@Test  
<pre>fun `unsupported currency should fail`() {  
    assertFalse(PaymentValidator.isSupportedCurrency("INR"))      
}</pre>

### 📂 Project Structure for Unit Test

<img width="572" alt="Screenshot 2025-07-03 at 11 29 28 PM" src="https://github.com/user-attachments/assets/5311409e-9237-4ae3-a131-39b393c27693" />

These tests are platform-independent, fast, and run on CI with no dependencies on Android or Firebase.  

## ✅  Behavior-Driven Development (BDD)  

BDD tests are structured using Cucumber with Gherkin-style scenarios and use fake data sources to simulate real-world behavior without external dependencies.  

### 📂 Project Structure for BDD Test  

<img width="645" alt="Screenshot 2025-07-03 at 11 29 36 PM" src="https://github.com/user-attachments/assets/f1a5fb4f-2ae3-4388-bd9c-9822f9975027" />

### 📜 Feature Files (Gherkin)  

Located in <em>resources/features/</em>, your <em>.feature</em> files describe high-level business behavior.  

<b>payment.feature</b>
<pre>Feature: Payment processing

  Scenario: Valid payment is sent successfully
    Given the user has entered a valid email and amount
    When the user submits the payment
    Then the payment should be processed and stored

  Scenario: Invalid email fails validation
    Given the user has entered an invalid email
    When the user submits the payment
    Then the system should show an error
</pre>

<b>transaction.feature</b>
<pre>Feature: Transaction History

  Scenario: Display transaction history
    Given transactions exist in the Firestore
    When the user opens the transaction history screen
    Then the user should see a list of past transactions
</pre>

### 🧪 Step Definitions (Kotlin)  

- *StepDefinitions.kt* validates form inputs using *PaymentValidator*
- *TransactionStepDefinitions.kt* uses *FakePaymentLocalDataSource* and *GetPaymentsUseCase* to simulate a populated Firestore and fetch transactions

Example step:  
<pre>@Then("the user should see a list of past transactions")
fun assertTransactionListNotEmpty() {
    assertTrue(result != null && result!!.isNotEmpty())
}</pre>  

### 🔌 Fake Data Sources  
- *FakePaymentLocalDataSource*: in-memory transaction store
- *FakePaymentRemoteDataSource*: no-op implementation that always succeeds
- These mocks isolate your BDD from real Firebase/API layers, making tests fast and deterministic.

<pre>./gradlew :shared:testDebugUnitTest --tests "com.jetbrains.fintechpayment.bdd.CucumberTestRunner"
</pre>

## ✅  API Performance Testing with JMeter

To test the performance and reliability of the /payments endpoint under simulated user load using Apache JMeter.  

### ⚙️ Test Configuration

<img width="733" alt="Screenshot 2025-07-04 at 12 12 09 AM" src="https://github.com/user-attachments/assets/723064a1-4213-44ee-abd8-4bbe626836d0" />

📍 Author  

**Priyanka Patil**  
*Senior Android Developer | Kotlin Expert | Mobile Architect*  
[LinkedIn Profile](https://www.linkedin.com/in/priyankaitape/)  
Email: prianka09@gmail.com







































