package Niji.Backend.Assessment.Mintyn.Dtos;

import Niji.Backend.Assessment.Mintyn.Pojo.Response.ResponseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardStatisticsResponse extends ResponseObject {
    private long size;
    private int start;
    private int limit;
    private Map<String, Integer> data;
    private boolean success;



}
