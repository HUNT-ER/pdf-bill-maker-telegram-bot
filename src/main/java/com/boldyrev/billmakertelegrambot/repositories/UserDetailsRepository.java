package com.boldyrev.billmakertelegrambot.repositories;

import com.boldyrev.billmakertelegrambot.exceptions.UserNotRegisteredException;
import com.boldyrev.billmakertelegrambot.models.UserDetails;
import java.util.HashSet;
import java.util.Set;
import org.telegram.telegrambots.meta.api.objects.User;

public enum UserDetailsRepository {

    STORAGE;

    private Set<UserDetails> registeredUsers;

    UserDetailsRepository() {
        registeredUsers = new HashSet<>();
    }

    public boolean containsUser(User user) {
        return registeredUsers.stream().anyMatch(u -> u.getUser().getId().equals(user.getId()));
    }

    public UserDetails getUserDetails(User user) {
        if (!containsUser(user)) {
            throw new UserNotRegisteredException("User not registered");
        }
        return registeredUsers.stream().filter(u -> u.getUser().getId().equals(user.getId()))
            .findFirst().get();
    }

    public void save(UserDetails userDetails) {
        registeredUsers.add(userDetails);
    }

}
