package com.WorkWave.WorkWave.Service;

import com.WorkWave.WorkWave.Model.Chat;
import com.WorkWave.WorkWave.Model.Project;
import com.WorkWave.WorkWave.Model.User;
import com.WorkWave.WorkWave.Repository.ChatRepository;
import com.WorkWave.WorkWave.Repository.ProjectServiceRepository;
import com.WorkWave.WorkWave.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImplenation implements ProjectService{
    @Autowired
    private ProjectServiceRepository projectServiceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRepository chatRepository;


    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project newProject = new Project();
//        newProject.setProjectname(project.getProjectname());
        newProject.setProjectname(project.getProjectname());
        newProject.setDescription(project.getDescription());
       // newProject.setId(project.getId());
        newProject.setOwner(user);
        newProject.getTeam().add(user);
        newProject.setCategory(project.getCategory());
        newProject.setTags(project.getTags());

        projectServiceRepository.save(newProject);
        Chat chat = new Chat();
        chat.setProject(newProject);
        Chat projectChat = chatRepository.save(chat);
        newProject.setChat(projectChat);




        return newProject;
    }

    @Override
    public List<Project> getAllProjects(User user, String category, String Tag) throws Exception {
        List<Project> projects = projectServiceRepository.findByTeamContainingorOwner(user,user);
        if(category!=null) {
            projects.stream().filter(project -> project.getCategory().equals(category) ).collect(Collectors.toList());
        }
        if(Tag!=null) {
            projects.stream().filter(project -> project.getTags().contains(Tag) ).collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
         Optional<Project> project = projectServiceRepository.findById(projectId);
         if(project.isPresent()) {
             throw new Exception("project not found");
         }
        return project.get();
    }

    @Override
    public Project updateProject(Project project, Long id) throws Exception {
        Project project1 = getProjectById(id);
        project1.setDescription(project.getDescription());

        project1.setTags(project.getTags());

        project1.setProjectname(project.getProjectname());

        return projectServiceRepository.save(project1);
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

        projectServiceRepository.deleteById(projectId);

    }

    @Override
    public void AddUserToProject(Long projectId, Long userId) throws Exception {
        Project projectadduser = getProjectById(projectId);
        User user = userRepository.findById(userId).get();
        if(!projectadduser.getTeam().contains(user)) {
            projectadduser.getTeam().add(user);
            projectadduser.getChat().getUsers().add(user);

        }
        projectServiceRepository.save(projectadduser);

    }

    @Override
    public void RemoveUserFromProject(Project project, Long userId) throws Exception {
        User user = userRepository.findById(userId).get();
        Project projectdeleteuser = getProjectById(project.getId());
        if(projectdeleteuser.getTeam().contains(user)) {
            projectdeleteuser.getTeam().remove(user);
            projectdeleteuser.getChat().getUsers().remove(user);
        }
        projectServiceRepository.save(projectdeleteuser);

    }

    @Override
    public Chat getChatById(Long ProjectId) throws Exception {
        Project project = projectServiceRepository.findById(ProjectId).get();

        return project.getChat();
    }

    @Override
    public List<Project> getProjectsByUser(String keyword, User user) throws Exception {
        List<Project> projects = projectServiceRepository.findByNameContainingAndTeamContaining(keyword,user);
        return projects;
    }
}
