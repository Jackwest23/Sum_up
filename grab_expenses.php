
<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
 //open connection to mysql db
    $connection = mysqli_connect("127.0.0.1:3306","u305876752_admin","Jubs@1409","u305876752_users_1") or die("Error " . mysqli_error($connection));
      $username = $_POST['username'];
      $table = "expenditure";
    //fetch table rows from mysql db
    $sql = "select * from " . $table . " where username = '" . $username . "'";
    $result = mysqli_query($connection, $sql) or die("Error in Selecting " . mysqli_error($connection));

    //create an array
    $emparray = array();
    while($row =mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }
    echo json_encode($emparray);

    //close the db connection
    mysqli_close($connection);

?>
