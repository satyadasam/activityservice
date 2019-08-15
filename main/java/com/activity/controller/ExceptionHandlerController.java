package com.  .activity.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.  .activity.exception.ActivityExceptionsEnum;
import com.  .activity.exception.AdapterException;
import com.  .activity.exception.InvalidObjectException;
import com.  .activity.exception.ProcessorException;
import com.  .activity.exception.ProfileServiceException;
import com.  .activity.exception.QNAException;
import com.  .activity.exception.UnAuthorizedUserExcepton;
import com.  .activity.model.AppResponse;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.util.ControllerUtils;
import com.  .auth.unmarshal.UnmarshalException;
import com.  .profile_service.adapter.exception.ProfileServiceAdapterException;

/**
 * @author SSangapalli
 *
 */
@ControllerAdvice(basePackages = "com.  .activity.controller")
public class ExceptionHandlerController {

	
	
	@ExceptionHandler(value = InvalidObjectException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<AppResponse> handleInvalidObjectException(HttpServletRequest request,
			InvalidObjectException exception) {
		return ControllerUtils.getResponse(exception.getErrorMessages().toString(),ActivityExceptionsEnum.INVALIDOBJECTEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<AppResponse> handleIllegalArgumentException(HttpServletRequest request,
			IllegalArgumentException exception) {
		return ControllerUtils.getResponse(exception.getMessage().toString(),ActivityExceptionsEnum.ILLEGALARGUMENTEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IOException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<AppResponse> handleIOException(HttpServletRequest request, IOException e) {
		return ControllerUtils.getResponse(e.getMessage().toString(),ActivityExceptionsEnum.IOEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UnAuthorizedUserExcepton.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ResponseEntity<AppResponse> UnAuthorizedUserException(HttpServletRequest request,
			UnAuthorizedUserExcepton e) {
		return ControllerUtils.getResponse(e.getMessage().toString(),ActivityExceptionsEnum.UNAUTHORIZEDUSEREXCEPTION.getErrorCode(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = ProcessorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<AppResponse> processorException(HttpServletRequest request, ProcessorException e) {
		return ControllerUtils.getResponse(e.getType().getErrorMsg(),e.getType().getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = QNAException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<AppResponse> QNAException(HttpServletRequest request, QNAException e) {
		return ControllerUtils.getResponse(e.getMessage().toString(),e.getType().getErrorCode(), HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler(value = AdapterException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<AppResponse> adaptorExcepton(HttpServletRequest request, AdapterException ex) {
		return ControllerUtils.getResponse(ex.getMessage().toString(),ex.getType().getErrorCode(), HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<AppResponse> handleValidationException(MethodArgumentNotValidException ex) {

		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<String> errros = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<>();
		for (FieldError fieldError : fieldErrors) {
			errros.add(String.format("Field : %s -> Error Msg : %s", fieldError.getField(), fieldError.getDefaultMessage()));
			map.put("errors", errros);
		}
		return ControllerUtils.getResponse(map.toString(),ActivityExceptionsEnum.METHODARGUMENTNOTVALIDEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<AppResponse> handleMessageNotReadableException(HttpMessageNotReadableException ex,
			HttpServletRequest request) {

		StringBuilder errorMessage = new StringBuilder("Error parsing json.");
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException ife = (InvalidFormatException) ex.getCause();
			errorMessage = new StringBuilder();

			errorMessage.append(ife.getValue());

			errorMessage.append(" is not a valid value for the ");

			Reference reference = ife.getPath().get(0);

			errorMessage.append(reference.getFieldName()).append(" field of ")
					.append(reference.getFrom().getClass().getSimpleName());
		}

		return ControllerUtils.getResponse(errorMessage.toString(),ActivityExceptionsEnum.HTTPMESSAGENOTREADABLEEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProfileServiceException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<AppResponse> handleControllerException(ProfileServiceException ex,
			HttpServletRequest request) {
		return ControllerUtils.getResponse(ex.getType().getErrorMsg(),ex.getType().getErrorCode(), HttpStatus.FAILED_DEPENDENCY);

	}

	@ExceptionHandler(UnmarshalException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<AppResponse> handleControllerException(UnmarshalException ex, HttpServletRequest request) {
		return ControllerUtils.getResponse(ex.getMessage() ,ActivityExceptionsEnum.UNMARSHALEXCEPTION.getErrorCode(), HttpStatus.FAILED_DEPENDENCY);
	}
	@ExceptionHandler(value = QNADataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<AppResponse> processorException(HttpServletRequest request, QNADataAccessException e) {
		return ControllerUtils.getResponse(e.getType().getErrorMsg(),e.getType().getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
