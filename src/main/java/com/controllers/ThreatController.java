package com.controllers;


import com.models.*;
import javafx.application.Application;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
        String location = request.getParameter("location");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Coordinates coordinates1 = new Coordinates();
        coordinates1.setHorizontal(coordinates.split(";")[0]);
        coordinates1.setVertical(coordinates.split(";")[1]);
        coordinates1.setCity(location.split(";")[0]);
        coordinates1.setCountry(location.split(";")[1]);
        coordinates1.setStreet(location.split(";")[2]);
        coordinates1.setStreetNumber(location.split(";")[3]);
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

    @RequestMapping(value = {"/addImage"}, method = RequestMethod.POST)
    @ResponseBody
    public String addImage(HttpServletRequest request , @RequestParam("file") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String threadUuid = request.getParameter("uuid");

        if (!file.isEmpty()) {
            try {
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("image" + threadUuid)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
            }
            catch (Exception e) {
                return "Error";
            }
        }
        else {
            return "file was empty";
        }

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
        String comment= request.getParameter("comment");
        String threadUuid = request.getParameter("uuid");

        Vote vote = new Vote();
        vote.setLogin(userDetails.getUsername());
        vote.setNumberOfStars(numberOfStars);
        vote.setDate(new Date());
        vote.setComment(comment);
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