package io.hotel.manage.service.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import io.hotel.manage.models.Billing;
import io.hotel.manage.models.Booking;
import io.hotel.manage.service.PaymentService;
import org.springframework.stereotype.Service;

@Service("stripe")
public class StripePaymentService implements PaymentService {
    @Override
    public Billing pay(Booking booking) {
        return null;
    }

    @Override
    public String getPaymentLink(Booking booking) {
        try {
        Stripe.apiKey = "sk_test_51QvnUR06tJuHilGedC2NggkI14JWDNElZ8ntWklz1PmrY5oajwgwK38YehqFBTqUQsJU9lUSMTY1GTJtpyUfnB6m00qLKR19Rt";
        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("INR")
                        .setUnitAmount(booking.getAmount().longValue()*27)
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Hotel booking").build()
                        )
                        .build();
        Price price = Price.create(params);
//        PaymentLinkCreateParams.AfterCompletion.Redirect redirect = PaymentLinkCreateParams.AfterCompletion.Redirect.builder().setUrl("localhost:8080/manage/hotel/payment/complete").putExtraParam("bookingId", booking.getBookingId()).build();
//        PaymentLinkCreateParams.AfterCompletion afterCompletion = PaymentLinkCreateParams.AfterCompletion.builder()
//                .setRedirect(redirect).setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT).build();
        PaymentLinkCreateParams paymentLinkCreateParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .build();

            PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }
}
