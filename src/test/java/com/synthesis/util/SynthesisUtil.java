package com.synthesis.util;

import com.synthesis.entities.User;

public class SynthesisUtil {
    public static User preapreUser() {
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setFirstName("Alex");
        return user;
    }
}
