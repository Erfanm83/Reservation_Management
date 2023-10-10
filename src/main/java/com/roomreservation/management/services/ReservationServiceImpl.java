package com.roomreservation.management.services;

import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    private Map<UUID , User> UserMap;

    private UserRepository userRepository;

    @Autowired
    public ReservationServiceImpl(UserRepository userRepository) {
        this.UserMap = new HashMap<>();
        this.userRepository = userRepository;
    }

    @Override
    public List<User> userlist() {
        return null;
    }

    @Override
    public User getUserById(UUID id) {
        log.debug("Get User by Id - in service. Id: " + id.toString());

        return UserMap.get(id);
    }

    @Override
    public User saveNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUserById(UUID userId, User User) {
        User existing = UserMap.get(userId);
        existing.setId(User.getId());
        existing.setName(User.getName());
        existing.setLastname(User.getLastname());

    }

    @Override
    public List<User> listUsers(){
        return new ArrayList<>(UserMap.values());
    }

    @Override
    public void deleteById(UUID userId) {
        UserMap.remove(userId);
    }

    //Dorost shavad badan
    @Override
    public void patchUserById(UUID UserId, User User) {

        User existing = UserMap.get(UserId);

        if (StringUtils.hasText(User.getName())){
            existing.setName(User.getName());
        }

        if (StringUtils.hasText(User.getLastname())){
            existing.setName(User.getLastname());
        }

        if (User.getName() != null) {
            existing.setName(User.getName());
        }

        if (User.getLastname() != null) {
            existing.setLastname(User.getLastname());
        }

        if (User.getId() != null){
            existing.setId(User.getId());
        }
    }
}
