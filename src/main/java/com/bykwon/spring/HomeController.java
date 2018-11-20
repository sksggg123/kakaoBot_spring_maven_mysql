package com.bykwon.spring;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bykwon.kakao.service.SearchKeyword;
import com.bykwon.service.JsoupParsingService;
import com.bykwon.vo.kakao.KeyboardVO;
import com.bykwon.vo.kakao.MessageVO;
import com.bykwon.vo.kakao.RequestMessageVO;
import com.bykwon.vo.kakao.ResponseMessageVO;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource(name="jsoupService")
	private JsoupParsingService jsoupParsingService;
	
	@Resource(name="searchKeyword")
	private SearchKeyword searchKeyword;
	
	@Autowired
    BasicDataSource dataSource;
	

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() throws IOException {

		ModelAndView model = new ModelAndView();
		
		// 블로그 크롤링
		final String url = "http://sksggg123.tistory.com/entry/%EC%A0%95%EB%B3%B4-%EA%B0%9C%EB%B0%9C-%EA%B4%80%EB%A0%A8-%EC%A0%95%EB%B3%B4-%EB%A7%81%ED%81%AC";
		jsoupParsingService.getBlogLinkList(model, url);
		
		// 카카오 returnMessage and DB Insert
		searchKeyword.dbConnector(model);
        
		model.setViewName("/home");
		return model;
	}
	
	@RequestMapping(value = "/keyboard", method = RequestMethod.GET)
	@ResponseBody
	public KeyboardVO keyboard() {
		KeyboardVO keyboardVO = new KeyboardVO("유니봇");
		return keyboardVO;
	}

	@RequestMapping(value = "/message", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessageVO message(@RequestBody RequestMessageVO vo) {
		
		ResponseMessageVO returnMessage = new ResponseMessageVO();
		
		searchKeyword.responseMessage(vo, returnMessage);
		
		return returnMessage;
	}
}
