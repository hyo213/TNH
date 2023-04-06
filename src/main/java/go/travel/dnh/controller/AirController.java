package go.travel.dnh.controller;

import go.travel.dnh.domain.User.LoginUser;
import go.travel.dnh.domain.air.*;
import go.travel.dnh.domain.member.MemberDTO;
import go.travel.dnh.domain.reservation.AirReservationDTO;
import go.travel.dnh.domain.reservation.AirReservationListDTO;
import go.travel.dnh.domain.reservation.ReservationDetail;
import go.travel.dnh.service.AirProductService;
import go.travel.dnh.service.MemberLoginService;
import go.travel.dnh.service.ReservationService;
import go.travel.dnh.validation.ReservationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/air")
@RequiredArgsConstructor
public class AirController {

    private final AirProductService airProductService;


    //항공권 전체 목록
    @GetMapping("/list")
    public String air_list_all(@ModelAttribute("sch") final SearchDTO sch, Model m) {
        PagingResponse<AirProductDTO> list = airProductService.getList(sch);
        m.addAttribute("air", list);
        return "air/list";
    }

    @GetMapping("/region")
    public String region(@ModelAttribute("sch") final SearchDTO sch, Model m){
        PagingResponse<AirProductDTO> listRegion = airProductService.indexSearch(sch);
        List<AirportDTO> airportList = airProductService.getListAirport();

        String from = airport(sch.getOneFrom());
        String to = airport(sch.getOneTo());
        m.addAttribute("fromAP",from);
        m.addAttribute("toAP",to);
        m.addAttribute("airOneWay", listRegion);
        m.addAttribute("airport",airportList);

        return "/air/search-list-region";
    }

    //항공권 검색 페이지
    @GetMapping("/search")
    public String air_search(@ModelAttribute("sch") final SearchDTO sch, Model m) {
        List<AirportDTO> airportList = airProductService.getListAirport();
        m.addAttribute("airport",airportList);
        return "air/search";
    }
    //항공권 검색
    @PostMapping("/search")
    public String air_search(@ModelAttribute("sch") final SearchDTO sch, HttpServletResponse res, Model m) throws IOException {
        PagingResponse<AirProductDTO> listOneWay = airProductService.getSearchOneWayList(sch);
        PagingResponse<AirProductDTO> listFrom = airProductService.getSearchFromList(sch);
        PagingResponse<AirProductDTO> listTo = airProductService.getSearchToList(sch);
        List<AirportDTO> airportList = airProductService.getListAirport();

        m.addAttribute("airport",airportList);

        //편도
        if(sch.getRoundFrom()==null){
            if(listOneWay.getList().isEmpty()){
                res.setContentType("text/html; charset=UTF-8");
                PrintWriter out = res.getWriter();
                out.println("<script>alert('해당 항공편은 존재하지 않습니다. 다시 검색해주세요'); </script>");
                out.flush();
                return "air/search";
            }

            m.addAttribute("airOneWay", listOneWay);


            String from = airport(sch.getOneFrom());
            String to = airport(sch.getOneTo());

            m.addAttribute("fromAP",from);
            m.addAttribute("toAP",to);

            return "/air/search-list-oneway";

        }
        //왕복
        else if (sch.getOneFrom()==null) {
            if(listFrom.getList().isEmpty() || listTo.getList().isEmpty()){
                res.setContentType("text/html; charset=UTF-8");
                PrintWriter out = res.getWriter();
                out.println("<script>alert('해당 항공편은 존재하지 않습니다. 다시 검색해주세요'); </script>");
                out.flush();
                m.addAttribute("airport",airportList);
                return "air/search";
            }


            m.addAttribute("airFrom", listFrom);
            m.addAttribute("airTo", listTo);

            String from = airport(sch.getRoundFrom());
            String to = airport(sch.getRoundTo());

            m.addAttribute("fromAP",from);
            m.addAttribute("toAP",to);
            return "/air/search-list-round";
        }
        return "air/search";
    }


    @PostMapping("/sort-oneway")
    public String sortListOneWay(@RequestParam("sortValue") String sortValue,
                                 @ModelAttribute("sch") final SearchDTO sch,
                                 Model m) {
        List<AirportDTO> airportList
                = airProductService.getListAirport();


        m.addAttribute("airport", airportList);


        String from = airport(sch.getOneFrom());
        String to = airport(sch.getOneTo());

        m.addAttribute("fromAP",from);
        m.addAttribute("toAP",to);

        PagingResponse<AirProductDTO> sortOneWay
                = airProductService.OneWaySort(sch);
        sch.setSortValue(sortValue);
        m.addAttribute("airOneWay", sortOneWay);
        return "/air/search-list-oneway";
    }

    @PostMapping("/sort-round")
    public String sortListOneWayP(@RequestParam("sortValue") String sortValue,
                                  @ModelAttribute("sch") final SearchDTO sch,
                                  Model m) {

        List<AirportDTO> airportList = airProductService.getListAirport();

        m.addAttribute("airport", airportList);

        String from = airport(sch.getRoundFrom());
        String to = airport(sch.getRoundTo());

        m.addAttribute("fromAP",from);
        m.addAttribute("toAP",to);

        PagingResponse<AirProductDTO> sortRoundOut = airProductService.roundSortOut(sch);
        PagingResponse<AirProductDTO> sortRoundIN = airProductService.roundSortIn(sch);
        m.addAttribute("airFrom", sortRoundOut);
        m.addAttribute("airTo", sortRoundIN);

        System.out.println(sortRoundOut.getList());
        System.out.println(sortRoundIN.getList());

        return "/air/search-list-round";
    }

    @GetMapping("/search-list-region")
    public String regionSchList(@ModelAttribute("sch") final SearchDTO sch,Model m){
        PagingResponse<AirProductDTO> listRegion = airProductService.indexSearch(sch);
        List<AirportDTO> airportList = airProductService.getListAirport();


        m.addAttribute("airOneWay", listRegion);
        m.addAttribute("airport",airportList);

        return "air/search-list-region";
    }

    @GetMapping("/search-list-oneway")
    public String onewaySchList(@ModelAttribute("sch") final SearchDTO sch,Model m){
        PagingResponse<AirProductDTO> listOneWay = airProductService.getSearchOneWayList(sch);
        List<AirportDTO> airportList = airProductService.getListAirport();


        m.addAttribute("airOneWay", listOneWay);
        m.addAttribute("airport",airportList);

        return "air/search-list-oneway";
    }

    @GetMapping("/search-list-round")
    public String roundSchList(@ModelAttribute("sch") final SearchDTO sch,Model m){

        PagingResponse<AirProductDTO> listFrom = airProductService.getSearchFromList(sch);
        PagingResponse<AirProductDTO> listTo = airProductService.getSearchToList(sch);
        List<AirportDTO> airportList = airProductService.getListAirport();

        m.addAttribute("airFrom", listFrom);
        m.addAttribute("airTo", listTo);
        m.addAttribute("airport",airportList);
        return "air/search-list-round";
    }


    //공항이름 넘기는 메소드
    public String airport(Integer airportCode){
        List<AirportDTO> airportList = airProductService.getListAirport();

        String apCode = "";
        for (int i = 0; i < airportList.size(); i++) {
            if(airportCode.equals(airportList.get(i).getAp_code())){
                apCode = airportList.get(i).getAp_name();
            }
        }
        return apCode;
    }


}
