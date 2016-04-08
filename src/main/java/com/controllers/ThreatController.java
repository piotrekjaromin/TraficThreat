package com.controllers;


import com.models.*;
import javafx.application.Application;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        coordinates1.setCity(location.split(";")[1]);
        coordinates1.setStreet(location.split(";")[0]);
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

    @RequestMapping(value = "/addImage", method = RequestMethod.GET)
    public String goAddImage(ModelMap model) {
        return "addImage";
    }

    @RequestMapping(value = {"/addImage"}, method = RequestMethod.POST)
    @ResponseBody
    public String addImage(HttpServletRequest request , @RequestParam("file") MultipartFile file) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String threatUuid = request.getParameter("uuid");


        if (!file.isEmpty()) {
            try {

                Threat threat = threatDAO.get(threatUuid);


                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File("tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + threatUuid);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);

                threat.setPathToPhoto(dir.getAbsolutePath()
                        + File.separator + threatUuid);
                threatDAO.update(threat);

                return "You successfully uploaded file";
            }
            catch (Exception e) {
                return "Error";
            }
        }
        else {
            return "file was empty";
        }
    }

    @RequestMapping(value = "/showImage", method = RequestMethod.GET)
    public String goShowdImage(ModelMap model) {
        return "showImage";
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

    @RequestMapping(value = "/showLogs", method = RequestMethod.GET)
    public String showLogs(ModelMap model) {
        try {
            model.addAttribute("logs", String.join("<br/>",Files.readAllLines(Paths.get("/home/piotrek/Applications/wildfly-10.0.0.Final/standalone/log/server.log"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "showLogs";
    }

    @RequestMapping(value = "/showUsers", method = RequestMethod.GET)
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userModelDAO.getAll());
        return "showUsers";
    }


    @RequestMapping(value = "/showImage", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String showImage(HttpServletRequest request) {
        String threatUuid = request.getParameter("uuid");
        Threat threat = threatDAO.get(threatUuid);
        if(threat!=null) {
            InputStream in;
            if (threat.getPathToPhoto() != null) {
                try {
                    final HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_PNG);
                    in = new BufferedInputStream(new FileInputStream(threat.getPathToPhoto()));
                    return new String(Base64.encodeBase64(IOUtils.toByteArray(in)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "Failure";
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String approve(HttpServletRequest request) {
        String threatUuid = request.getParameter("uuid");
        Threat threat = threatDAO.get(threatUuid);
        if(threat!=null) {
            if(threat.getIsApproved()) {
                return "Already approved";
            } else {
                threat.setIsApproved(true);
                threatDAO.update(threat);
                return "Success";
            }

        }
        return "bad uuid";
    }

    @RequestMapping(value = "/disapprove", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String disapprove(HttpServletRequest request) {
        String threatUuid = request.getParameter("uuid");
        Threat threat = threatDAO.get(threatUuid);
        if(threat!=null) {
            if(threat.getIsApproved()) {
                threat.setIsApproved(false);
                threatDAO.update(threat);
                return "Success";
            } else {
                return "Already disapproved";
            }

        }
        return "bad uuid";
    }

    @RequestMapping(value = "/deleteThreat", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String threatUuid = request.getParameter("uuid");
        Threat threat = threatDAO.get(threatUuid);

        if(threat==null) {
            return "Failure: bad uuid";
        }

        if(!userModelDAO.getByLogin(userDetails.getUsername()).getUserRole().getType().equals("lADMIN"))
        return "Failure: no permission";

        Threat threat1 = threatDAO.get(threatUuid);

        for(UserModel user : userModelDAO.getAll()){
            if(user.getThreats().contains(threat)){
                user.deleteThreat(threat);
                userModelDAO.update(user);
                break;
            }

        }
        threat.deleteAllConection();
        threatDAO.update(threat);

        if(!threat1.getPathToPhoto().equals("")) {
            File file = new File(threat1.getPathToPhoto());
            file.delete();
        }

        if(threat1.getType()!=null)
            threatTypeDAO.delete(threat1.getType());

        if(threat1.getCoordinates()!=null)
            coordinatesDAO.delete(threat1.getCoordinates());

        for(Vote vote : threat1.getVotes())
            voteDAO.delete(vote);

        threatDAO.delete(threat);

        return "success";
    }

    @RequestMapping(value = "/getThreat", method = RequestMethod.GET)
    public String showUsers(ModelMap model, HttpServletRequest request) {
        String threatUuid = request.getParameter("uuid");
        model.addAttribute("threat", threatDAO.get(threatUuid));
        return "editThreat";
    }

    @RequestMapping(value = "/editThreat", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String editThreat(HttpServletRequest request) {
        String threatUuid = request.getParameter("uuid");
        String typeOfThreat = request.getParameter("typeOfThreat");
        String location = request.getParameter("location");
        String description = request.getParameter("description");

        Threat threat = threatDAO.get(threatUuid);
        if(threat!=null) {
            ThreatType threatType = threat.getType();
            threatType.setThreatType(typeOfThreat);
            threatTypeDAO.update(threatType);
            threat.setType(threatType);
            Coordinates coordinates = threat.getCoordinates();
            coordinates.setStreet(location.split(";")[0]);
            coordinates.setCity(location.split(";")[1]);
            coordinatesDAO.update(coordinates);
            threat.setCoordinates(coordinates);
            threat.setDescription(description);
            threatDAO.update(threat);
            return "Success";
        }
        return "Failure: bad uuid";
    }


}