package com.jypark.coding.common.advice;

import com.jypark.coding.domain.advice.entity.Advice;
import com.jypark.coding.domain.advice.repository.AdviceRepository;
import com.jypark.coding.exception.BadRequestException;
import com.jypark.coding.exception.InternalServerException;
import com.jypark.coding.exception.SlackSendException;
import com.jypark.coding.exception.UserParameterInvalidException;
import com.jypark.coding.exception.dto.CommonExceptionResponseDTO;
import com.jypark.coding.exception.dto.ServerExceptionResponseDTO;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ApiControllerAdvice {

    @Autowired
    private AdviceRepository adviceRepository;

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public CommonExceptionResponseDTO adviceFor(BadRequestException exception) {
        return badRequest(exception);
    }

    @ExceptionHandler(UserParameterInvalidException.class)
    @ResponseBody
    public CommonExceptionResponseDTO adviceFor(UserParameterInvalidException exception) {
        return badRequest(exception);
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseBody
    public CommonExceptionResponseDTO adviceFor(InternalServerException exception) {
        return serverError(exception);
    }

    @ExceptionHandler(SlackSendException.class)
    @ResponseBody
    public ServerExceptionResponseDTO adviceFor(SlackSendException exception) {
        return serverError(exception);
    }

    public ServerExceptionResponseDTO serverError(Exception exception) {
        final Advice advice = save(exception);
        return new ServerExceptionResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
            advice.getUri(), exception);
    }

    public CommonExceptionResponseDTO badRequest(Exception exception) {
        final Advice advice = save(exception);
        return new CommonExceptionResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage(),
            advice.getUri());
    }

    public Advice save(Exception exception) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return adviceRepository.save(new Advice(request, exception));
    }

    @ExceptionHandler
    @ResponseStatus
    public ModelAndView adviceFor(Exception exception) {
        log.error(ExceptionUtils.getStackTrace(exception));
        final Advice advice = save(exception);
        final ModelAndView modelAndView = new ModelAndView("/error/500");
        modelAndView.addObject("advice", advice);
        return modelAndView;
    }
}
