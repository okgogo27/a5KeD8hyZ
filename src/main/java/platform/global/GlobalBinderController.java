package platform.global;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalBinderController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//自动去除输入参数的前后空格
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
