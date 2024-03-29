package go.travel.dnh.mapper;

import go.travel.dnh.domain.air.AirProductDTO;
import go.travel.dnh.domain.air.AirlineDTO;
import go.travel.dnh.domain.air.AirportDTO;
import go.travel.dnh.domain.air.SearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AirProductMapper {
    List<AirProductDTO> selectPro(SearchDTO sch);

    List<AirProductDTO> searchAirFrom(SearchDTO sch);
    List<AirProductDTO> searchAirTo(SearchDTO sch);
    List<AirProductDTO> searchAirOneWay(SearchDTO sch);

    List<AirProductDTO> sortAirOneWay(SearchDTO sch);
    List<AirProductDTO> sortAirRoundOut(SearchDTO sch);
    List<AirProductDTO> sortAirRoundIn(SearchDTO sch);

    List<AirProductDTO> selectIndex(SearchDTO sch);

    int count(SearchDTO sch);

    int searchCntOneWay(SearchDTO sch);
    int searchCntFrom(SearchDTO sch);
    int searchCntTo(SearchDTO sch);

    int indexCount(SearchDTO sch);



    List<AirportDTO> selectAP();
    List<AirlineDTO> selectAL();



    AirProductDTO selectRes(Integer ano);

}
