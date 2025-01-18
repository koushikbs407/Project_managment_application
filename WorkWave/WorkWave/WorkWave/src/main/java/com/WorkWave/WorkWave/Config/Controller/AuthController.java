package com.WorkWave.WorkWave.Config.Controller;

import com.WorkWave.WorkWave.Config.JwtProvider;
import com.WorkWave.WorkWave.Model.User;
import com.WorkWave.WorkWave.Repository.UserRepository;
import com.WorkWave.WorkWave.Service.CoustomUserDetainsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CoustomUserDetainsImp coustomUserDetainsImp;


    @PostMapping("/signup")
    public ResponseEntity<?> createuserHandler(@RequestBody User user) throws Exception {
        User isuser = userRepository.findByEmail(user.getEmail());
        if (isuser != null) {
            throw new Exception("Email Already Exists");
        }

        User newuser = new User();
        newuser.setEmail(user.getEmail());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
        newuser.setFullname(user.getFullname());
        User saveduser = userRepository.save(newuser);


//

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(token);
        String jwt = JwtProvider.GenerateToken(token);

        Authresponse authresponse = new Authresponse();
        authresponse.setToken(jwt);
        authresponse.setMessage("new user created");


        return new ResponseEntity<>(authresponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@RequestBody Loginrequest login) throws Exception {
        String username = login.getEmail();
        String password = login.getPassword();


        Authentication authentication = getAuthentication(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = JwtProvider.GenerateToken(authentication);
        Authresponse authresponse = new Authresponse();
        authresponse.setToken(jwt);
        authresponse.setMessage("login successful");

        return new ResponseEntity<>(authresponse, HttpStatus.OK);






    }


    private Authentication getAuthentication(String Username,String password) {
        UserDetails userDetails = coustomUserDetainsImp.loadUserByUsername(Username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }


}
