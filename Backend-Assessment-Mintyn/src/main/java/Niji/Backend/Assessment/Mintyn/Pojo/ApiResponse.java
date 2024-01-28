package Niji.Backend.Assessment.Mintyn.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T payload;
}


//change