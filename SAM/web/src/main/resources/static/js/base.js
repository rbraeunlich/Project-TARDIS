function showNotification(e) {
	
	var notification = document.getElementById("notification");
	

	
	if (notification.style.display == "none") {		
		notification.style.display = "block";
	}
}
function closeNotification() {
	document.getElementById("notification").style.display = "none";
}