package com.bykwon.service;

import org.springframework.web.servlet.ModelAndView;

public interface JsoupParsingService {
	
	public ModelAndView getBlogJsoupParsing(final ModelAndView model);
	
	public ModelAndView getBlogLinkList(final ModelAndView model, final String url);

}
