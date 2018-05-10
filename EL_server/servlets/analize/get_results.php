<?php
function get_results()
{
    /**
    ?request=get_results&lab_number=47&lab_author=Sergey&task_number=1
    **/
    // creating file header
    $command = "results";
    include "header.php";
    fclose($file);

    // system('cd C:\Users\Xiaomi\Desktop & count.py', $retval);

    $result_file = file("DAS_communication/result.txt");
    $result = array();

    // read result from file result.txt
    for ($i=0; $i < count($result_file); $i+=5) {

        // get arrays for building graphics
        $data1 = array_map('intval', explode(' ', rtrim($result_file[i+1])));
        $data2 = array_map('intval', explode(' ', rtrim($result_file[i+3])));

        $graph = array(
            'variable' => rtrim($result_file[$i]),
            'measurements' => $data1,
            'mTarget' => (int) rtrim($result_file[$i+2]),
            'errors' => $data2,
            'eTarget' => (int) rtrim($result_file[$i+4])
        );
        array_push($result, $graph);
    }

    // response
    echo json_encode($result);
}
?>
