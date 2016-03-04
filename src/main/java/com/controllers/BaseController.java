package com.controllers;

import com.dao.*;
import com.models.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseController {
@Autowired
    UserModelDAO userModelDAO;


}
