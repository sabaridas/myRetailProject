package com.target.myRetail.service;

import com.target.myRetail.errors.MyRetailException;
import com.target.myRetail.model.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Optional;

@Service
public class ProductDetailsServiceImpl implements ProductDetailService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${DOWNSTREAM_URI}")
    private String downstreamURI;

    private boolean is5xxServerError(Throwable throwable) {
         return throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode().is5xxServerError();
    }

    @Override
    public ProductDetails getProductDetails(String productId) throws MyRetailException {
        ProductDetails prodDetails = null;
        try {
            // 1. call the downstream service, if downstream service fails retry the call 3 times in duration of 100 ms
            // 2. set the timeout of 0.5 seconds to handle latency
            prodDetails = webClientBuilder.build().get()
                    .uri(downstreamURI + productId)// + "/" + productId)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new MyRetailException("4xx error! while calling downstream")))
                    .bodyToMono(ProductDetails.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100))
                            .filter(this::is5xxServerError))
                    .timeout(Duration.ofMillis(50000))
                    .block();
        } catch (final Exception e) {
            throw new MyRetailException("Something went wrong while calling downstream");
        }
        return prodDetails;
    }

}
