package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
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

    @Autowired
    private AuthenticationService authenticationService;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity addQuestion(QuestionEntity question, final String accessToken) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthToken = authenticationService.signInValidation(accessToken);
        question.setUser(userAuthToken.getUser());

      return questionDao.createQuestion(question);
    }

    public List<QuestionEntity> getAllQuestions(final String accessToken) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthToken = authenticationService.signInValidation(accessToken);
        return questionDao.getAllQuestions();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity editQuestionContent(final String uuid, final String accessToken, final  String content) throws AuthorizationFailedException, InvalidQuestionException {
        UserAuthTokenEntity userAuthToken = authenticationService.signInValidation(accessToken);
        QuestionEntity existingQuestion = questionDao.getQuestionByUuid(uuid);
        if(existingQuestion == null) {
            throw new InvalidQuestionException("QUES-001","Entered question uuid does not exist");
        }
        if(existingQuestion.getUser().getId() != userAuthToken.getUser().getId()) {
            throw new AuthorizationFailedException("ATHR-003", "Only the question owner can edit the question");
        }
        existingQuestion.setContent(content);
        return questionDao.editQuestionContent(existingQuestion);
    }


}
