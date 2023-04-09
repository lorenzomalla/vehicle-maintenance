package it.lorenzomalla.app.configuration.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class AspectAop {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired(required = false)
	private HttpServletRequest request;

	@AfterReturning(value = "execution(* it.lorenzomalla.app.service.*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		try {
			if (result != null) {
				log.info("Start call {} con Response - {} - Transaction_Id : {}",
						request.getRequestURI() + request.getQueryString(), objectMapper.writeValueAsString(result),
						request.getHeader("transactionId"));
			} else {
				log.info("End call {} con Response - {} ", request.getRequestURI() + request.getQueryString(), result);
			}
		} catch (JsonProcessingException jpe) {
			log.error("Error during parse Request to JSON");
		}
	}

	@After(value = "execution(* it.lorenzomalla.app.service.*.*(..))")
	public void after(JoinPoint joinPoint) {
		try {
			if (joinPoint != null && joinPoint.getArgs() != null) {
				if (request.getMethod().equals("GET") || request.getMethod().equals("DELETE")) {
					log.info("Start call : {} - Request : {} - Transaction_Id : {} ", request.getRequestURI(),
							request.getQueryString(), request.getHeader("transactionId"));
				} else {
					log.info("Start call : {} - con Request : {} - Transaction_Id : {}",
							request.getRequestURI() + request.getQueryString(),
							objectMapper.writeValueAsString(joinPoint.getArgs()), request.getHeader("transactionId"));
				}
			}
		} catch (JsonProcessingException jpe) {
			log.error("Error during parse Request to JSON");
		}
	}

}