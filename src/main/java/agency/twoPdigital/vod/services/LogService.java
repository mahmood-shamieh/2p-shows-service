package agency.twoPdigital.vod.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class LogService {

    private final ObjectMapper jsonConverter = new ObjectMapper();

    public void log(HttpServletRequest httpServletRequest, Exception ex) {
        jsonConverter.registerModule(new JavaTimeModule());
        Map<String, Object> logMap = new LinkedHashMap<>();
        logMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        logMap.put("Path", httpServletRequest.getRequestURI());
        logMap.put("input", "");
        logMap.put("exception", ex.getMessage());
        try {
            String jsonLog = jsonConverter.writeValueAsString(logMap);
            log.error(jsonLog);
        } catch (JsonProcessingException e) {
            log.error("Failed to log JSON", e);
        }
    }
}
