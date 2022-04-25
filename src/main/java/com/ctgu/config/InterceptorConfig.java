package com.ctgu.config;

import com.ctgu.common.Const;
import com.ctgu.model.Account;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: InterceptorConfig
 * Description:
 * date: 2019/12/18 16:27
 *
 * @author crwen
 * @create 2019-12-18-16:27
 * @since JDK 1.8
 */
public class InterceptorConfig implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
		if (currentAccount == null) {
			//System.out.println("=============================================================");
			//System.out.println(request.getRequestURI());
			//System.out.println("=============================================================");
			if (request.getRequestURI().contains("/manage")) {
				response.sendRedirect("/manage/login");
			} else {
				response.sendRedirect("/");
			}

			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
