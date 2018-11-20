package com.bykwon.kakao.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.bykwon.kakao.service.SearchKeyword;
import com.bykwon.vo.kakao.MessageVO;
import com.bykwon.vo.kakao.RequestMessageVO;
import com.bykwon.vo.kakao.ResponseMessageVO;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

@Service
public class DefaultSearchKeyword implements SearchKeyword {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultSearchKeyword.class);
	
    private BasicDataSource dataSource;

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void dbConnector(ModelAndView model) {
		Connection conn = null;
	    Statement st = null;
	    
	    try {
	        conn = getDataSource().getConnection();
	        st = conn.createStatement();
	        ResultSet rs = st.executeQuery("select _id, name, descript from LINK_REPOSITORY;");
	        
	        List<String> result = new ArrayList<String>();
	        
	        while(rs.next()) {
	        	int _id = rs.getInt("_id");
	        	String name = rs.getString("name");
	        	String descript = rs.getString("descript");
	        	
	        	result.add(_id+" | " + name + " | " + descript);
	        }
	        
	        model.addObject("result", result);
	        
	    } catch (Exception e) {
	        e.printStackTrace();    
	        
	    } finally {
	        try {
	            if(st != null) st.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        try {
	            if(conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }                        
	    }
		
	}

	@Override
	public ResponseMessageVO responseMessage(final RequestMessageVO vo, final ResponseMessageVO returnMessage) {
		
		MessageVO mes_vo = new MessageVO();
		
		mes_vo.setText("구독과 좋아요! 앙 배불띠~");
		
		if (vo.getContent().contains("링크리스트")) 
		{
			StringBuilder ssb = new StringBuilder();
			
			vo.getContent().trim();
			String[] reqContent = null;
			List<String> list = new ArrayList<String>();
			int listCnt = 0;
			
			reqContent = vo.getContent().split(">");
			
			list = Lists.newArrayList(Splitter.on("|").trimResults().omitEmptyStrings().splitToList(reqContent[1]));
			
			for(String link : list)
			{
				String[] content = link.split("\\^");
				ssb.append(link + ": " + content[0] + " ^ " + content[1]);
				
				Connection conn = null;
		        Statement st = null;
		        
		        try {
		            conn = dataSource.getConnection();
		            st = conn.createStatement();
		            
		            StringBuilder sb = new StringBuilder();
		            String sql = sb.append("insert into LINK_REPOSITORY values(")
		            		.append(0 + ",")
		                    .append("'" + content[0] + "',")
		                    .append("'" + content[1] + "'")
		                    .append(");")
		                    .toString();
		            
		            st.executeUpdate(sql);
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if(st != null) st.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		            try {
		                if(conn != null) conn.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }                        
		        }
		        ++listCnt;
			}
			
			mes_vo.setText(listCnt + "건 DB Insert Success");
		} else if (vo.getContent().equals("connecting?"))
		{
			try {
				Thread.sleep(5*1000);
				mes_vo.setText("connecting!!");
				
			} catch (InterruptedException e) {
				mes_vo.setText("error... retry please");
				e.printStackTrace();
			}
		} 
		
		returnMessage.setMessage(mes_vo);
		return returnMessage;
	}
	
}
