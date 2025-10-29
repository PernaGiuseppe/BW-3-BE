package buildweek5.BW_3_BE.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsWithListDTO(String message, LocalDateTime timeStamp, List<String> errorMessages) {
}
