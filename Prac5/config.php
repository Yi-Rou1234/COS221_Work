<?php
    function openCon() {
        //Database connection details
        $host = "localhost";
        $dbUsername = "root";
        $dbPassword = "root";
        $dbName = "testdb";

        //Creating a returning a new mysqli connection object
        return new mysqli($host,$dbUsername,$dbPassword,$dbName);
    }
?>