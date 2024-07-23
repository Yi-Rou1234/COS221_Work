<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<?php
    session_start();

    $email = $_POST['email'];
    $password = $_POST['password']; 

    // Check if information is inputted
    if (isset($email) && isset($password)) 
    {
        //create database connection
        $conn = mysqli_connect("host", "username", "password", "dbname");

        if (!$conn) 
        {
            die("Connection failed: " . mysqli_connect_error());
        }

        //check if email is in database
        $sql = "SELECT * FROM USER WHERE EMAIL = '$email'";
        $result = $conn->query($sql);
        $data = array();
        if ($result->num_rows > 0) 
        {
            while($row = $result->fetch_assoc()) 
            {
                $data[] = $row; 
            }
        }
        else
        {
            echo "<div>Email does not exist</div>";
            return;
        }

        //get salt and hash password to check if it corresponds
        foreach ($result as $row)
        {
            $name = $row['FIRSTNAME'];
            $surname = $row['LASTNAME'];
            $username = $row['USERNAME'];
            $salt = $row['SALT'];
            $email = $row['EMAIL'];
        }
        
        $hashed_password = hash('sha256', $password . $salt);
        
        //check if password is correct
        $sql1 = "SELECT * FROM USER WHERE "."EMAIL = '$email' AND PASSWORD = '$hashed_password'";
        echo $sql1;
        $result = $conn->query($sql1);
        if ($result->num_rows > 0)
        {
            setcookie('email', $email, time() + (86400 * 30), '/');
            setcookie('name', $name, time() + (86400 * 30), '/');
            setcookie('surname', $surname, time() + (86400 * 30), '/');
            setcookie('username', $username, time() + (86400 * 30), '/');
            $_SESSION['logged_in'] = true;
            echo "<div>Logged in</div>";
            header("Location: index.html");
        }
        else
        {
            echo "<div>Incorrect password</div>";
            return;
        }
    }
    else
    {
        echo "<div>Please fill in email and password</div>";
        exit();
    }
?>