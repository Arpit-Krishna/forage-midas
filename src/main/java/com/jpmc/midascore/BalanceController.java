package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BalanceController {

    @Autowired
    private BalanceQuerierService balanceQuerierService; 

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        return balanceQuerierService.query(userId);
    }
}
