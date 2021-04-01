package com.sequoiacap.wsock.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WSockController
{
	@RequestMapping("/websock/asyncw.fn")
	public String asyncws(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession(true);
		
		model.addAttribute("sessionId", session.getId());

		return "asyncws";
	}
}
