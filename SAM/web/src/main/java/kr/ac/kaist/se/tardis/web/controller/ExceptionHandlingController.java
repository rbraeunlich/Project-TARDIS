package kr.ac.kaist.se.tardis.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class ExceptionHandlingController implements ErrorController {

	private static final String KEY_ERROR_MESSAGE = "message";
	
	@Autowired
	private ErrorAttributes errorAttributes;
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public String notFound(){
		return "errorpage";
	}
	
	@ResponseStatus(code=HttpStatus.FORBIDDEN)
	@RequestMapping("error")
	public String forbidden(HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> map = getErrorAttributes(request, false);
		model.addAttribute(KEY_ERROR_MESSAGE, map.get(KEY_ERROR_MESSAGE));
		return "errorpage";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	protected Map<String, Object> getErrorAttributes(HttpServletRequest request,
			boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes(requestAttributes,
				includeStackTrace);
	}
}
