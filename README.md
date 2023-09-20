# FootPrint

**FootPrint** is an Android application developed by students Igor Shverlo (wwerlosh) and Ilya Fetyukhin (mastkeyxgod) to participate in the All-Russian contest "Your Move". The app is designed to calculate a person's carbon footprint and help users reduce it.

## About the app

### Description

The **FootPrint** application is developed using Kotlin and provides the user with the ability to:

- Evaluate the carbon footprint of your daily activities.
- Keep track of your carbon footprint statistics.
- Receive recommendations on reducing the carbon footprint.

### Technologies

The application is developed using the following technologies and tools:

- Android (Kotlin)
- A database (MySQL) for storing statistics and a log of user actions.
- Interface through fragments for more convenient application management.
- A variety of libraries and resources for a convenient and intuitive user experience.

## Installation and launch

For the **FootPrint** application to work successfully, you need to configure access to your database. You will need to provide the following information:

- A link to your database.
- Login to access the database.
- Password to access the database.

### Step 1: Create a database

Create a MySQL database if you don't have one yet. Make sure that you have access to this database and know the URL to connect to.

### Step 2: Change the connection settings

Open the `SqlHelper.kt` file in the project and replace the following lines with information about your database:

```kotlin
val c = DriverManager.getConnection(
"jdbc:mysql://your-host:port/your-database",
"your-login",
    "your-password"
)
