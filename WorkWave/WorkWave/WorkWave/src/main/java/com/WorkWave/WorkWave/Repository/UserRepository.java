package com.WorkWave.WorkWave.Repository;

import com.WorkWave.WorkWave.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
