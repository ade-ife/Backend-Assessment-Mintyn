package Niji.Backend.Assessment.Mintyn.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailsResponse {
    private int limit;
    private int start;
    private long size;
    private Map<String, Integer> payload;
    private boolean success;
}


