<?php
// DataBase config
require_once "includes/db.php";

// get servlets
include "servlets/get/get_lab.php";
include "servlets/get/get_all_labs.php";
include "servlets/get/get_all_tasks.php";
include "servlets/get/get_tasks.php";

// upload servlets
include "servlets/upload/upload_task.php";
include "servlets/upload/upload_lab.php";

// analize servlets
include "servlets/analize/push.php";
include "servlets/analize/get_results.php";

// check rquest option
if (isset($_GET['request'])) {
    switch ($_GET['request']) {


        // get options
        case 'get_lab':
            get_lab($connection);
            break;
        case 'get_all_labs':
            get_all_labs($connection);
            break;
        case 'get_all_tasks':
            get_all_tasks($connection);
            break;
        case 'get_tasks':
            get_tasks($connection);
            break;


        // upload options
        case 'upload_lab':
            upload_lab($connection);
            break;
        case 'upload_task':
            upload_task($connection);
            break;


        // analize options
        case 'push':
            push();
            break;
        case 'get_results':
            get_results();
            break;
    }
}
?>
