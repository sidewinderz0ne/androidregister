<?php
include_once "config.php";

class usr
{
}

$mobile = $_POST["mobile"];
$firstname = $_POST["firstname"];
$lastname = $_POST["lastname"];
$birth = $_POST["birth"];
$gender = $_POST["gender"];
$email = $_POST["email"];


    $num_mobile = mysqli_num_rows(mysqli_query($con, "SELECT * FROM useracc WHERE mobile='" . $mobile . "'"));
    $num_email = mysqli_num_rows(mysqli_query($con, "SELECT * FROM useracc WHERE email='" . $email . "'"));

    if ($num_mobile == 0 && $num_email == 0) {
        $query = mysqli_query($con, "INSERT INTO useracc (id, mobile, firstname, lastname, birth, gender, email) VALUES(0,'" . $mobile . "','" . $firstname . "','" . $lastname . "','" . $birth . "','" . $gender . "','" . $email . "')");
        if ($query) {
            $response = new usr();
            $response->success = 1;
            $response->message = "Account registered!";
            die(json_encode($response));
        } else  {
            $response = new usr();
            $response->success = 0;
            $response->message = "Data error, contact the dev";
            die(json_encode($response)); 
        }
    } else if($num_mobile != 0 && $num_email != 0){
        $response = new usr();
        $response->success = 0;
        $response->message = "E-mail and mobile already registered!";
        die(json_encode($response));
    } else if($num_mobile != 0){
        $response = new usr();
        $response->success = 0;
        $response->message = "Mobile number already exist";
        die(json_encode($response));
    } else if($num_email != 0){
        $response = new usr();
        $response->success = 0;
        $response->message = "E-mail already exist";
        die(json_encode($response));
    }


mysqli_close($con);
