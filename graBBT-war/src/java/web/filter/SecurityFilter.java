/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.filter;

import entity.RetailerEntity;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})

public class SecurityFilter implements Filter {

    FilterConfig filterConfig;
    
    private static final String CONTEXT_ROOT = "/graBBT-war";
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
//    {
//        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
//        HttpSession httpSession = httpServletRequest.getSession(true);
//        String requestServletPath = httpServletRequest.getServletPath();        
//        
//        
//
//        if(httpSession.getAttribute("isLogin") == null)
//        {
//            httpSession.setAttribute("isLogin", false);
//        }
//
//        Boolean isLogin = (Boolean)httpSession.getAttribute("isLogin");
//    
//        if(!excludeLoginCheck(requestServletPath))
//        {
//            if(isLogin == true)
//            {
//               //Staff Entity does not exist yet
//               RetailerEntity currentRetailerEntity = (RetailerEntity)httpSession.getAttribute("currentRetailerEntity");
//               
//             if(checkAccessRight(requestServletPath))
//                {
//                    chain.doFilter(request, response);
//                }
//                else
//                {
//                    httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
//                }
//            }
//            else
//            {
//                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
//            }
//        }
//        else
//        {
//            chain.doFilter(request, response);
//        }
//    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();        
        
        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }
        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");
        
        if (!excludeLoginCheck(requestServletPath)) {
            if (isLogin == true) {
                RetailerEntity curr = (RetailerEntity) httpSession.getAttribute("currentRetailerEntity");
                
                if (curr.getUsername().equals("manager")) {
                    chain.doFilter(request, response);
                    
                } else {
                    httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
                }
            } else {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
            }
        } else {
            chain.doFilter(request, response);
        }
    }
    
    public void destroy() {
        
    }

//    //Check logged in user?
//    private Boolean checkAccessRight(String path)
//    {
//        return true;
//    }
//    
    private Boolean excludeLoginCheck(String path) {
        if (path.equals("/index.xhtml")
                || path.equals("/accessRightError.xhtml")
                || path.equals("/listing.xhtml")
                || path.startsWith("/javax.faces.resource")) {
            return true;
        } else {
            return false;
        }
    }
}
