<?php
require_once "config.php";

$connection = mysqli_connect(
    $config['server'],
    $config['username'],
    $config['password'],
    $config['database']
);

if ($connection == false) {
    echo "cant connect to this data base!";
    echo mysqli_connect_error();
    exit();
}
?>
