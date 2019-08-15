package com.  .activity.cmepulse.controller;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.  .activity.exception.*;
import com.  .activity.model.AppResponse;
import com.  .activity.model.LegacyResponse;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.util.ControllerUtils;
import com.  .auth.unmarshal.UnmarshalException;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SSangapalli
 *
 */
@ControllerAdvice(basePackages = "com.  .activity.cmepulse.controller")
public class MobileExceptionHandlerController {

	
	
	@ExceptionHandler(value = InvalidObjectException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<LegacyResponse> handleInvalidObjectException(HttpServletRequest request,
			InvalidObjectException exception) {
		return LegacyResponse.getResponse(exception.getErrorMessages().toString(),ActivityExceptionsEnum.INVALIDOBJECTEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<LegacyResponse> handleIllegalArgumentException(HttpServletRequest request,
			IllegalArgumentException exception) {
		return LegacyResponse.getResponse(exception.getMessage().toString(),ActivityExceptionsEnum.ILLEGALARGUMENTEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IOException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<LegacyResponse> handleIOException(HttpServletRequest request, IOException e) {
		return LegacyResponse.getResponse(e.getMessage().toString(),ActivityExceptionsEnum.IOEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UnAuthorizedUserExcepton.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ResponseEntity<LegacyResponse> UnAuthorizedUserException(HttpServletRequest request,
			UnAuthorizedUserExcepton e) {
		return LegacyResponse.getResponse(e.getMessage().toString(),ActivityExceptionsEnum.UNAUTHORIZEDUSEREXCEPTION.getErrorCode(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = ProcessorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<LegacyResponse> processorException(HttpServletRequest request, ProcessorException e) {
		return LegacyResponse.getResponse(e.getType().getErrorMsg(),e.getType().getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = QNAException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<LegacyResponse> QNAException(HttpServletRequest request, QNAException e) {
		return LegacyResponse.getResponse(e.getMessage().toString(),e.getType().getErrorCode(), HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler(value = AdapterException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<LegacyResponse> adaptorExcepton(HttpServletRequest request, AdapterException ex) {
		return LegacyResponse.getResponse(ex.getMessage().toString(),ex.getType().getErrorCode(), HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<LegacyResponse> handleValidationException(MethodArgumentNotValidException ex) {

		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<String> errros = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<>();
		for (FieldError fieldError : fieldErrors) {
			errros.add(String.format("Field : %s -> Error Msg : %s", fieldError.getField(), fieldError.getDefaultMessage()));
			map.put("errors", errros);
		}
		return LegacyResponse.getResponse(map.toString(),ActivityExceptionsEnum.METHODARGUMENTNOTVALIDEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<LegacyResponse> handleMessageNotReadableException(HttpMessageNotReadableException ex,
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

		return LegacyResponse.getResponse(errorMessage.toString(),ActivityExceptionsEnum.HTTPMESSAGENOTREADABLEEXCEPTION.getErrorCode(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProfileServiceException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<LegacyResponse> handleControllerException(ProfileServiceException ex,
			HttpServletRequest request) {
		return LegacyResponse.getResponse(ex.getType().getErrorMsg(),ex.getType().getErrorCode(), HttpStatus.FAILED_DEPENDENCY);

	}

	@ExceptionHandler(UnmarshalException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
	@ResponseBody
	public ResponseEntity<LegacyResponse> handleControllerException(UnmarshalException ex, HttpServletRequest request) {
		return LegacyResponse.getResponse(ex.getMessage() ,ActivityExceptionsEnum.UNMARSHALEXCEPTION.getErrorCode(), HttpStatus.FAILED_DEPENDENCY);
	}
	@ExceptionHandler(value = QNADataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<LegacyResponse> processorException(HttpServletRequest request, QNADataAccessException e) {
		return LegacyResponse.getResponse(e.getType().getErrorMsg(),e.getType().getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
