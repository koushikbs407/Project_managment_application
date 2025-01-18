package com.WorkWave.WorkWave.Repository;

import com.WorkWave.WorkWave.Model.Project;
import com.WorkWave.WorkWave.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectServiceRepository extends JpaRepository<Project, Long> {

//    List<Project> findByOwner(User owner);
    List<Project> findByNameContainingAndTeamContaining(String name,User user);
//    @Query("SELECT p FROM Project p join p.team t where t=:user")
//    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingorOwner(User user,User owner);

}
