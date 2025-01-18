package com.WorkWave.WorkWave.Service;

import com.WorkWave.WorkWave.Config.JwtProvider;
import com.WorkWave.WorkWave.Model.User;
import com.WorkWave.WorkWave.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserProfileByJWT(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserProfileByEmail(email);
    }

    @Override
    public User findUserProfileByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("user not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("user not found");

        }
        return user.get();
    }

    @Override
    public User updateUsersProjectSize(User user, int number) throws Exception {
        user.setProjectsize(user.getProjectsize()+number);
        return userRepository.save(user);
    }
}
