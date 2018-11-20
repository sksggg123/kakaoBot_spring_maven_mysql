package com.bykwon.kakao.service;

import org.springframework.web.servlet.ModelAndView;

import com.bykwon.vo.kakao.RequestMessageVO;
import com.bykwon.vo.kakao.ResponseMessageVO;

public interface SearchKeyword {

	public ResponseMessageVO responseMessage(final RequestMessageVO vo, final ResponseMessageVO returnMessage);
	
	public void dbConnector(final ModelAndView model);
	
}
