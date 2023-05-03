package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.VipLounge;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.VipLoungeService;
import java.util.concurrent.atomic.AtomicLong;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

@Controller
public class VipLoungeController {

    @Autowired
    public VipLoungeService vipLoungeService;

    @Autowired
    MeterRegistry metricsRegistry;

    private final AtomicLong counter = new AtomicLong();


    @GetMapping("/getVipLoungeByEntryPrice")
    @ResponseBody
    @Timed(value = "hello.getVipLoungeByEntryPrice.time", description = "Time taken to return VipLounge")
    @Counted(value = "hello.getVipLoungeByEntryPrice.count", description = "Times getVipLoungeByEntryPrice was used")
    public VipLounge getVipLoungeByEntryPrice(@RequestParam(name="entryPrice", required = true) String entryPrice) throws EntityNotFoundException {
        metricsRegistry.counter("my_non_aop_metric", "endpoint", "hello").increment(counter.incrementAndGet());
        return vipLoungeService.getVipLoungeByEntryPrice(entryPrice);
    }
}
