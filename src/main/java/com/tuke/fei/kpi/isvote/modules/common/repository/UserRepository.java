package com.tuke.fei.kpi.isvote.modules.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tuke.fei.kpi.isvote.modules.common.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrMail(String username, String mail);

}
