<?php
function upload_lab($connection)
{
    if (isset($_GET['title']) & isset($_GET['number']) & isset($_GET['author']))
    {
        $req = 'INSERT INTO `labs`(`id`, `title`,`number`, `author`, `taskcount`) VALUES '
        . '( NULL, \'' . $_GET['title'] . '\', \'' . $_GET['number'] . '\', \'' . $_GET['author']
        . '\', \'0\')';
        $upload_request = mysqli_query($connection, $req);
    }
}
?>
