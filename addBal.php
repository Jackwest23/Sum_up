<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['username']) && isset($_POST['balance'])) {
    if ($db->dbConnect()) {
        if ($db->addbalance("balance", $_POST['username'], $_POST['balance']) {
            echo "balance added";
        } else echo "error not added";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
