<head>
    <meta charset="UTF-8">
    <title>Sign up</title>
</head>
<?php

// Retrieve user input
$name = $_POST['name'];
$surname = $_POST['surname'];
$username = $_POST['username'];
$email = $_POST['email'];
$password = $_POST['password1'];
$password2 = $_POST['password2'];

// Validate user input
if (empty($name) || empty($surname) || empty($email) || empty($password) || empty($username)) {
    die("<div>Please fill in all fields<br></div>");
}

if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    die("<div>Invalid email format<br></div>");
}

if (strlen($password) < 8) {
    die("<div>Password must be at least 8 characters long<br></div>");
}

if (!preg_match("#[0-9]+#", $password)) {
    die("<div>Password must contain at least one number<br></div>");
}

if (!preg_match("#[a-z]+#", $password)) {
    die("<div>Password must contain at least one lowercase letter<br></div>");
}

if (!preg_match("#[A-Z]+#", $password)) {
    die("<div>Password must contain at least one uppercase letter<br></div>");
}

if (!preg_match("#[\W]+#", $password)) {
    die("<div>Password must contain at least one symbol<br></div>");
}

if ($password !== $password2)
{
    die("<div>Passwords do not match<br></div>");
}

//create database connection
$conn = mysqli_connect("host", "username", "password", "dbname");

//Check if email already exists in database
$sql = "SELECT * FROM USER WHERE EMAIL = '$email'";
$result = mysqli_query($conn, $sql);

//if returns data, then email already exists in database
if (mysqli_num_rows($result) > 0) 
{
    die("<div>Email already exists<br></div>");
}

//Hash password with salt
$salt = bin2hex(random_bytes(20));
$hashed_password = hash('sha256', $password . $salt);

//Insert user into database
$sql = "INSERT INTO USER (FIRSTNAME, LASTNAME, USERNAME, EMAIL , PASSWORD, SALT)
        VALUES ('$name', '$surname', '$username', '$email', '$hashed_password', '$salt')";

if (mysqli_query($conn, $sql)) 
{
    echo "<div>Registration successful<br></div>";
} 
else 
{
    echo "<div>Error: " . $sql . "<br>" . mysqli_error($conn) . "<br></div>";
}

mysqli_close($conn);
exit();
?>
