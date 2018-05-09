<?php  

	$image = $_POST["image"];
	$name = $_POST["name"];
	$path = "up/$name.jpg";



	file_put_contents($path, base64_decode($image));
	//echo json_encode(array('response'=>'success full'));






















?>