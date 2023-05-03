package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.LocationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Location;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.LocationService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    MeterRegistry metricsRegistry;

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/getLocationByAddress")
    @ResponseBody
    @Timed(value = "hello.getLocation.time", description = "Time taken to return address")
    @Counted(value = "hello.getLocation.count", description = "Times getLocation was used")
    public Location getLocationByAddress(@RequestParam(name="address", required = true, defaultValue = "Bulevardul General Paul Teodorescu 4, București 061344") String address) throws  EntityNotFoundException{
        metricsRegistry.counter("my_non_aop_metric", "endpoint", "hello").increment(counter.incrementAndGet());
        return locationService.getLocationByAddress(address);
    }

    @PostMapping
    @ResponseBody
    @Timed(value = "hello.addLocation.time", description = "Time taken to return location")
    @Counted(value = "hello.addLocation.count", description = "Times addLocation was used")
    public boolean addLocation(@RequestParam(name="address", required = true) String address, @RequestParam(name="name", required = true) String name, @RequestParam(name="phoneNumber", required = true) String phoneNumber){
        metricsRegistry.counter("my_non_aop_metric", "endpoint", "hello").increment(counter.incrementAndGet());
        return locationService.addLocation(new Location(address, name, phoneNumber));
    }

}
