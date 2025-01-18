package com.WorkWave.WorkWave.Service;

import com.WorkWave.WorkWave.Model.Chat;
import com.WorkWave.WorkWave.Model.Project;
import com.WorkWave.WorkWave.Model.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user)throws Exception;
    List<Project> getAllProjects(User user,String category,String Tag)throws Exception;
    Project getProjectById(Long projectId)throws Exception;
    Project updateProject(Project project, Long id)throws Exception;
    void deleteProject(Long projectId,Long userId)throws Exception;
    void AddUserToProject(Long projectId,Long userId)throws Exception;
    void RemoveUserFromProject(Project project,Long userId)throws Exception;
    Chat getChatById(Long ProjectId)throws Exception;
    List<Project> getProjectsByUser(String keyword,User user)throws Exception;


    }
