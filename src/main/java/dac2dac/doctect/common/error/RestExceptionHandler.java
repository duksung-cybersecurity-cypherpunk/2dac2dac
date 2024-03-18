package dac2dac.doctect.common.error;

import dac2dac.doctect.common.error.exception.BadRequestException;
import dac2dac.doctect.common.error.exception.DuplicateException;
import dac2dac.doctect.common.error.exception.ForbiddenException;
import dac2dac.doctect.common.error.exception.InternalServerErrorException;
import dac2dac.doctect.common.error.exception.InterruptedException;
import dac2dac.doctect.common.error.exception.NoSuchElementException;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.common.error.exception.UnauthorizedException;
import dac2dac.doctect.common.response.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    // Custom Bad Request Error
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    protected ApiResult<String> handleBadRequestException(BadRequestException exception,
        HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResult.error(exception.getCode());
    }

    // Custom Unauthorized Error
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    protected ApiResult<String> handleUnauthorizedException(UnauthorizedException exception,
        HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResult.error(exception.getCode());
    }

    // Custom Internal Server Error
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    protected ApiResult<String> handleInternalServerErrorException(
        InternalServerErrorException exception,
        HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResult.error(exception.getCode());
    }

    // @RequestBody valid 에러
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResult<String> handleMethodArgNotValidException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        logInfo(request, message);
        return ApiResult.error(HttpStatus.BAD_REQUEST, message);
    }

    // @ModelAttribute valid 에러
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResult<String> handleMethodArgNotValidException(BindException exception,
        HttpServletRequest request) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        logInfo(request, message);
        return ApiResult.error(HttpStatus.BAD_REQUEST, message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResult<String> handleNotFoundException(NotFoundException exception,
        HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResult.error(exception.getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public ApiResult<String> handleDuplicationException(DuplicateException exception,
        HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResult.error(exception.getCode());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ApiResult<String> handlerForbiddenException(ForbiddenException exception,
        HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResult.error(exception.getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ApiResult<String> handlerNoSuchElementException(ForbiddenException exception,
        HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResult.error(exception.getCode());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InterruptedException.class)
    public ApiResult<String> handlerNoSuchElementException(InterruptedException exception,
        HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResult.error(exception.getCode());
    }

    private void logInfo(HttpServletRequest request, String message) {
        log.info("{} {} : {} (traceId: {})",
            request.getMethod(), request.getRequestURI(), message, getTraceId());
    }

    private void logWarn(HttpServletRequest request, Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        exception.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        log.warn("{} {} (traceId: {})\n{}", request.getMethod(), request.getRequestURI(),
            getTraceId(), stackTrace);
    }

    private String getTraceId() {
        return MDC.get("traceId");
    }
}