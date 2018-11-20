package com.bykwon.vo.kakao;

import java.util.Arrays;

public class KeyboardVO
{
	private String type;
	private String[] buttons;
	private String text;
	
//	public KeyboardVO(String[] buttons) {
//		this.type = "buttons";
//		this.buttons = buttons;
//	}
	
	public KeyboardVO(String text) {
		this.type = "text";
		this.text = text;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String[] getButtons() {
		return buttons;
	}

	public void setButtons(String[] buttons) {
		this.buttons = buttons;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
//		return "KeyboardVO [type=" + type + ", buttons=" + Arrays.toString(buttons) + "]";
		return "KeyboardVO [type=" + type + ", text=" + this.text.toString() + "]";
	}

}
