package com.aorise.controller.ueditor;

import com.aorise.ueditor.ActionEnter;
import org.json.JSONException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 用于处理关于ueditor插件相关的请求
 * @author Guoqing
 * @date 2017-11-29
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/ue")
public class UeditorController {

	@RequestMapping(value = "/upload")
	@ResponseBody
	public String exec(HttpServletRequest request) throws UnsupportedEncodingException{ 
		request.setCharacterEncoding("utf-8");
		String rootPath = request.getRealPath("/");
		try {
			return new ActionEnter( request, rootPath ).exec();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
