package com.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
public class RestfulBookController extends BaseController {


    @RequestMapping(value = "/rest/getBooks/", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listAllbooks() {

        return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.OK);
    }


}