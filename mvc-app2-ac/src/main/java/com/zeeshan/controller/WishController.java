package com.zeeshan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.zeeshan.service.WishService;

public class WishController extends AbstractController {

	private WishService service;

	public void setService(WishService service) {
		this.service = service;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String msg = service.generateWishMessage();
		return new ModelAndView("result", "wmsg", msg);
	}

}
