package project.main.tool;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.main.model.UrlFilter;
import project.main.service.UrlFilterService;
import project.main.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

/**
 * This is the url filter that blocks unauthorized visit and page not found
 */
@Component
public class AuthFilter implements Filter {
    @Autowired
    UrlFilterService urlFilterService;
    @Autowired
    UserService userService;

    private static final String BASE_URL = "http://localhost:8080/";
    private HashMap<String, int[]> urlMap; // url -> { type, auth }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<UrlFilter> urlFilterList = urlFilterService.getFilter();
        urlMap = new HashMap<String, int[]>();
        for (UrlFilter uf : urlFilterList) {
            urlMap.put(BASE_URL + uf.getUrl(), new int[]{uf.getType(), uf.getAuth()});
        }
        System.out.println("Initialize filter: " + filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURL().toString();

        if (!url.startsWith(BASE_URL + "static")) {
            System.out.println("Processing the request of " + url);
            int[] typeAndAuth = urlMap.get(url);
            if (typeAndAuth == null) {
                httpServletResponse.sendRedirect(BASE_URL + "not-found");
                return;
            } else {
                if (typeAndAuth[1] == 1) {
                    if (!validateRequest(httpServletRequest)) {
                        if (typeAndAuth[0] == 0) {
                            JSONObject json = new JSONObject();
                            json.put("status", "unauthorized");
                            json.put("msg", "Please login to use this interface");
                            PrintWriter out = response.getWriter();
                            out.write(json.toString());
                        } else if (typeAndAuth[0] == 1) {
                            httpServletResponse.sendRedirect(BASE_URL + "no-auth");
                            return;
                        }
                    } else {
                        String id = Utility.readCookie(httpServletRequest, "userId");
                        httpServletRequest.setAttribute("userId", id);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("Destroy filter");
    }

    private boolean validateRequest(HttpServletRequest request) throws UnsupportedEncodingException {
        String id = Utility.readCookie(request, "userId");
        String token = Utility.readCookie(request, "token");
        if (id != null && token != null && !id.equals("") && !token.equals("")) {
            return userService.validateUser(Integer.parseInt(id), token);
        } else {
            return false;
        }
    }
}
