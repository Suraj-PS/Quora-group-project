package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
Service class that interact with DAO classes
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity addQuestion(QuestionEntity question, final String accessToken) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthToken = userDao.getAuthToken(accessToken);
        if(userAuthToken == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        if(userAuthToken.getLogutTime()!=null) {
            if(userAuthToken.getLoginTime().isBefore(userAuthToken.getLogutTime())) {
                throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to post a question");
            }
        }
        question.setUser(userAuthToken.getUser());

      return questionDao.createQuestion(question);
    }

    public List<QuestionEntity> getAllQuestions(final String accessToken) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthToken = userDao.getAuthToken(accessToken);
        if(userAuthToken == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        if(userAuthToken.getLogutTime()!=null) {
            if(userAuthToken.getLoginTime().isBefore(userAuthToken.getLogutTime())) {
                throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to post a question");
            }
        }

        return questionDao.getAllQuestions();
    }

}