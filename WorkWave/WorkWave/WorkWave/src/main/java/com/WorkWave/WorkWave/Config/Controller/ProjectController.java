package com.WorkWave.WorkWave.Config.Controller;

import com.WorkWave.WorkWave.Model.Chat;
import com.WorkWave.WorkWave.Model.Project;
import com.WorkWave.WorkWave.Model.User;
import com.WorkWave.WorkWave.Service.ProjectService;
import com.WorkWave.WorkWave.Service.UserService;
import com.WorkWave.WorkWave.messages.ProjectResponsemessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Project>>getAllProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String token

    ) throws Exception {
        User user = userService.findUserProfileByJWT(token);
        List<Project> projects = projectService.getAllProjects(user,category,tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projecId}")
    public ResponseEntity<Project>getProjectById(@PathVariable("projecId") Long projecId,

            @RequestHeader("Authorization") String token

    ) throws Exception {
        User user = userService.findUserProfileByJWT(token);
        Project project = projectService.getProjectById(projecId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project>createProject(@RequestHeader("Authorization") String token,
                                                @RequestBody Project project

    ) throws Exception {
        User user = userService.findUserProfileByJWT(token);
        Project createdproject = projectService.createProject(project,user);
        return new ResponseEntity<>(createdproject, HttpStatus.OK);
    }


    @PatchMapping("/{projectId}")
    public ResponseEntity<Project>updateProject(@PathVariable("projecId") Long projecId,

                                                 @RequestHeader("Authorization") String token,
                                                @RequestBody Project project

    ) throws Exception {
        User user = userService.findUserProfileByJWT(token);
        Project updatedProject = projectService.updateProject(project,projecId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projecId}")
    public ResponseEntity<ProjectResponsemessage>deleteProject(@PathVariable("projecId") Long projecId,

                                                 @RequestHeader("Authorization") String token

    ) throws Exception {
        User user = userService.findUserProfileByJWT(token);
        projectService.deleteProject(projecId,user.getId());
        ProjectResponsemessage projectResponsemessage = new ProjectResponsemessage();
        projectResponsemessage.setMessage("project deleted succesfully");
        return new ResponseEntity<>(projectResponsemessage, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>>SearchProjects(
            @RequestParam(required = false) String keyword,

            @RequestHeader("Authorization") String token

    ) throws Exception {
        User user = userService.findUserProfileByJWT(token);
        List<Project> projects = projectService.getProjectsByUser(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat>getProjectchat(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String token

    ) throws Exception {
        User user = userService.findUserProfileByJWT(token);
        Chat chat = projectService.getChatById(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }


}
