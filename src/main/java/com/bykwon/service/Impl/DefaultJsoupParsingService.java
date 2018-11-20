package com.bykwon.service.Impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.bykwon.kakao.service.impl.DefaultSearchKeyword;
import com.bykwon.service.JsoupParsingService;

public class DefaultJsoupParsingService implements JsoupParsingService {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultJsoupParsingService.class);

	@Override
	public ModelAndView getBlogJsoupParsing(ModelAndView model) {
		
		try {
			model.addObject("naver1", getTestJsoupMethod("naver")[0]);
			model.addObject("naver2", getTestJsoupMethod("naver")[1]);
			model.addObject("naver3", getTestJsoupMethod("naver")[2]);
			
			model.addObject("google1", getTestJsoupMethod("google")[0]);
			model.addObject("google2", getTestJsoupMethod("google")[1]);
			model.addObject("google3", getTestJsoupMethod("google")[2]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@Override
	public ModelAndView getBlogLinkList(ModelAndView model, String url) {
		
		try {
			getBlogContent(model, url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	
	
	
	// Blog link만 추출
	private static ModelAndView getBlogContent(final ModelAndView model, final String url) throws IOException {
		
		String returnMessage1 = "N";
		String linkList = "";
		
		try {
			String connUrl = url;
			Document doc2 = Jsoup.connect(connUrl).post();
			Elements links = doc2.select("a[href]").select(".tx-link");
			returnMessage1 = doc2.toString();
			
			for (Element link : links) {
				linkList += (link.attr("abs:href") +"<br/>");
			}
			
			returnMessage1 = "Y";
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String returnMessage[] = {returnMessage1, linkList};
		model.addObject("flag", returnMessage[0]);
		model.addObject("linkList", returnMessage[1]);
		return model;
	}
	
	// WEB page 추출
	private static String[] getTestJsoupMethod(String type) throws IOException {
		
		String returnMessage1 = "do not return message1..." + type;
		String returnMessage2 = "do not return message2..." + type;
		String returnMessage3 = "do not return message3..." + type;
		
		if (type.equals("naver")) {
			try {
				String connUrl = "http://sksggg123.tistory.com/entry/%EC%A0%95%EB%B3%B4-%EA%B0%9C%EB%B0%9C-%EA%B4%80%EB%A0%A8-%EC%A0%95%EB%B3%B4-%EB%A7%81%ED%81%AC";
				Document doc2 = Jsoup.connect(connUrl).post();
				returnMessage1 = doc2.toString();
				returnMessage2 = doc2.data();
				returnMessage3 = doc2.html();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				String connUrl = "http://sksggg123.tistory.com/entry/%EC%A0%95%EB%B3%B4-%EA%B0%9C%EB%B0%9C-%EA%B4%80%EB%A0%A8-%EC%A0%95%EB%B3%B4-%EB%A7%81%ED%81%AC";
				Document doc2 = Jsoup.connect(connUrl).get();
				returnMessage1 = doc2.toString();
				returnMessage2 = doc2.data();
				returnMessage3 = doc2.html();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String returnMessage[] = {returnMessage1, returnMessage2, returnMessage3};
		
		return returnMessage;
	}

}
