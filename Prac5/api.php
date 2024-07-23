<?php
    require_once 'config.php';
    //$json_data = file_get_contents('php://input');
    $json_return = '{"status":';

    /////////////////////////////
    ///  MOCK DATA FOR FORMAT ///
    /////////////////////////////
    
    //Register
    // $json_data = '{
    //     "type":"register",
    //     "username":"$L$O$G$",
    //     "data":["NULL",
    //             "TestFN",
    //             "TestLN",
    //             "TestUN2",
    //             "test1@test.com",
    //             "123"]
    // }'; 
    
    //Login
    
    // $json_data = '{
    //     "type":"login",
    //     "username":"$L$O$G$",
    //     "data":["test1@test.com",
    //             "123"]
    // }';
    
    //addWine
    /*$json_data = '{
        "type":"addWine",
        "username":"$L$O$G$",
        "data":[
            "NULL",
            "aCoolWine",
            "Red Blend",
            "$24.00",
            "1/1/2020",
            "100",
            "Special Reserve",
            "Israel",
            "NULL",
            "Galilee",
            "NULL",
            "NULL",
            "NULL"]
        }'; */

    //View all reviews
    // $json_data = '{
    //         "type":"viewAllReviews"   
    // }';

    //View all wines
    $json_data = '{
        "type":"viewAllWines"   
    }';
    
    //This is the default format of the JSON string
    //The $L$O$G$ specifies that the user needs to login

    $json_array = json_decode($json_data, true);
    
    if ($json_array["type"] == "register")
    {
        $dbCon = openCon();
        $data = $json_array["data"];
        $userExists = $dbCon->query("SELECT * FROM user WHERE username='" . $data[3] . "'");
        $emailExists = $dbCon->query("SELECT * FROM user WHERE email='" . $data[4] . "'");
        
        //Checks if user already exists in the database
        if ($userExists->num_rows == 0 && $emailExists->num_rows == 0)
        {
            //Retrieving recordcount for insertion
            $record_count = $dbCon->query("SELECT * FROM user")->num_rows;
         
            //Hashing the password
            $hashedPassword = password_hash($data[5],PASSWORD_DEFAULT);

            var_dump(password_verify($data[5],$hashedPassword));

            // Building INSERT query
            $query = "INSERT INTO user (userid,
                                        firstname,
                                        lastname,
                                        username,
                                        email,
                                        password,
                                        salt) 
                                            VALUES 
                                            (" . $record_count+1 .",'"
                                            . $data[1] . "', '"
                                            . $data[2] . "', '"
                                            . $data[3] . "', '" 
                                            . $data[4] . "', '" 
                                            . $hashedPassword . "', 
                                            'SALT')";
            //Executing INSERT query
            $insert_result = $dbCon->query($query);

            //If query was successfull
            if ($insert_result)
            {
                //Returns username
                $json_return .= '"success","time":' . time() . ',"data":"' . $data[3] . '"}';
                echo $json_return;
            }
            //If query was not successful, report internal serverside error
            else 
            {
                $json_return .= '"error","time":' . time() . ',"data":"ERR500"}';
                echo $json_return;
            }
        }
        //User already exists
        else 
        {
            $json_return .= '"error","time":' . time() . ',"data":"userExists"}';
            echo $json_return;
        }
        //Script is finished executing, close database connection
        $dbCon->close();
        exit();
    }
    else if ($json_array["type"] == "login")
    {
        //Check if user exists...
        $dbCon = openCon();
        $data = $json_array["data"];
        $sql_result = $dbCon->query("SELECT * FROM user WHERE email='" . $data[0] . "'");
        
        if ($sql_result->num_rows == 0)
        {
            //User not found
            $json_return .= '"error","time":' . time() . ',"data":"userNotFound"}';
            echo $json_return;
        }
        else
        {
            //User Found
            $given_pass = $data[1];
            $row = mysqli_fetch_assoc($sql_result);

            //Bool value to check if passwords match
            $passwordMatch = password_verify($given_pass,$row["PASSWORD"]);

            if ($passwordMatch)
            {
                echo "THE PASSWORDS MATCH";
                $json_return .= '"success","time":' . time() . ',"data":' . $data[0] . '}';
                echo $json_return;
            }
            else
            {
                echo "NO MATCH";
                $json_return .= '"error","time":' . time() . ',"data":"incorrectPass"}';
                echo $json_return;
            }
        }
        $dbCon->close();
        exit(); 
    }
    else if ($json_array["type"] == "addWine")
    {
        $dbCon = openCon();
        $data = $json_array["data"];
        
        //Checks if user is logged in...
        if ($json_array["username"] == "$L$O$G$")
        {
            $json_return .= '"error","time":' . time() . ',"data":"notLoggedIn"}';
            echo $json_return;
        }
        else
        {

        }
        $dbCon->close();
        exit(); 
    }
    else if ($json_array["type"] == "viewAllWines")
    {
        $dbCon = openCon();
        $sql_result = $dbCon->query("SELECT * FROM wine WHERE wineid mod 50 = 0 LIMIT 50");
        $count = 0;

        if ($sql_result)
        {
            //Sql query executed successfully
            $json_return .= '"success","time":' . time() . ',"data":[ ';
            while ($row = mysqli_fetch_assoc($sql_result))
            {
                //Fetching required values...
                $winery_result = mysqli_fetch_assoc($dbCon->query("SELECT name FROM winery where wineryid=" . $row["WINERYID"]));
                $winery_name = $winery_result["name"];
                $user_score = $row["USERSCORE"];
                $critic_score = $row["CRITICSCORE"];

                //If there is no userscore for the current wine, generate a random score...
                if ($row["USERSCORE"] == NULL)
                    $user_score = random_int(40,86);

                //If there is no criticscore for the current wine, generate a random score...
                if ($row["CRITICSCORE"] == NULL)
                    $critic_score = random_int(30,90);
                
                if ($count < $sql_result->num_rows-1)
                {
                    $json_return .= '{"title":"' . $row["TITLE"] . '", "variety":"' . $row["VARIETY"] . '", "price":"' . $row["PRICE"] . '", "vintage":"' . $row["VINTAGE"] . '", "points":' . $row["POINTS"] . ', "designation":"' . $row["DESIGNATION"] .'", "country":"' . $row["COUNTRY"] . '", "county":"' . $row["COUNTY"] . '", "province":"' . $row["PROVINCE"] . '", "userscore":' . $user_score . ', "criticscore":' . $critic_score . ', "wineryname":"' . $winery_name . '"},';   
                }
                else
                    $json_return .= '{"title":"' . $row["TITLE"] . '", "variety":"' . $row["VARIETY"] . '", "price":"' . $row["PRICE"] . '", "vintage":"' . $row["VINTAGE"] . '", "points":' . $row["POINTS"] . ', "designation":"' . $row["DESIGNATION"] .'", "country":"' . $row["COUNTRY"] . '", "county":"' . $row["COUNTY"] . '", "province":"' . $row["PROVINCE"] . '", "userscore":' . $user_score . ', "criticscore":' . $critic_score . ', "wineryname":"' . $winery_name . '"}';   
    
                $count++;
            }
            $json_return .= '] }';
        }
        else
        {
            $json_return .= '"error","time":'. time() . ',"data":["SQL error in viewAllWines"]';
        }
       
        echo $json_return;
        $dbCon->close();
        exit(); 
    }
    else if ($json_array["type"] == "viewAllWineries")
    {

    }
    else if ($json_array["type"] == "addWinery")
    {
        //Check if $json_array["username"] is not "$L$O$G$"
        $data = $json_array["data"];
        $dbCon = openCon();

    }
    else if ($json_array["type"] == "viewAllReviews")
    {
        $dbCon = openCon();
        $sql_result = $dbCon->query("SELECT * FROM review");

        $json_return .= '"success","time":' . time() . ',"data":[ ';
        $count = 0;
        while ($row = mysqli_fetch_assoc($sql_result))
        {
            //Retrieving the name of the wine...
            $wine_result = mysqli_fetch_assoc($dbCon->query("SELECT title FROM wine WHERE wineid='" . $row["WINEID"] . "'"));
            //var_dump($wine_result);
            $wine_name = $wine_result["title"];

            //Retrieving the name of the user...
            $user_result = mysqli_fetch_assoc($dbCon->query("SELECT username FROM user WHERE userid='" . $row["USERID"] . "'"));
            $user_name = $user_result["username"];

            //Building JSON response
            if ($count < $sql_result->num_rows-1)
            {
                $json_return .= '{"rating":' . $row["RATING"] . ', "comment":"' . $row["COMMENT"] . '", "username":"' . $user_name . '", "winename":"' . $wine_name . '"},';
            }
            else $json_return .= '{"rating":' . $row["RATING"] . ', "comment":"' . $row["COMMENT"] . '", "username":"' . $user_name . '", "winename":"' . $wine_name . '"}';

            $count++;
        }

        $json_return .= '] }';
        echo $json_return;
        $dbCon->close();
        exit;
    }
    else if ($json_array["type"] == "addReview")
    {
        //Check if $json_array["username"] is not "$L$O$G$"
        if ($json_array["username"] == "$L$O$G$")
        {
            $json_return .= '"error","time":' . time() . ',"data":"notLoggedIn"}';
            echo $json_return;
        }
        else
        {
            
        }
    }
    else 
    {
        //Incorrect type specified, return error
        $json_return .= '"error","timestamp":' . time() . ',"data":"Error. Incorrect type specified."}';
        echo $json_return;
    }
?>