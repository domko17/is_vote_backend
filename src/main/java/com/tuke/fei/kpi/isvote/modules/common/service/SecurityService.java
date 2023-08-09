package com.tuke.fei.kpi.isvote.modules.common.service;

import com.tuke.fei.kpi.isvote.modules.common.enums.UserType;
import com.tuke.fei.kpi.isvote.modules.common.model.User;

import java.util.Optional;

public interface SecurityService {
    /**
     * @return id of currently logged user
     */
    Optional<String> loggedUserId();

    /**
     * @return id of currently logged user
     */
    String getLoggedUserId();

    /**
     * Fetch currently logged user object from database.
     *
     * @return currently logged user
     */
    Optional<User> loggedUser();

    /**
     * Fetch currently logged user object from database.
     *
     * @return currently logged user or exception
     */
    User getLoggedUser();

    /**
     * Get lazily loaded reference to logged user. This method is not fetching user instance from
     * database.
     *
     * @return currently logged user
     */
    Optional<User> loggedUserReference();

    /**
     * @return type of currently logged user
     */
    Optional<UserType> loggedUserType();

}
