package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceQuerierService {

    @Autowired
    private UserRepository userRepository;

    public Balance query(Long userId) {
        UserRecord user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new Balance(user.getBalance());
        } else {
            return new Balance(0f);
        }
    }
}
