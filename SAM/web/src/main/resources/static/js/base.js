function showTask(project, task, progress) {
	
	var project_task = document.createElement("div");
	project_task.className = "projectTaskBlock";
	
	<!-- name of task -->
	var task_name = document.createElement("p");
	task_name.className = "taskName";
	var text = document.createTextNode(task);
	task_name.appendChild(text);
	project_task.appendChild(task_name);
	
	<!-- progress of task -->
	var task_progress = document.createElement("progress");
	task_progress.className = "taskProgress";
	task_progress.max = "100";
	task_progress.value = progress;
	project_task.appendChild(task_progress);
		
	var task_list = document.getElementById(project + "TaskList");
	task_list.appendChild(project_task);
}

<!-- function add entry of project to project overview board -->
function showProject(name, description) {
	<!-- create entry block -->
	var project_entry = document.createElement("div");
	project_entry.className = "entry";
	project_entry.id = name;
	
	<!-- block for texts -->
	var text_block = document.createElement("div");
	text_block.className = "textBlock";
	
	<!-- write project name in -->
	var project_name = document.createElement("p");
	project_name.className = "projectNameText";
	var text = document.createTextNode(name);
	project_name.appendChild(text);
	text_block.appendChild(project_name);	
	
	<!-- write project description in -->
	var project_description = document.createElement("p");
	project_description.className = "projectDescription";
	text = document.createTextNode(description);
	project_description.appendChild(text);
	text_block.appendChild(project_description);
	
	project_entry.appendChild(text_block);
	
	<!-- box to show tasks -->
	var taskList = document.createElement("div");
	taskList.className = "projectTaskList";
	taskList.id = name + "TaskList";
	project_entry.appendChild(taskList);
	
	<!-- setting button -->
	var settingForm = document.createElement("form");
	var settingButton = document.createElement("input");
	settingButton.className = "settingButton";
	settingButton.type = "image";
	settingButton.id = name + "Setting";
	settingButton.src = "../image/setting.png";
	settingForm.appendChild(settingButton);
	project_entry.appendChild(settingForm);
		
	var project_board = document.getElementById("project");
	project_board.insertBefore(project_entry, document.getElementById("createProject"));
} 

<!-- event handler on resize of window -->
function resizeWindow() {
	var size = document.getElementById("board").clientWidth;
	
	// Incase enough width, two column of project and personal to-do list
	if (size>1500) {
		document.getElementById("project").style = "float:left;";
		document.getElementById("toDoList").style = "float:right;";
	} else {
		document.getElementById("project").style = "float:none;margin:auto;margin-top:25px;width:80%;overflow:auto;";
		document.getElementById("toDoList").style = "float:none;margin:auto;margin-top:25px;margin-bottom:25px;width:80%;overflow:auto;";
	}
}

function onload() {
	var username = [[${username}]];
	document.getElementById("username").innerHTML = "Hello, " + username;
}

<!-- show notifications on mouse over to icon -->
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

<!-- project creation form appears after click on addProjectIcon -->
function showCreateProjectForm() {
	document.getElementById("createProjectFormWrapper").style.display = "block";
	document.getElementById("createProjectIcon").style.display = "none";
}