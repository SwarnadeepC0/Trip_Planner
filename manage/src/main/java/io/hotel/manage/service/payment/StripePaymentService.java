package io.hotel.manage.service.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import io.hotel.manage.models.Billing;
import io.hotel.manage.models.Booking;
import io.hotel.manage.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("stripe")
public class StripePaymentService implements PaymentService {
    @Value("${stripe.api.key}")
    private String apiKey;

    @Override
    public Billing pay(Booking booking) {
        return null;
    }

    @Override
    public String getPaymentLink(Booking booking) {
        try {
        Stripe.apiKey = this.apiKey;
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
            RequestOptions options =
                    RequestOptions.builder()
                            .setIdempotencyKey(price.getId())
                            .build();
            PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams, options);
            System.out.println(paymentLink.getId());
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }
}
