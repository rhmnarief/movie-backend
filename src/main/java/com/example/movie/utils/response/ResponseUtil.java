package com.example.movie.utils.response;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.movie.constants.CommonConstant;
import com.example.movie.utils.response.ResponseStatus;
import com.example.movie.utils.response.ResponsePage;
import com.example.movie.utils.response.ApiResponse;
import com.example.movie.utils.response.ApiPagedResponse;

import static org.springframework.http.HttpStatus.OK;

@Component
public class ResponseUtil<T> {
    private final ResponseStatus responseStatus;
    private final ResponsePage responsePage;

    public ResponseUtil() {
        responseStatus = new ResponseStatus();
        responsePage = new ResponsePage();
    }

    private void setResponsePage(Page<T> pagedData) {
        responsePage.setPage(pagedData.getNumber() + 1);
        responsePage.setRows(CommonConstant.ROWS_PER_PAGE);
        responsePage.setTotalPages(pagedData.getTotalPages());
        responsePage.setTotalRows(pagedData.getTotalElements());
    }

    private void setResponseStatus(HttpStatus status, String message) {
        responseStatus.setCode(status.value());
        responseStatus.setMessage(message);
    }

    public ResponseEntity<ApiResponse<T>> build(T data) {
        setResponseStatus(OK, OK.getReasonPhrase());
    
        ApiResponse<T> body = new ApiResponse<>(data, responseStatus);
    
        return ResponseEntity.status(OK).body(body);
      }
    
      public ResponseEntity<ApiResponse<T>> build(T data, HttpStatus status, String message) {
        setResponseStatus(status, message);
    
        ApiResponse<T> body = new ApiResponse<>(data, responseStatus);
    
        return ResponseEntity.status(status).body(body);
      }
    
      public ResponseEntity<ApiPagedResponse<T>> build(Page<T> pagedData) {
        setResponseStatus(OK, OK.getReasonPhrase());
        setResponsePage(pagedData);
    
        ApiPagedResponse<T> body = new ApiPagedResponse<>(pagedData.getContent(), responseStatus, responsePage);
    
        return ResponseEntity.status(OK).body(body);
      }
    
      public ResponseEntity<ApiPagedResponse<T>> build(Page<T> pagedData, HttpStatus status, String message) {
        setResponseStatus(status, message);
        setResponsePage(pagedData);
    
        ApiPagedResponse<T> body = new ApiPagedResponse<>(pagedData.getContent(), responseStatus, responsePage);
    
        return ResponseEntity.status(status).body(body);
      }
}
