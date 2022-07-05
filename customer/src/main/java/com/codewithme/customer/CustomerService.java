package com.codewithme.customer;

import com.codewithme.amqp.RabbitMQMessageProducer;
import com.codewithme.clients.fraud.FraudCheckResponse;
import com.codewithme.clients.fraud.FraudClient;
import com.codewithme.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final FraudClient fraudClient;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email()).build();

        customerRepository.save(customer);


       // FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}", FraudCheckResponse.class, customer.getId());
        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Codewithme...", customer.getFirstName()));

        rabbitMQMessageProducer.publish(notificationRequest,"internal.exchange","internal.notification.routing-key");
    }
}
