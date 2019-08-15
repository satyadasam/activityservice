package com.  .activity.cmepulse.controller;

import com.  .activity.controller.AbstractController;
import com.  .activity.exception.*;
import com.  .activity.model.AppResponse;
import com.  .activity.model.LegacyResponse;
import com.  .activity.model.UserActivity;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.util.ExceptionUtils;
import com.  .auth.unmarshal.UnmarshalException;
import com.  .profile_service.adapter.exception.ProfileServiceAdapterException;
import com.  .qnaservice.entity.QuestionForm;
import com.  .qnaservice.entity.Questionnaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by thatikonda on 12/14/16.
 */
@RestController
public class MobileController extends AbstractController {

    private static Logger log = LoggerFactory.getLogger(MobileController.class);

    /**
     * EndPoint for CMEPULSE APP , post form submit
     * 1)
     * @param userActivity
     * @param guid
     * @param request
     * @param userAgent
     * @return
     * @throws ProcessorException
     * @throws QNAException
     * @throws InvalidObjectException
     * @throws UnmarshalException
     * @throws ProfileServiceAdapterException
     * @throws UnAuthorizedUserExcepton, ProcessorException
     */
    @RequestMapping(
            value = {"/questionnaire/submitForm/POST"},
            method = {RequestMethod.POST},
            headers = {"Accept=application/json"}
    )
    @ResponseBody
    public LegacyResponse submitPostForm(@Valid @RequestBody UserActivity userActivity, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request,
                                         @NotNull @ModelAttribute("guid") String guid) throws ProfileServiceAdapterException, UnmarshalException, QNAException, ProcessorException,UnAuthorizedUserExcepton,ProfileServiceException,QNADataAccessException
    //	throws QNADataAccessException, QNAManagerException, QNARuleException
    {
        return processMobileRequest(userActivity,guid,userAgent,"post");
    }


    /**
     * EndPoint for CMEPULSE APP , internal form submit
     * 1)
     * @param userActivity
     * @param guid
     * @param request
     * @param userAgent
     * @return
     * @throws ProcessorException
     * @throws QNAException
     * @throws InvalidObjectException
     * @throws UnmarshalException
     * @throws ProfileServiceAdapterException
     * @throws UnAuthorizedUserExcepton, ProcessorException
     */
    @RequestMapping(
            value = {"/questionnaire/submitForm/INTERNAL"},
            method = {RequestMethod.POST},
            headers = {"Accept=application/json"}
    )
    @ResponseBody
    public LegacyResponse submitInternalForm(@Valid @RequestBody UserActivity userActivity, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request,
                                             @NotNull @ModelAttribute("guid") String guid) throws ProfileServiceAdapterException, UnmarshalException, QNAException, ProcessorException,UnAuthorizedUserExcepton,ProfileServiceException,QNADataAccessException
    //	throws QNADataAccessException, QNAManagerException, QNARuleException
    {
        return processMobileRequest(userActivity,guid,userAgent,"internal");
    }

    /**
     * EndPoint for CMEPULSE APP , pre form submit
     * 1)
     * @param userActivity
     * @param guid
     * @param request
     * @param userAgent
     * @return
     * @throws ProcessorException
     * @throws QNAException
     * @throws InvalidObjectException
     * @throws UnmarshalException
     * @throws ProfileServiceAdapterException
     * @throws UnAuthorizedUserExcepton, ProcessorException
     */
    @RequestMapping(
            value = {"/questionnaire/submitForm/PRE"},
            method = {RequestMethod.POST},
            headers = {"Accept=application/json"}
    )
    @ResponseBody
    public LegacyResponse submitPreForm(@Valid @RequestBody UserActivity userActivity, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request,
                                        @NotNull @ModelAttribute("guid") String guid) throws ProfileServiceAdapterException, UnmarshalException, QNAException, ProcessorException,UnAuthorizedUserExcepton,ProfileServiceException,QNADataAccessException{
        return processMobileRequest(userActivity, guid, userAgent, "pre");
    }

    /**
     * EndPoint for CMEPULSE APP , eval form submit
     * 1)
     * @param userActivity
     * @param guid
     * @param request
     * @param userAgent
     * @return
     * @throws ProcessorException
     * @throws QNAException
     * @throws InvalidObjectException
     * @throws UnmarshalException
     * @throws ProfileServiceAdapterException
     * @throws UnAuthorizedUserExcepton, ProcessorException
     */
    @RequestMapping(
            value = {"/questionnaire/submitForm/EVAL"},
            method = {RequestMethod.POST},
            headers = {"Accept=application/json"}
    )
    @ResponseBody
    public LegacyResponse submitEvalForm(@Valid @RequestBody UserActivity userActivity, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request,
                                         @NotNull @ModelAttribute("guid") String guid) throws ProfileServiceAdapterException, UnmarshalException, QNAException, ProcessorException,UnAuthorizedUserExcepton,ProfileServiceException,QNADataAccessException{
        return processMobileRequest(userActivity, guid, userAgent, "eval");
    }



    private LegacyResponse processMobileRequest(UserActivity userActivity ,String guid ,String userAgent , String formType) throws ProfileServiceAdapterException, UnmarshalException, QNAException, ProcessorException,UnAuthorizedUserExcepton, ProfileServiceException,QNADataAccessException {
        userActivity.setUserAgent(userAgent);
        this.prepareUserActivityRequest(userActivity,guid);
        Questionnaire questionnaire = null;
        try{
            questionnaire = qnaAdaptor.getQuestionnaireById(userActivity.getQuestionnaireId(), String.valueOf(userActivity.getGuid()), userActivity.getSiteId(), false, Questionnaire.class);
        }
        catch(Exception ex){
            log.error("Exception "+ExceptionUtils.getStackTrace(ex));
            throw new QNAException("Exception fetching questionnaire ", ex);
        }
        Optional<QuestionForm> optional  = questionnaire.getQuestionForms().stream().filter( formset->( formset.getFormId() == userActivity.getFormId()) ).findFirst();

        if(!optional.isPresent()){
            throw new QNAException("Unable to find Form in Questionnaire for FormId "+userActivity.getFormId());
        }
        userActivity.setFormType(formType);
        createAppRequestProcessor(userActivity);

        return new LegacyResponse().transformResponse(processAppRequest(userActivity, questionnaire).getBody().getResponseMsg());

    }


}
