function showNotification(e) {
	
	var notification = document.getElementById("notification");
	
	var size = document.getElementById("board").clientWidth;
	var x = e.clientX - 150 - (size*1.125);
	var y = e.clientY - 150;	
	notification.style.left = x + "px";
	notification.style.top = y + "px";  
	
	if (notification.style.display == "none") {		
		notification.style.display = "block";
	}
}
function closeNotification() {
	document.getElementById("notification").style.display = "none";
}