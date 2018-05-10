<?php
function get_lab($connection)
{
    if (isset($_GET['number']) & isset($_GET['author']))
    {
        $req = 'SELECT * FROM `labs` WHERE `number`'
        . '="' . $_GET['number'] . '" AND `author`= "'
        . $_GET['author'] . '"';

        $all_labs_data = mysqli_query($connection, $req);

        $single_lab = mysqli_fetch_assoc($all_labs_data);
        echo json_encode($single_lab);
    }
}
?>
