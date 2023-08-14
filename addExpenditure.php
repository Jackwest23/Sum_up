<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['username']) && isset($_POST['expense_name']) && isset($_POST['cost'])){
    if ($db->dbConnect()) {
        if ($db->addEx("Expenditures", $_POST['username'], $_POST['expense_name'] ,$_POST['cost'])) {
            echo "expense added";
        } else echo "error not added";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
