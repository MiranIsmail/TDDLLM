# Installation & Running the Application

## Prerequisites

You need **Java 17+** and **Maven 3.8+**. The database is SQLite — no separate server needed,
the file is created automatically on first run.

---

## 1 — Install Prerequisites

### Linux (Debian/Ubuntu)
```bash
sudo apt update
sudo apt install -y openjdk-17-jdk maven
```

### macOS
```bash
# Install Homebrew first if you don't have it:
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

brew install openjdk@17 maven
# Add Java to your PATH (follow the instructions brew prints after install)
```

### Windows
1. Download and install **Java 17 JDK** from https://adoptium.net  
   During installation, tick "Set JAVA_HOME variable".
2. Download **Maven** from https://maven.apache.org/download.cgi  
   Unzip it, then add the `bin` folder to your `PATH` environment variable.
3. Open a new terminal (PowerShell or Git Bash) and verify:
   ```
   java -version
   mvn -version
   ```

---

## 2 — Verify your install

Run these two commands — both should print a version number without errors:

```bash
java -version    # should say 17 or higher
mvn -version     # should say 3.x.x
```

---

## 3 — Run the application

```bash
# From the experiment/ directory:
mvn compile
mvn exec:java -Dexec.mainClass="com.experiment.App"
```

The server starts on **http://localhost:8080**.

Open your browser to:
- `http://localhost:8080/login`    — login page
- `http://localhost:8080/register` — registration page
- `http://localhost:8080/admin`    — admin panel (static demo data)

---

## 4 — Run the tests

```bash
mvn test                                           # run all tests
mvn test -Dtest=RegistrationTest                   # run one class
mvn test -Dtest=RegistrationTest#successfulRegistrationReturns201  # run one method
```

---

## 5 — Quick-start script (Linux & macOS only)

The script below checks your environment and starts the app in one step.
It does **not** install anything — install Java and Maven first (see above).

```bash
bash install_check.sh
```

The script (`install_check.sh`) is in the `experiment/` directory alongside this file.

---

## Notes

- The SQLite database file (`auth-experiment.db`) is created automatically in the working
  directory the first time you start the app. It is listed in `.gitignore` and will not be committed.
- In test mode (`App.createApp(true)`) the database runs in-memory and is wiped between runs.
- If port 8080 is already in use, edit the `app.start(8080)` line in `App.java` to a free port.
