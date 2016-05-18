package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;

//@Controller
public class ExceptionHandlingController implements ErrorController {

	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public String notFound(){
		return "errorpage";
	}
	
	@ResponseStatus(code=HttpStatus.FORBIDDEN)
	@RequestMapping("error")
	public String forbidden(){
		return "errorpage";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
