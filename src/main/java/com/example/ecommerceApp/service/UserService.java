package com.example.ecommerceApp.service;

import com.example.ecommerceApp.api.model.LoginBody;
import com.example.ecommerceApp.api.model.RegistrationBody;
import com.example.ecommerceApp.exception.UserAlreadyExistsException;
import com.example.ecommerceApp.model.LocalUser;
import com.example.ecommerceApp.model.dao.LocalUserDao;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private LocalUserDao localUserDao;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JwtService jwtService;


    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {

        if (localUserDao.findByUsernameIgnoreCase(registrationBody.getUserName()).isPresent() || localUserDao.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        };
        LocalUser localUser = new LocalUser();
        localUser.setEmail(registrationBody.getEmail());

        localUser.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));

        localUser.setFirstName(registrationBody.getFirstName());
        localUser.setLastName(registrationBody.getLastName());
        localUser.setUsername(registrationBody.getUserName());
        localUserDao.save(localUser);
        return localUser;
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opuser = localUserDao.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opuser.isPresent()) {
            LocalUser user = opuser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJwt(user);
            }
        }
        return null;
    }
}
