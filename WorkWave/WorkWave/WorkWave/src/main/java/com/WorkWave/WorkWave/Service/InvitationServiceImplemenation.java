package com.WorkWave.WorkWave.Service;

import com.WorkWave.WorkWave.Model.Invitation;
import com.WorkWave.WorkWave.Repository.InvitationRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class InvitationServiceImplemenation implements InvitaionService{

    @Autowired
    private EmailService emailService;
    @Autowired
    private InvitationRepository invitationRepository;

    @Override
    public void Sendinvitation(String email, Long projectId) throws MessagingException {
        String invitation = UUID.randomUUID().toString();
        Invitation invitation1 = new Invitation();
        invitation1.setEmail(email);
        invitation1.setProjectId(projectId);
        invitation1.setToken(invitation);
        invitationRepository.save(invitation1);

        String invitationLink = "http://localhost:5173/acceptinvitation?token="+invitation;
        emailService.sendEmailwithToken(email,invitationLink);

    }

    @Override
    public Invitation AcceptInviataion(String Token, Long userId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(Token);
        if (invitation==null){
            throw new Exception("invalid Invitation Token");
        }
        return invitation;
    }

    @Override
    public String GetTokenByuserEmail(String UserEmail) {
        Invitation invitation = invitationRepository.findByEmail(UserEmail);
        return invitation.getToken();
    }

    @Override
    public void DeleteToken(String Token) {
        Invitation invitation = invitationRepository.findByToken(Token);

        invitationRepository.delete(invitation);

    }
}
