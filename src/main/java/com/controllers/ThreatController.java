package com.controllers;


import com.models.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Controller
public class ThreatController extends BaseController {

    @RequestMapping(value = "/addThreat", method = RequestMethod.GET)
    public String goAddThreat(ModelMap model) {
        return "addThreat";
    }


    @RequestMapping(value = {"/addThreat"}, method = RequestMethod.POST)
    @ResponseBody
    public String addThreat(HttpServletRequest request) {
        String typeOfThreat = request.getParameter("typeOfThreat");
        String description= request.getParameter("description");
        String coordinates = request.getParameter("coordinates");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Coordinates coordinates1 = new Coordinates();
        coordinates1.setHorizontal(coordinates.split(";")[0]);
        coordinates1.setVertical(coordinates.split(";")[0]);
        coordinatesDAO.save(coordinates1);
        ThreatType threatType = new ThreatType();
        threatType.setThreatType(typeOfThreat);
        threatTypeDAO.save(threatType);
        Threat threat = new Threat();
        threat.setCoordinates(coordinates1);
        threat.setType(threatType);
        threat.setLogin(userDetails.getUsername());
        threat.setDescription(description);
        threat.setDate(new Date());
        threatDAO.save(threat);
        UserModel user = userModelDAO.getByLogin(userDetails.getUsername());
        user.addThread(threat);
        userModelDAO.update(user);

        return "Success";

    }

    @RequestMapping(value = "/addComment", method = RequestMethod.GET)
    public String goAddComment(ModelMap model) {
        return "addComment";
    }

    @RequestMapping(value = {"/addComment"}, method = RequestMethod.POST)
    @ResponseBody
    public String addComment(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String comment = request.getParameter("comment");
        String threatUuid = request.getParameter("uuid");
        ThreatComment threatComment = new ThreatComment();
        threatComment.setDate(new Date());
        threatComment.setComment(comment);
        threatComment.setLogin(userDetails.getUsername());
        threatCommentDAO.save(threatComment);
        Threat threat = threatDAO.get(threatUuid);
        threat.addComment(threatComment);
        threatDAO.update(threat);

        return "Success";
    }

    @RequestMapping(value = "/addVoteForThreat", method = RequestMethod.GET)
    public String goAddVote(ModelMap model) {
        return "addVote";
    }

    @RequestMapping(value = {"/addVoteForThreat"}, method = RequestMethod.POST)
    @ResponseBody
    public String addVote(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        short numberOfStars = Short.parseShort(request.getParameter("stars"));
        String threadUuid = request.getParameter("uuid");

        Vote vote = new Vote();
        vote.setLogin(userDetails.getUsername());
        vote.setNumberOfStars(numberOfStars);
        voteDAO.save(vote);

        Threat threat = threatDAO.get(threadUuid);
        threat.addVote(vote);
        threatDAO.update(threat);

        return "Success";
    }

    @RequestMapping(value = "/showThreats", method = RequestMethod.GET)
    public String showThreat(ModelMap model) {
        model.addAttribute("threats", threatDAO.getAll());
        return "showThreats";
    }

    @RequestMapping(value = "/showUsers", method = RequestMethod.GET)
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userModelDAO.getAll());
        return "showUsers";
    }

}