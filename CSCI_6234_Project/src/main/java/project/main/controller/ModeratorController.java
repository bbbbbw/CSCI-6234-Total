package project.main.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.main.model.Group;
import project.main.model.Movie;
import project.main.model.VotingEvent;
import project.main.model.WatchingEvent;
import project.main.service.*;
import project.main.tool.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

@RestController
public class ModeratorController {
    @Autowired
    GroupService groupService;
    @Autowired
    GroupMemberService groupMemberService;
    @Autowired
    MovieService movieService;
    @Autowired
    GroupMovieService groupMovieService;
    @Autowired
    WatchingEventService watchingEventService;
    @Autowired
    VotingEventService votingEventService;
    @Autowired
    VotingMovieService votingMovieService;


    @RequestMapping(value = "add-movie", method = RequestMethod.POST)
    public String addToGroupMovieList(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!moderatorOfGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user is not the group moderator.");
        } else {
            String apiId = request.getParameter("apiId");
            String movieName = request.getParameter("movieName");
            Movie movie = movieService.addMovie(apiId, movieName);
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            int movieId = movie.getId();
            if (groupMovieService.existGroupMovie(groupId, movieId)) {
                result.put("status", "exist");
                result.put("msg", "This movie has already exist in this group!");
            } else {
                groupMovieService.populateMovie(groupId, movieId);
                result.put("status", "success");
                result.put("msg", "You have successfully added this movie to the group movie list!");
            }
        }
        return result.toString();
    }

    @RequestMapping(value = "remove-movie", method = RequestMethod.POST)
    public String removeFromGroupMovieList(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!moderatorOfGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user is not the group moderator.");
        } else {
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            if (groupMovieService.existGroupMovie(groupId, movieId)) {
                groupMovieService.removeMovie(groupId, movieId);
                result.put("status", "success");
                result.put("msg", "You have successfully removed this movie from the group movie list!");
            } else {
                result.put("status", "not-exist");
                result.put("msg", "This movie does not exist in this group!");
            }
        }
        return result.toString();
    }

    @RequestMapping(value = "create-watching-event", method = RequestMethod.POST)
    public String createWatchingEvent(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
        JSONObject result = new JSONObject();
        if (!moderatorOfGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user is not the group moderator.");
        } else {
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            String watchDate = request.getParameter("watchDate");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String message = request.getParameter("msg");
            Pair<String, String> res = watchingEventService.createWatchingEvent(groupId, watchDate, startTime, endTime, message);
            result.put("status", res.getKey());
            result.put("msg", res.getValue());
        }
        return result.toString();
    }

    @RequestMapping(value = "create-voting-event", method = RequestMethod.POST)
    public String createVotingEvent(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
        JSONObject result = new JSONObject();
        if (!moderatorOfGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user is not the group moderator.");
        } else {
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            List<WatchingEvent> groupWatchingEvent = watchingEventService.getWatchingEvent(groupId);
            WatchingEvent watchingEvent = groupWatchingEvent.get(groupWatchingEvent.size() - 1);
            votingEventService.createVotingEvent(watchingEvent);
            result.put("status", "success");
            result.put("msg", "You have successfully created a voting event!");
        }
        return result.toString();
    }

    @RequestMapping(value = "update-voting-event", method = RequestMethod.POST)
    public String updateVotingEvent(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
        JSONObject result = new JSONObject();
        if (!moderatorOfGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user is not the group moderator.");
        } else {
            int watchingEventId = Integer.parseInt(request.getParameter("watchingEventId"));
            WatchingEvent we = watchingEventService.getWatchingEventById(watchingEventId);
            String startDate = request.getParameter("startDate");
            String startTime = request.getParameter("startTime");
            String endDate = request.getParameter("endDate");
            String endTime = request.getParameter("endTime");
            Pair<String, String> res = votingEventService.updateVotingEvent(we, startDate, startTime, endDate, endTime);
            result.put("status", res.getKey());
            result.put("msg", res.getValue());
        }
        return result.toString();
    }

    @RequestMapping(value = "add-vote-movie", method = RequestMethod.POST)
    public String addVoteMovie(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
        JSONObject result = new JSONObject();
        if (!moderatorOfGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user is not the group moderator.");
        } else {
            int watchingEventId = Integer.parseInt(request.getParameter("watchingEventId"));
            VotingEvent ve = votingEventService.getVotingEvent(watchingEventId);
            if (ve == null) {
                result.put("status", "fail");
                result.put("msg", "This watching event does not have corresponding voting event!");
            } else {
                int votingEventId = ve.getId();
                String movieName = request.getParameter("movieName");
                String apiId = request.getParameter("apiId");
                Movie movie = movieService.addMovie(apiId, movieName);
                if (votingMovieService.votingMovieExist(votingEventId, movie.getId())) {
                    result.put("status", "fail");
                    result.put("msg", "This movie is already in the voting movie list!");
                } else {
                    votingMovieService.addVotingMovie(votingEventId, movie.getId());
                    result.put("status", "success");
                    result.put("msg", "You successfully add the movie to the voting movie list!");
                }
            }
        }
        return result.toString();
    }

    @RequestMapping(value = "get-moderate-group", method = RequestMethod.GET)
    public String getModeratorGroup(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        int userId = Integer.parseInt(Utility.readCookie(request, "userId"));
        List<Group> groups = groupService.getModerateGroups(userId);
        result.put("status", "success");
        result.put("data", groups);
        return result.toString();
    }

    private boolean moderatorOfGroup(HttpServletRequest request) throws UnsupportedEncodingException {
        int userId = Integer.parseInt(Utility.readCookie(request, "userId"));
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        return groupMemberService.moderatorOfGroup(userId, groupId);
    }
}
