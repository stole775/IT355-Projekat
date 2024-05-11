package com.it355.MladenStolicProjekat.authentication;

import com.it355.MladenStolicProjekat.dto.LoginResponse;
import com.it355.MladenStolicProjekat.dto.LoginDTO;
import com.it355.MladenStolicProjekat.entity.Admin;
import com.it355.MladenStolicProjekat.jwt.JwtService;
import com.it355.MladenStolicProjekat.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final AdminService userRepository;

    private  final JwtService jwtService;

    private final TokenBlackListService tokenBlackListService;

    private final PasswordEncoder passwordEncoder;




    public LoginResponse register(Admin request){
        var user = new Admin();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        String token = jwtService.generateToken(user, generateExtraClaims(user));
        return  new LoginResponse(token);
    }




    public LoginResponse login(LoginDTO authenticationRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
        );
        authenticationManager.authenticate(authToken);
        Admin user = userRepository.findByUsername(authenticationRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new LoginResponse(jwt);
    }

    public void logout(String token) {
        tokenBlackListService.blacklistToken(token);
        SecurityContextHolder.clearContext();
    }


    private Map<String, Object> generateExtraClaims(Admin user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("ID_Admina" , user.getId());
        extraClaims.put("Username" , user.getUsername());
       // extraClaims.put("Role" , user.getRole() );
        return extraClaims;
    }
}
