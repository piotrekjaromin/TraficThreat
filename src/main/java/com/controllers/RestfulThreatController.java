package com.controllers;


import com.dao.CoordinatesDAO;
import com.dao.ThreatCommentDAO;
import com.dao.ThreatTypeDAO;
import com.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class RestfulThreatController extends BaseController {


    /**
     * Dodawanie zagrozenia. Wszyscy zalogowani
     * @return status
     */

    @RequestMapping(value = "/rest/addThreat/", method = RequestMethod.POST)
    public ResponseEntity<String> addThreat(HttpServletRequest request) {
        String typeOfThreat = request.getParameter("typeOfThreat");
        String description= request.getParameter("description");
        String coordinates = request.getParameter("coordinates");
        String token = request.getParameter("token");
        Session session = sessionManager.getAndUpdateSession(token);

        if (session == null)
            return new ResponseEntity<String>("{\"Status\" : \"Failure bad token\"}",HttpStatus.UNAUTHORIZED);

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
        threat.setLogin(session.getLogin());
        threat.setDescription(description);
        threat.setDate(new Date());
        threat.setIsApproved(false);
        threatDAO.save(threat);
        UserModel user = userModelDAO.getByLogin(session.getLogin());
        user.addThread(threat);
        userModelDAO.update(user);

        return new ResponseEntity<String>("{\"Status\" : \"Success\"}",HttpStatus.OK);
    }

    /**
     * Akceptacja zagrozenia. Tylko admin
     * @return status
     */

    @RequestMapping(value = "/rest/approveThreat/", method = RequestMethod.POST)
    public ResponseEntity<String> approveThreat(HttpServletRequest request) {
        String threatUuid = request.getParameter("threadUuid");
        String token = request.getParameter("token");
        Session session = sessionManager.getAndUpdateSession(token);

        if (session == null)
            return new ResponseEntity<String>("{\"Status\" : \"Failure bad token\"}",HttpStatus.UNAUTHORIZED);

        if(!userModelDAO.getByLogin(session.getLogin()).getUserRole().getType().equals("ADMIN"))
            return new ResponseEntity<String>("{\"Status\" : \"Failure no permission\"}",HttpStatus.FORBIDDEN);

        Threat threat = threatDAO.get(threatUuid);
        threat.setIsApproved(true);
        threatDAO.update(threat);

        return new ResponseEntity<String>("{\"Status\" : \"Success\"}",HttpStatus.OK);
    }


    /**
     * Dodawanie komentarza. tylko zalogowani uzytkownicy
     * @return status
     */

    @RequestMapping(value = "/rest/addComment/", method = RequestMethod.POST)
    public ResponseEntity<String> addComment(HttpServletRequest request) {
        String comment = request.getParameter("comment");
        String threatUuid = request.getParameter("uuid");
        String token = request.getParameter("token");
        Session session = sessionManager.getAndUpdateSession(token);

        if (session == null)
            return new ResponseEntity<String>("{\"Status\" : \"Failure bad token\"}",HttpStatus.UNAUTHORIZED);

        ThreatComment threatComment = new ThreatComment();
        threatComment.setDate(new Date());
        threatComment.setComment(comment);
        threatComment.setLogin(session.getLogin());
        threatCommentDAO.save(threatComment);
        Threat threat = threatDAO.get(threatUuid);
        threat.addComment(threatComment);
        threatDAO.update(threat);

        return new ResponseEntity<String>("{\"Status\" : \"Success\"}",HttpStatus.OK);
    }

    /**
     * Dodawanie glosu (glosowanie). tylko zalogowani uzytkownicy
     * @return status
     */

    @RequestMapping(value = "/rest/addVote/", method = RequestMethod.POST)
    public ResponseEntity<String> addVote(HttpServletRequest request) {
        short numberOfStars = Short.parseShort(request.getParameter("stars"));
        String threadUuid = request.getParameter("uuid");
        String token = request.getParameter("token");
        Session session = sessionManager.getAndUpdateSession(token);
        Threat threat = threatDAO.get(threadUuid);

        if (session == null)
            return new ResponseEntity<String>("{\"Status\" : \"Failure bad token\"}",HttpStatus.UNAUTHORIZED);

        if (threat == null)
            return new ResponseEntity<String>("{\"Status\" : \"Failure bad threat uuid\"}",HttpStatus.UNAUTHORIZED);

        Vote vote = new Vote();
        vote.setLogin(session.getLogin());
        vote.setNumberOfStars(numberOfStars);
        voteDAO.save(vote);

        threat.addVote(vote);
        threatDAO.update(threat);

        return new ResponseEntity<String>("{\"Status\" : \"Success\"}",HttpStatus.OK);
    }


    /**
     * Pobieranie wszystkich zagrozen. Dostep wszyscy
     * @return lista zagrozen
     */

    @RequestMapping(value = "/rest/getThreats/", method = RequestMethod.GET)
    public ResponseEntity<List<Threat>> listAllThreat() {
        List<Threat> threats = threatDAO.getAll();
        if (threats.isEmpty()) {
            return new ResponseEntity<List<Threat>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Threat>>(threats, HttpStatus.OK);
    }

    /**
     * Pobieranie zatwierdzonych zagrozen. Dostep wszyscy
     * @return lista zagrozen
     */

    @RequestMapping(value = "/rest/getApprovedThreats/", method = RequestMethod.GET)
    public ResponseEntity<List<Threat>> listApprovedThreat() {
        List<Threat> threats = threatDAO.getAllApproved();
        if (threats.isEmpty()) {
            return new ResponseEntity<List<Threat>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Threat>>(threats, HttpStatus.OK);
    }

    /**
     * Pobieranie niezatwierdzonych zagrozen. Dostep wszyscy
     * @return lista zagrozen
     */

    @RequestMapping(value = "/rest/getNotApprovedThreats/", method = RequestMethod.GET)
    public ResponseEntity<List<Threat>> listNotApprovedThreat() {
        List<Threat> threats = threatDAO.getAllNotApproved();
        if (threats.isEmpty()) {
            return new ResponseEntity<List<Threat>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Threat>>(threats, HttpStatus.OK);
    }

    /**
     * Zwraca zagrozenie o podanych uuid. Dostep wszyscy.
     * @param request -"uuid"
     * @return zagrozenie o podanym id
     */

    @RequestMapping(value = "/rest/getThreat/", method = RequestMethod.GET)
    public ResponseEntity<Threat> getBook(HttpServletRequest request) {
        String uuid = request.getParameter("uuid");

        Threat threat = threatDAO.get(uuid);
        if (threat == null) {
            System.out.println("threat with uuid " + uuid + " not found");
            return new ResponseEntity<Threat>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Threat>(threat, HttpStatus.OK);
    }

    /**
     * Zwraca komentarz o podanych uuid. Dostep wszyscy.
     * @param request -"uuid"
     * @return komentarz o podanym id
     */

    @RequestMapping(value = "/rest/getThreatComment/", method = RequestMethod.GET)
    public ResponseEntity<ThreatComment> getComment(HttpServletRequest request) {
        String uuid = request.getParameter("uuid");

        ThreatComment comment = threatCommentDAO.get(uuid);
        if (comment == null) {
            System.out.println("comment with uuid " + uuid + " not found");
            return new ResponseEntity<ThreatComment>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ThreatComment>(comment, HttpStatus.OK);
    }



    //Nie dziala

    /**
     * Usuwa Zagrozenie o podanym uuid. Tylko admin
     * @param request -"uuid", "token"
     * @return status
     */

    @RequestMapping(value = "/rest/deleteThreat/", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteThreat(HttpServletRequest request) {

        String token = request.getParameter("token");
        String uuid= request.getParameter("uuid");

        Session session = sessionManager.getAndUpdateSession(token);

        if (session == null)
            return new ResponseEntity<String>("{\"Status\" : \"Failure bad token\"}",HttpStatus.UNAUTHORIZED);

        if(!userModelDAO.getByLogin(session.getLogin()).getUserRole().getType().equals("ADMIN"))
            return new ResponseEntity<String>("{\"Status\" : \"Failure no permission\"}",HttpStatus.FORBIDDEN);

        Threat threat = threatDAO.get(uuid);
        if (threat == null) {
            System.out.println("Unable to delete. threat with uuid " + uuid + " not found");
            return new ResponseEntity<String>("{\"Status\" : \"Failure threat not found\"}",HttpStatus.NOT_FOUND);
        }
        Threat threat1 = threatDAO.get(uuid);

        for(UserModel user : userModelDAO.getAll()){
            if(user.getThreats().contains(threat)){
                user.deleteThreat(threat);
                userModelDAO.update(user);
                break;
            }

        }

        threat.deleteAllConection();
        threatDAO.update(threat);

        for (ThreatComment comment : threat1.getThreatComments())
            threatCommentDAO.delete(comment);

        if(threat1.getType()!=null)
            threatTypeDAO.delete(threat1.getType());

        if(threat1.getCoordinates()!=null)
        coordinatesDAO.delete(threat1.getCoordinates());

        for(Vote vote : threat1.getVotes())
            voteDAO.delete(vote);

        threatDAO.delete(threat);
        return new ResponseEntity<String>("{\"Status\" : \"Success\"}",HttpStatus.OK);
    }


    /**
     * Usuwa komentarz o podanym uuid. Tylko admin lub autor komentarza
     * @param request -"uuid", "token"
     * @return status
     */

    @RequestMapping(value = "/rest/deleteComment/", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteComment(HttpServletRequest request) {

        String token = request.getParameter("token");
        String uuid= request.getParameter("uuid");

        Session session = sessionManager.getAndUpdateSession(token);
        ThreatComment comment = threatCommentDAO.get(uuid);

        if (comment == null) {
            System.out.println("Unable to delete. threat with uuid " + uuid + " not found");
            return new ResponseEntity<String>("{\"Status\" : \"Failure threat not found\"}",HttpStatus.NOT_FOUND);
        }

        if (session == null)
            return new ResponseEntity<String>("{\"Status\" : \"Failure bad token\"}",HttpStatus.UNAUTHORIZED);

        if(!userModelDAO.getByLogin(session.getLogin()).getUserRole().getType().equals("ADMIN") || !session.getLogin().equals(comment.getLogin()))
            return new ResponseEntity<String>("{\"Status\" : \"Failure no permission\"}",HttpStatus.FORBIDDEN);

        for(Threat threat : threatDAO.getAll()) {
            if(threat.getThreatComments().contains(threatCommentDAO.get(uuid))){
                threat.deleteComment(threatCommentDAO.get(uuid));
                threatDAO.update(threat);
                break;
            }
        }
        threatCommentDAO.delete(threatCommentDAO.get(uuid));
        return new ResponseEntity<String>("{\"Status\" : \"Success\"}",HttpStatus.OK);
    }
}
