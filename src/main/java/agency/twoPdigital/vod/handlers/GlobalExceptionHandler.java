package agency.twoPdigital.vod.handlers;

import agency.twoPdigital.vod.excpetions.CreateSeasonException;
import agency.twoPdigital.vod.excpetions.NotFoundException;
import agency.twoPdigital.vod.models.ApiResponse;
import agency.twoPdigital.vod.services.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    LogService logService;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        logService.log(request,ex);
        return ApiResponse.builder()
                .statusCode(404)
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(CreateSeasonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleCreateShowException(Exception ex,HttpServletRequest request) {
        logService.log(request,ex);
        return ApiResponse.builder()
                .statusCode(500)
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleRequestBodyCheckException(Exception ex,HttpServletRequest request) {
        logService.log(request,ex);
        return ApiResponse.builder()
                .statusCode(400)
                .message("Request Body Should Be Provided")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map> handleValidationExceptions(MethodArgumentNotValidException ex,HttpServletRequest request) {
        logService.log(request,ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ApiResponse.<Map>builder()
                .body(errors)
                .message("Kindly Check The Errors")
                .statusCode(400)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleUnknownException(Exception ex,HttpServletRequest request) {
        logService.log(request,ex);
        return ApiResponse.builder()
                .statusCode(500)
                .message(ex.getMessage())
                .build();
    }
}
