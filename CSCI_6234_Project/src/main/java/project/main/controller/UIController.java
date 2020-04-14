package project.main.controller;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.main.model.Movie;
import project.main.service.MovieService;
import project.main.tool.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UIController {
    @Autowired
    MovieService movieService;

    @RequestMapping(value = "/not-found", method = RequestMethod.GET)
    public String notFound() {
        return "not-found";
    }

    @RequestMapping(value = "/no-auth", method = RequestMethod.GET)
    public String noAuth() {
        return "no-auth";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = Utility.readCookie(request, "userId");
        if (userId != null && !userId.equals("")) {
            response.sendRedirect("index");
        }
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "register";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Utility.writeCookie(response, "userId", "", 0);
        Utility.writeCookie(response, "token", "", 0);
        response.sendRedirect("login");
        return "login";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(HttpServletRequest request) throws IOException {
        String movieId = request.getParameter("movie_id");
        Movie movie = movieService.getMovieById(Integer.parseInt(movieId));
        JSONObject result = new JSONObject();
        result.put("id", movie.getId());
        result.put("apiId", movie.getApiId());
        request.setAttribute("movie", result.toString());
        return "detail";
    }

    @RequestMapping(value = "/manage-group", method = RequestMethod.GET)
    public String manageGroup() {
        return "manage-group";
    }

    @RequestMapping(value = "/search-movie", method = RequestMethod.GET)
    public String searchMovie() {
        return "search-movie";
    }

    @RequestMapping(value = "/preview-movie", method = RequestMethod.GET)
    public String previewMovie() {
        return "preview-movie";
    }

    @RequestMapping(value = "/watching-event", method = RequestMethod.GET)
    public String watchingEvent() {
        return "watching-event";
    }


    @RequestMapping(value = "/voting-event", method = RequestMethod.GET)
    public String votingEvent() {
        return "voting-event";
    }
}
