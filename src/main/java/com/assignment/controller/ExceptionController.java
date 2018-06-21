package com.assignment.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.assignment.constant.ErrorCodeConstants;
import com.assignment.dto.ErrorDto;

@ControllerAdvice
public class ExceptionController {

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<List<ErrorDto>> handleInternalErrorException(Exception e) {
		final List<ErrorDto> errorDto = new ArrayList<>();
		errorDto.add(new ErrorDto(ErrorCodeConstants.INTERNAL_ERROR, "", "Something went wrong"));
		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
