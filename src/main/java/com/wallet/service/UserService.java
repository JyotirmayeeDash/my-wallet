package com.wallet.service;

import com.wallet.constant.ErrorType;
import com.wallet.entity.User;
import com.wallet.model.exception.CustomException;
import com.wallet.model.request.UserDetails;
import com.wallet.model.response.CreateUserResponse;
import com.wallet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public UserService() {
    }

    public CreateUserResponse createUser(UserDetails userDetails) {
        User user = userRepository.findByUserName(userDetails.getUserName());

        if(user!=null) {
            log.warn("User already exists with the user name provided.");
            throw new CustomException(ErrorType.USER_ALREADY_EXISTS);
        }
        user = modelMapper.map(userDetails, User.class);
        user.setWalletBalance(BigDecimal.ZERO);
        user.setCreationTime(ZonedDateTime.now());
        user.setEncodedPassword(bcryptEncoder.encode(userDetails.getPassword()));
        userRepository.save(user);
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setMessage("User account created.");
        return createUserResponse;
    }


}
