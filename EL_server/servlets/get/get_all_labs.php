<?php
function get_all_labs($connection)
{
    $all_labs_data = mysqli_query($connection, "SELECT * FROM `labs`");
    echo "[";
    while ($single_lab = mysqli_fetch_assoc($all_labs_data)) {
        echo json_encode($single_lab), ",";
    }
    echo "]";
}
?>
