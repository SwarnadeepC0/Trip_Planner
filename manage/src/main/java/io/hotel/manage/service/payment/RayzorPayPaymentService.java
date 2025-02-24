package io.hotel.manage.service.payment;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.hotel.manage.models.Billing;
import io.hotel.manage.models.Booking;
import io.hotel.manage.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service(value="razor-pay")
public class RayzorPayPaymentService implements PaymentService {

    public RazorpayClient razorpayClient;

    @Override
    public Billing pay(Booking booking){
        Billing billing = getBilling(booking);
        return billing;
    }

    @Override
    public String getPaymentLink(Booking booking) {

        return "";
    }

    private Billing getBilling(Booking booking) {
        Order order = createRazorPayOrder(Double.toString(booking.getAmount()));
        Billing billing = new Billing(booking.getBookingId(), booking.getCustomerId(), booking.getHotelId(), booking.getRoomId(), booking.getAmount(), (String)order.get("id"));
        return billing;

    }

    private Order createRazorPayOrder(String amount) {

        JSONObject options = new JSONObject();
        options.put("amount", convertRupeeToPaise(amount));
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture.
        try {
            return razorpayClient.orders.create(options);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();

    }
}
