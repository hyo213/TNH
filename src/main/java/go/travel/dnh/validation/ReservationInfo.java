package go.travel.dnh.validation;

import lombok.*;

@Data
public class ReservationInfo {

    private String air_from;
    private String air_to;
    private Integer air_from_check;
    private Integer air_to_check;
    private Integer ea;

}
