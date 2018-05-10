<?php
function get_tasks($connection)
{
    if (isset($_GET['parent']) & isset($_GET['author']))
    {
        $req = 'SELECT * FROM `tasks` WHERE `parent`='
        . $_GET['parent'] . ' AND `author`= \'' . $_GET['author'] . '\'';
        $tasks_data = mysqli_query($connection, $req);
        echo "[";
        while ($single_task = mysqli_fetch_assoc($tasks_data)) {
            echo json_encode($single_task), ",";
        }
        echo "]";
    }
}
?>
