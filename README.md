# 🛒 ECommerce Web Automation Test Suite

This project is a comprehensive automation test suite for [Automation Test Store](https://automationteststore.com/) developed using **Java**, **Selenium WebDriver**, **TestNG**, and **Maven** (if used). It automates various key functionalities of an eCommerce website, including product selection, cart validation, user registration, form validations, and reporting.

---

## 📌 Project Overview

| Task | Description |
|------|-------------|
| ✅ Product Selection | Randomly selects and adds 2 in-stock products to the cart |
| 🛒 Cart Verification | Validates cart items, quantity, price, and subtotal |
| 👤 User Registration | Fills out and submits registration form using test data |
| ❌ Form Validation | Leaves required fields blank to trigger error messages |
| 📸 Screenshots | Captures screenshots on test failures |
| 📂 Reporting | Logs cart total, skipped elements, and failed validations into `report.txt` |
| 📄 Data Driven | Reads user registration details from `testdata.csv` |
| ⚙ TestNG Integration | Executes all test cases via `testng.xml` file |

---

## 🛠 Tech Stack

- **Java**
- **Selenium WebDriver**
- **TestNG**
- **WebDriverManager**
- **Maven** (optional)
- **TestNG XML**
- **CSV for test data**
- **JUnit/TestNG assertions**
- **Java IO for custom logging**

---

## 📁 Folder Structure

src/
├── main/
│ └── java/
│ └── com.automation.base/ # Base setup classes
├── test/
│ └── java/
│ └── com.automation.test/ # Test classes
│ └── resources/
│ └── testdata.csv # Test data for registration
screenshots/ # Captured screenshots
report.txt # Logs test summary


---

## 🧪 How to Run

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/ecommerce-automation.git

2. Open in Eclipse/IntelliJ

3. Make sure ChromeDriver/WebDriverManager is configured

4. Update testdata.csv if needed

5. Run test suite:

  - via testng.xml

  - or run individual test classes




