package com.wallet.service;

import com.wallet.entity.User;
import com.wallet.model.CreateUserResponse;
import com.wallet.model.UserDetails;
import com.wallet.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public CreateUserResponse createUser(UserDetails userDetails) {
        User user = modelMapper.map(userDetails, User.class);
        user.setAccountBalance(BigDecimal.ZERO);
        user.setCreationTime(ZonedDateTime.now());
        user.setEncodedPassword(bcryptEncoder.encode(userDetails.getPassword()));
        userRepository.save(user);
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setMessage("User account created.");
        return createUserResponse;
    }


}
