package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.AdminService;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(path = "/admin/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDeleteResponse> userDelete(@RequestHeader("authorization") final String accessToken,
                                                         @PathVariable("userId") final String userId) throws
            AuthorizationFailedException, UserNotFoundException {

        UserAuthTokenEntity userAuthToken = adminService.signInValidationAdmin(accessToken);
        String deletedUserUUID = adminService.deleteUserAsAdmin(userId);
        UserDeleteResponse userDeleteResponse = new UserDeleteResponse().id(deletedUserUUID).status("USER SUCCESSFULLY DELETED");
        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.OK);
    }

}