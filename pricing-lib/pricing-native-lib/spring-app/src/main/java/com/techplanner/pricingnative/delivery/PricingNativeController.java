package com.techplanner.pricingnative.delivery;

import com.techplanner.pricingnative.service.PricingNativeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/pricing")
public class PricingNativeController {

    private final PricingNativeService service;

    public PricingNativeController(PricingNativeService service) {
        this.service = service;
    }

    @GetMapping("/calculate")
    public Map<String, Object> calculate(
            @RequestParam double basePrice,
            @RequestParam double tax
    ) {

        return Map.of(
                "operation", "calculate_price",
                "basePrice", basePrice,
                "tax", tax,
                "result",
                service.calculatePrice(basePrice, tax)
        );
    }

    @GetMapping("/discount")
    public Map<String, Object> discount(
            @RequestParam int amount,
            @RequestParam int percentage
    ) {

        return Map.of(
                "operation", "apply_discount",
                "amount", amount,
                "percentage", percentage,
                "result",
                service.applyDiscount(amount, percentage)
        );
    }

    @GetMapping("/fibonacci")
    public Map<String, Object> fibonacci(
            @RequestParam int n
    ) {

        return Map.of(
                "operation", "fibonacci",
                "n", n,
                "result",
                service.fibonacci(n)
        );
    }
}