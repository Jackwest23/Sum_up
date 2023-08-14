<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {

        $this->servername = '127.0.0.1:3306';
        $this->username = 'u305876752_admin';
        $this->password = 'Jubs@1409';
        $this->databasename = 'u305876752_users_1';

    }
}

?>
