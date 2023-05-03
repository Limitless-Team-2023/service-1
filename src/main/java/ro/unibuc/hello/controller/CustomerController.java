package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.CustomerService;
import java.util.concurrent.atomic.AtomicLong;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    MeterRegistry metricsRegistry;

    private final AtomicLong counter = new AtomicLong();


    @GetMapping("/getCustomerByName")
    @ResponseBody
    @Timed(value = "hello.getCustomerById.time", description = "Time taken to return customer")
    @Counted(value = "hello.getCustomerById.count", description = "Times getCustomerById was used")
    public Customer getCustomerById(@RequestParam(name="name", required = true) String Name) throws EntityNotFoundException {
        metricsRegistry.counter("my_non_aop_metric", "endpoint", "customer").increment(counter.incrementAndGet());
        return customerService.getCustomerByName(Name);
    }
}
