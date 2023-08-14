
<?php
require "DataBaseConfig.php";
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table,$username,$password,$email ,$phone_number )
    {

        $username = $this->prepareData($username);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $email = $this->prepareData($email);
       $phone_number = $this->prepareData($phone_number);

        $this->sql =
        "INSERT INTO user (username,email,password,phone_number) Values ('jurrien','pass','jubs','091')";
            //"INSERT INTO " . $table . " ( username, password, email,phone_number) VALUES ('" . $username . "','" . $password . "','" . $email . "','" . $phone_number . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }
    function addbalance($table,$username,$balance){
     $username = $this->prepareData($username);
     $balance = $this->prepareData($balance);
      $this->sql = "INSERT INTO " . $table . " ( username,balance) VALUES ('" . $username . "','" . $balance . "')";
       if (mysqli_query($this->connect, $this->sql)) {
                  return true;
              } else return false;
    }

}

?>
