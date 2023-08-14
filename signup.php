<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['email']) && isset($_POST['phone'])) {
    if ($db->dbConnect()) {
        if ($db->signUp("user", $_POST['username'], $_POST['password'], $_POST['email'], $_POST['phone'])) {
            echo "Sign Up Success";
        } else echo display_errors;
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
