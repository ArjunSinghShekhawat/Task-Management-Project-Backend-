package com.taskManagement.controllers;

import com.taskManagement.service.CustomUserService;
import com.taskManagement.config.JwtProvider;
import com.taskManagement.exceptions.UserException;
import com.taskManagement.models.User;
import com.taskManagement.repository.UserRepository;
import com.taskManagement.request.LoginRequest;
import com.taskManagement.responce.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserService customUserService;

    @PostMapping("/signup")
    public ResponseEntity<?>signup(@RequestBody User user){

        //check already present or not
        Optional<User> existUser = userRepository.findByEmail(user.getEmail());

        if(existUser.isPresent()){
            throw new UserException("Email used in another account !");
        }

         User newUser = new User();

         newUser.setFullName(user.getFullName());
         newUser.setEmail(user.getEmail());
         newUser.setRole(user.getRole());
         newUser.setPassword(passwordEncoder.encode(user.getPassword()));

         User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        return new ResponseEntity<>(AuthResponse.builder().jwt(token).status(true).message("User Register successfully").build(),HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        return new ResponseEntity<>(AuthResponse.builder().jwt(token).message("Login Success").status(true).build(),HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(email);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
