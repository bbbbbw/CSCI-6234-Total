package project.main.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.main.model.Group;
import project.main.model.User;
import project.main.service.GroupMemberService;
import project.main.service.GroupService;
import project.main.service.UserService;
import project.main.tool.Utility;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {
    private final int COOKIE_EXPIRES = 60;
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    GroupMemberService groupMemberService;

    @RequestMapping(value = "check-code", method = RequestMethod.GET)
    public String checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Pair<BufferedImage, String> checkCodeAndVal = Utility.createCheckCode();
        request.getSession().setAttribute("checkCode", checkCodeAndVal.getValue());
        response.setContentType("image/jpeg");
        response.setDateHeader("expires", -1);
        response.setHeader("Cache-Control", "no-cache");
        ImageIO.write(checkCodeAndVal.getKey(), "jpg", response.getOutputStream());
        return null;
    }

    @RequestMapping(value = "user-register", method = RequestMethod.POST)
    public String userRegister(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        JSONObject result = new JSONObject();
        if (!Utility.validateCheckCode(request)) {
            result.put("status", "fail");
            result.put("msg", "Incorrect check code!");
            return result.toString();
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("tel");
        if (userService.getUser(username) != null) {
            result.put("status", "fail");
            result.put("msg", "Username already exists!");
            return result.toString();
        } else {
            userService.createUser(username, password, phone);
            result.put("status", "success");
            result.put("msg", "You have successfully created user " + username + "!");
        }
        return result.toString();
    }

    @RequestMapping(value = "user-login", method = RequestMethod.POST)
    public String userLogin(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException, ServletException {
        JSONObject result = new JSONObject();
        if (!Utility.validateCheckCode(request)) {
            result.put("status", "fail");
            result.put("msg", "Incorrect check code!");
            return result.toString();
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.login(username, password);
        if (user == null) {
            result.put("status", "fail");
            result.put("msg", "Incorrect username or password!");
        } else {
            result.put("status", "success");
            Utility.writeCookie(response, "userId", Integer.toString(user.getId()), COOKIE_EXPIRES);
            Utility.writeCookie(response, "token", user.getToken(), COOKIE_EXPIRES);
        }
        return result.toString();
    }

    @RequestMapping(value = "create-group", method = RequestMethod.POST)
    public String createGroup(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        JSONObject result = new JSONObject();
        int moderatorId = Integer.parseInt(Utility.readCookie(request, "userId"));
        String name = request.getParameter("name");
        Group newGroup = groupService.createGroup(name, moderatorId);
        if (newGroup == null) {
            result.put("status", "fail");
            result.put("msg", "Fail in creating group " + name + "!");
        } else {
            groupMemberService.addMember(newGroup.getId(), moderatorId, 0);
            result.put("status", "success");
            result.put("msg", "Create group " + name + " successful!");
        }
        return result.toString();
    }

    @RequestMapping(value = "join-group", method = RequestMethod.POST)
    public String joinGroup(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        String inviteCode = request.getParameter("invite_code");
        Group group = groupService.getGroupFromInviteCode(inviteCode);
        if (group == null) {
            result.put("status", "fail");
            result.put("msg", "Invalid invite code!");
        } else {
            int userId = Integer.parseInt(Utility.readCookie(request, "userId"));
            if (groupMemberService.memberInGroup(userId, group.getId())) {
                result.put("status", "fail");
                result.put("msg", "You are already in the group!");
            } else {
                groupMemberService.addMember(group.getId(), userId, 1);
                result.put("status", "success");
                result.put("msg", "Join group " + group.getName() + " successful!");
            }
        }
        return result.toString();
    }
}
