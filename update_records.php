<?php
require "DataBase.php";
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
$db = new DataBase();
if (isset($_POST['username'])) {
    if ($db->dbConnect()) {
        if ($db->updateProfile("user", $_POST['username'],$_POST['email'],$_POST['password'],$_POST['phone']$_POST['old_username'],)) {
            echo "records updated";
        } else echo "no expenses found add one on the stats page";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>