package com.WorkWave.WorkWave.Repository;

import com.WorkWave.WorkWave.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
