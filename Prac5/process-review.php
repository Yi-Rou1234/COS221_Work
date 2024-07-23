<?php
    session_start();

    //gets user input
    $title = $_POST['title'];
    $wineID = $_POST['wine'];
    $rating = $_POST['rating'];
    $comment = $_POST['comment'];

    if ($_SESSION['logged_in'])
    {
        //check if the input is all filled in
        if (empty($title) || empty($wine) || empty($comment) || empty($rating))
        {
            return "Not sufficient information";
        }

        $conn = mysqli_connect('localhost', 'root', '', '');
        //first check userID and check if critic
        $userEmail = $_COOKIE['email'];
        $sql = "SELECT * FROM USER WHERE EMAIL = $userEmail";
        $result = mysqli_query($conn, $sql);
        foreach ($result as $row)
        {
            $userID = $row['USERID'];
        }

        //checking if user is a critic
        $sql = "SELECT * FROM CRITIC WHERE USERID = $userID";
        $result = mysqli_query($conn, $sql);
        $data = array();
        if ($result->num_rows > 0) 
        {
            while($row = $result->fetch_assoc()) 
            {
                $data[] = $row; 
            }
            foreach ($result as $row)
            {
                $criticID = $row['CRITICID'];
            }
        }

        //then check wineID
        $sql = "SELECT * FROM WINE WHERE WINEID = $wineID";
        if ($result->num_rows > 0) 
        {
            while($row = $result->fetch_assoc()) 
            {
                $data[] = $row; 
            }
        }
        else
        {
            echo "<div>Wine does not exist</div>";
            return;
        }

        //need to upload user input to database
        $query = "INSERT INTO REVIEW (RATING, COMMENT, CRITICREVIEW, WINEID, USERID) VALUES ('$rating', '$comment', '0', )";
        $result = mysqli_query($conn, $query);
        if ($result)
        {
            echo "<div>Review uploaded</div>";
        }
        else
        {
            echo "<div>Review not uploaded</div>";
        }
    }
    else
    {
        echo "You are not logged in";
        header("Location: login.html");
    }
?>