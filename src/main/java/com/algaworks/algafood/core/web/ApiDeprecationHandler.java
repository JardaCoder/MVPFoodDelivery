//package com.algaworks.algafood.core.web;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//@Component
//public class ApiDeprecationHandler extends HandlerInterceptorAdapter {
//
//
//	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		
//		if(request.getRequestURI().startsWith("/v1/")) {
//			response.addHeader("X-JardaFood-Deprecated", "Essa versão da Api está depreciada e deixara de existir a partir de 01/01/2023."
//					+ " Use a versão mais recente");
//		}
//		
//		return true;
//	}
//}
