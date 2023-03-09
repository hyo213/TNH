package go.travel.dnh.controller;

import go.travel.dnh.domain.pay.PayDTO;
import go.travel.dnh.domain.reservation.AirReservationListDTO;
import go.travel.dnh.service.PaymentService;
import go.travel.dnh.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class PayController {

    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private int totalPrice;

    @GetMapping("{rno}")
    public String payrev(@PathVariable("rno") Long rno, Model model) {

        AirReservationListDTO revDto = reservationService.getReservation(rno);
        List<AirReservationListDTO> revDtDto = reservationService.getReservationDetail(rno);

        /*결제 완료된 경우 여기 페이지로 오면 안된다.*/
        if(revDto.getArp_state().equalsIgnoreCase("결제 완료")){
            model.addAttribute("message", "이미 결제 완료된 내역입니다");
            model.addAttribute("searchURL", "/");
            return "message";
        }
        totalPrice = revDto.getArp_price();
        model.addAttribute("list", revDto);
        model.addAttribute("listDetail", revDtDto);
        return "order/payment";
    }

    /*카드결제 성공 후*/
    @PostMapping("/payment/complete")
    @ResponseBody
    public ResponseEntity<String> paymentComplete(String imp_uid, Long merchant_uid, String pay_method) throws IOException {

        //1. 아임포트 API키와 SECRET키로 토큰을 생성
        String token = paymentService.getToken();

        //토큰으로 결제 완료된 주문정보를 가져온다.
        int amount = paymentService.paymentInfo(imp_uid, token);


        //DB에서 실제 계산되어야 할 가격 가져오기
        try {
            // 계산 된 금액과 실제 금액이 다를 때
            if (totalPrice != amount) {
                paymentService.paymentCancle(token,imp_uid, amount, "결제 금액 오류");
                return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);
            }

            /*성공로직*/
            paymentService.insertPay(imp_uid,merchant_uid,totalPrice,pay_method);

            return new ResponseEntity<>("결제가 완료되었습니다.", HttpStatus.OK);

        } catch (Exception e) {
            paymentService.paymentCancle(token, imp_uid, amount, "결제 에러");
            return new ResponseEntity<String>("결제 에러!", HttpStatus.BAD_REQUEST);
        }
    }

    /*결제성공 페이지*/
    @GetMapping("/confirm")
    public String bookingConfirm(@RequestParam Long rno, Model model) {
        AirReservationListDTO revDto = reservationService.getReservation(rno);
        PayDTO payDTO = paymentService.readPay(paymentService.readPno(rno));

        /*결제 취소된 이후 여기 페이지로 오면 안된다.*/
        if(revDto.getArp_state().equalsIgnoreCase("결제 취소")){
            model.addAttribute("message", "이미 결제 취소된 내역입니다");
            model.addAttribute("searchURL", "/");
            return "message";
        }

        model.addAttribute("rlist", revDto);
        model.addAttribute("plist", payDTO);
        return "order/bookingConfirm";
    }

}
