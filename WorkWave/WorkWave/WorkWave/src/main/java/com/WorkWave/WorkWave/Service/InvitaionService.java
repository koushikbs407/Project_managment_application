package com.WorkWave.WorkWave.Service;

import com.WorkWave.WorkWave.Model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitaionService {



    public  void Sendinvitation(String email,Long projectId) throws MessagingException;
    public Invitation AcceptInviataion(String Token,Long userId) throws Exception;
    public String GetTokenByuserEmail(String UserEmail);

    void DeleteToken(String Token);
}
