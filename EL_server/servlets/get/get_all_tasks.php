<?php
function get_all_tasks($connection)
{
    $all_tasks_data = mysqli_query($connection, "SELECT * FROM `tasks`");
    echo "[";
    while ($single_task = mysqli_fetch_assoc($all_task_data)) {
        echo json_encode($single_task), ",";
    }
    echo "]";
}
?>
