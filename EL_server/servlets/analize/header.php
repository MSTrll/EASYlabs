<?php
if (isset($_GET['lab_number']) & isset($_GET['lab_author'])
    & isset($_GET['task_number']))
{
    $lab_number = $_GET['lab_number'];
    $lab_author = $_GET['lab_author'];
    $task_number = $_GET['task_number'];

    $file = fopen("DAS_communication/command.txt", "w+t");
    fwrite($file, $command . "\n" . $lab_number . "\n"
            . $lab_author . "\n" . $task_number . "\n");
}
?>
