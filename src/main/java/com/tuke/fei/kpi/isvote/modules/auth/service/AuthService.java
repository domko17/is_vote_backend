package com.tuke.fei.kpi.isvote.modules.auth.service;

import com.tuke.fei.kpi.isvote.modules.auth.dto.*;
import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;

public interface AuthService {

    /**
     * Resets user password
     *
     * @param mail to which the password is sent
     */
    void resetPassword(String mail);

    /**
     * Returns current logged user
     *
     * @return logged user
     */
    UserDto getCurrentUser();

    /**
     * Login with refresh token
     *
     * @param refreshTokenDto token
     * @return access and refresh token
     */
    TokenResponse logInViaRefreshToken(RefreshTokenDto refreshTokenDto);

    /**
     * Login with credentials
     *
     * @param credentials user credentials
     * @return access and refresh token
     */
    TokenResponse logInViaCredentials(CredentialsDto credentials);

    /**
     * Changes user password
     *
     * @param passwordChangeDto new and old passwords
     * @return access and refresh token
     */
    TokenResponse changePassword(PasswordChangeDto passwordChangeDto);

    /**
     * method to register/create new user in the system
     * @param createUserDto requested payload with data
     * @return current user dto
     */
    UserDto createUser(CreateUserDto createUserDto);



}
