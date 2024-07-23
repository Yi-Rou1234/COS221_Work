<?php
// Assuming you have already connected to the database

// User Registration
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['register'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];
    // Perform validation on username and password

    // Hash the password
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    // Insert user into the database
    $query = "INSERT INTO users (username, password) VALUES ('$username', '$hashedPassword')";
    // Execute the query
    // ...

    // Redirect to the login page
    header("Location: login.php");
    exit();
}

// User Login
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['login'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];
    // Perform validation on username and password

    // Retrieve the user from the database
    $query = "SELECT * FROM users WHERE username = '$username'";
    // Execute the query and fetch the result
    // ...

    // Verify the password
    if (password_verify($password, $hashedPassword)) {
        // Password is correct, create a session
        session_start();
        $_SESSION['username'] = $username;
        // Redirect to the home page
        header("Location: index.php");
        exit();
    } else {
        // Password is incorrect, display an error message
        $loginError = "Invalid username or password";
    }
}

// Add Wine
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['add_wine'])) {
    $wineName = $_POST['wine_name'];
    $region = $_POST['region'];
    // Validate the input

    // Insert the wine into the database
    $query = "INSERT INTO wines (wine_name, region) VALUES ('$wineName', '$region')";
    // Execute the query
    // ...
}

// Fetch and Display Wines
$query = "SELECT * FROM wines";
// Execute the query and fetch the results
// ...

foreach ($results as $row) {
    echo "<p>Wine Name: " . $row['wine_name'] . "</p>";
    echo "<p>Region: " . $row['region'] . "</p>";
    // Display other relevant information
    echo "<hr>";
}

?>