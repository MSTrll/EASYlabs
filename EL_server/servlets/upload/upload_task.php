<?php
function upload_task($connection)
{
    if (isset($_GET['name']) & isset($_GET['number']) & isset($_GET['author'])
    & isset($_GET['text']) & isset($_GET['parent']))
    {
        $req = 'INSERT INTO `tasks` (`id`, `name`, `parent`, `number`, `text`, `formula`, `author`) VALUES '
        . '( NULL, \'' . $_GET['name'] . '\', \'' . $_GET['parent'] . '\', \'' . $_GET['number'] . '\', \''
        . $_GET['text']  . '\', NULL, \'' . $_GET['author'] . '\')';
        $upload_request = mysqli_query($connection, $req);
    }
}
?>
