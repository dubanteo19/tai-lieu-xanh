package com.nlu.tai_lieu_xanh.data_loader;

import com.nlu.tai_lieu_xanh.dto.request.UserCreateRequest;
import com.nlu.tai_lieu_xanh.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class UserLoader implements CommandLineRunner {
    private final UserService userService;
    @Value("${include-data-loader}")
    boolean includeDataLoader;

    public UserLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (includeDataLoader) {
            return;
        }
        var user1 = new UserCreateRequest(
                "dubanteo2003@gmail.com", "Du Ban Teo", "123");
        var user2 = new UserCreateRequest(
                "minh@gmail.com", "Du Thanh Minh", "123");
        var registeredUser1 = userService.register(user1);
        var registeredUser2 = userService.register(user2);
        userService.approveUser(registeredUser1.getId());
        userService.approveUser(registeredUser2.getId());
    }
}

