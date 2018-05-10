<?php
function push()
{
    /**
    ?request=push&lab_number=47&lab_author=Sergey&task_number=1&data=
    {"velocity":[1,2,3],"path":[14,15]}
    **/

    // creating file header
    $command = "push";
    include "header.php";

    // creating file data
    $data = json_decode($_GET['data']);
    foreach ($data as $variable => $values) {
        $string = "";
        foreach ($values as $value) {
            $string .= " " . $value;
        }
        fwrite($push_file, $variable . $string . "\n");
    }
    fclose($file);
}
?>
