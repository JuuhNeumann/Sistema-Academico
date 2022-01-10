package web;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Collections;
import java.util.Map;

public class ViewUtil {
	public static String renderiza(String template, Map<String, Object> dados) {
		return new VelocityTemplateEngine().render(new ModelAndView(dados, template));
	}
	
	public static String renderiza(String template) {
		return new VelocityTemplateEngine().render(new ModelAndView(Collections.emptyMap(), template));
	}
}
