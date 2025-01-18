package com.WorkWave.WorkWave.Service;

import com.WorkWave.WorkWave.Model.Chat;
import com.WorkWave.WorkWave.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImplenetation implements ChatSevice{
    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Chat creatChat(Chat chat) {

        return chatRepository.save(chat);
    }
}
