package project.main.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.main.model.*;
import project.main.service.*;
import project.main.tool.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class MemberController {
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    MovieService movieService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    GroupMemberService groupMemberService;
    @Autowired
    WatchingEventService watchingEventService;
    @Autowired
    VotingEventService votingEventService;
    @Autowired
    MemberVoteService memberVoteService;

    @RequestMapping(value = "unsubscribe", method = RequestMethod.POST)
    public String unsubscribe(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!belongToGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user does not belong to the group.");
        } else {
            int userId = Integer.parseInt(Utility.readCookie(request, "userId"));
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            Group group = groupService.getGroup(groupId);
            if (group.getModeratorId() == userId) {
                result.put("status", "failed");
                result.put("msg", "You are the moderator! You cannot unsubscribe.");
            } else {
                groupMemberService.removeMember(groupId, userId);
                result.put("status", "success");
                result.put("msg", "You have successfully unsubscribed");
            }
        }
        return result.toString();
    }

    @RequestMapping(value = "cast-vote", method = RequestMethod.POST)
    public String castVote(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
        JSONObject result = new JSONObject();
        if (!belongToGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user does not belong to the group.");
        } else {
            int watchingEventId = Integer.parseInt(request.getParameter("watchingEventId"));
            VotingEvent ve = votingEventService.getVotingEvent(watchingEventId);
            SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date now = new Date();
            if (ve == null) {
                result.put("status", "fail");
                result.put("msg", "This watching event does not have corresponding voting event!");
            } else if (now.after(sft.parse(ve.getEndAt()))) {
                result.put("status", "fail");
                result.put("msg", "This voting event is over!");
            } else {
                int userId = Integer.parseInt(Utility.readCookie(request, "userId"));
                int movieId = Integer.parseInt(request.getParameter("movieId"));
                int forOrAgainst = Integer.parseInt(request.getParameter("forOrAgainst"));
                if (memberVoteService.voteExist(ve.getId(), userId, movieId)) {
                    memberVoteService.updateVote(ve.getId(), userId, movieId, forOrAgainst);
                } else {
                    memberVoteService.createVote(ve.getId(), userId, movieId, forOrAgainst);
                }

                int max = -999, maxMovieId = -1;
                List<VotesCount> votesCountList = memberVoteService.getVotes(ve.getId());
                for (VotesCount vc: votesCountList) {
                    if (vc.getVoteScore() > max) {
                        max = vc.getVoteScore();
                        maxMovieId = vc.getMovieId();
                    }
                }
                votingEventService.updateWinner(ve.getId(), maxMovieId);

                result.put("status", "success");
                result.put("msg", "You have updated your vote!");
            }
        }
        return result.toString();
    }

    @RequestMapping(value = "get-group", method = RequestMethod.GET)
    public String getGroups(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        int userId = Integer.parseInt(Utility.readCookie(request, "userId"));
        List<Group> groups = groupService.getGroups(userId);
        result.put("status", "success");
        result.put("data", groups);
        return result.toString();
    }

    @RequestMapping(value = "get-group-member", method = RequestMethod.GET)
    public String getGroupMember(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        List<User> users = userService.getGroupMember(groupId);
        result.put("status", "success");
        result.put("data", users);
        return result.toString();
    }

    @RequestMapping(value = "get-group-movie", method = RequestMethod.GET)
    public String getGroupMovies(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!belongToGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user does not belong to the group.");
        } else {
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            List<Movie> movies = movieService.getGroupMovie(groupId);
            result.put("status", "success");
            result.put("data", movies);
        }
        return result.toString();
    }

    @RequestMapping(value = "get-local-review", method = RequestMethod.GET)
    public String getLocalReview(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!belongToGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user does not belong to the group.");
        } else {
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            List<Review> review = reviewService.getReview(movieId, groupId);
            result.put("status", "success");
            result.put("data", review);
        }
        return result.toString();
    }

    @RequestMapping(value = "add-review", method = RequestMethod.POST)
    public String addReview(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!belongToGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user does not belong to the group.");
        } else {
            int userId = Integer.parseInt(Utility.readCookie(request, "userId"));
            String userName = userService.getUser(userId).getName();
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            int movieId = Integer.parseInt(request.getParameter("movie_id"));
            String review = request.getParameter("content");
            reviewService.writeReview(userId, userName, groupId, movieId, review);
            result.put("status", "success");
            result.put("msg", "You have successfully created a review! (The review could only been viewed by member in your group)");
        }
        return result.toString();
    }

    @RequestMapping(value = "get-watching-event", method = RequestMethod.GET)
    public String getWatchingEvent(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!belongToGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user does not belong to the group.");
        } else {
            int groupId = Integer.parseInt(request.getParameter("groupId"));
            List<WatchingEvent> events = watchingEventService.getWatchingEvent(groupId);
            result.put("status", "success");
            result.put("data", events);
        }
        return result.toString();
    }

    @RequestMapping(value = "get-vote-movie-list", method = RequestMethod.GET)
    public String getVoteMovieList(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!belongToGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user does not belong to the group.");
        } else {
            int watchingEventId = Integer.parseInt(request.getParameter("watchingEventId"));
            VotingEvent ve = votingEventService.getVotingEvent(watchingEventId);
            if (ve == null) {
                result.put("status", "fail");
                result.put("msg", "This watching event does not have corresponding voting event!");
            } else {
                int votingEventId = ve.getId();
                List<Movie> movies = movieService.getVoteMovieList(votingEventId);
                result.put("status", "success");
                result.put("data", movies);
            }
        }
        return result.toString();
    }

    @RequestMapping(value = "get-voting-event", method = RequestMethod.GET)
    public String getVotingEvent(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        if (!belongToGroup(request)) {
            result.put("status", "unauthorized");
            result.put("msg", "Current user does not belong to the group.");
        } else {
            int watchingEventId = Integer.parseInt(request.getParameter("watchingEventId"));
            VotingEvent ve = votingEventService.getVotingEvent(watchingEventId);
            result.put("status", "success");
            result.put("data", ve);
        }
        return result.toString();
    }

    private boolean belongToGroup(HttpServletRequest request) throws UnsupportedEncodingException {
        int userId = Integer.parseInt(Utility.readCookie(request, "userId"));
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        return groupMemberService.memberInGroup(userId, groupId);
    }
}
