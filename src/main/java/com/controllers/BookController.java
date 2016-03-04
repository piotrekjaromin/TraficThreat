package com.controllers;


//import com.configuration.Mail;
import com.models.*;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;


@Controller
public class BookController extends BaseController {
//
//    @Autowired
//    Mail mail;

    @RequestMapping(value = {"/admin/addBook"}, method = RequestMethod.GET)
    public String addBook(Model model) {
        //model.addAttribute("sections", sectionDAO.getAll());
        //model.addAttribute("typesOfBooks", typeOfBookDAO.getAll());
        return "addBook";
    }

}