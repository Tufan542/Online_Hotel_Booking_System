package com.airbnb.controller;


import com.airbnb.dto.AppUserDto;
import com.airbnb.dto.JWTToken;
import com.airbnb.dto.LoginDto;
import com.airbnb.entity.AppUser;
import com.airbnb.service.AppUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AppUserServiceImpl appUserServiceImpl;
    public AuthController(AppUserServiceImpl appUserServiceImpl){

        this.appUserServiceImpl=appUserServiceImpl;
    }
    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(
           @Valid @RequestBody AppUserDto appUserDto,
           BindingResult result
    )
    {
        appUserDto.setRole("ROLE_USER");
        if(result.hasErrors())
        {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        AppUserDto appUser = appUserServiceImpl.createAppUser(appUserDto);
        return new ResponseEntity<>(appUser, HttpStatus.CREATED);
    }

    @PostMapping("/createpropertyowner")
    public ResponseEntity<?> createPropertyOwner(
            @Valid
            @RequestBody AppUserDto appUserDto,
            BindingResult result
    )
    {
        if(result.hasErrors())
        {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        appUserDto.setRole("ROLE_OWNER");
        AppUserDto appUser = appUserServiceImpl.createPropertyOwnerDetails(appUserDto);
        return new ResponseEntity<>(appUser, HttpStatus.CREATED);
    }
    //this part should be accessible  by admin only
    @PostMapping("/createpropertymanager")
    public ResponseEntity<?> createPropertyManager(
            @Valid
            @RequestBody AppUserDto appUserDto, BindingResult result
    )
    {
        if(result.hasErrors())
        {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        appUserDto.setRole("ROLE_MANAGER");
        AppUserDto appUser = appUserServiceImpl.createPropertyManagerDetails(appUserDto);
        return new ResponseEntity<>(appUser, HttpStatus.CREATED);
    }
//    @PostMapping("/login")
//    public ResponseEntity<String> signIn(
//            @RequestBody LoginDto loginDto
//    ){
//        boolean status = appUserServiceImpl.verifyLogin(loginDto);
//        if(status)
//        {
//            return new ResponseEntity<>("Successful",HttpStatus.OK);
//        }
//        else
//        {
//            return new ResponseEntity<>("Invalid username/password",HttpStatus.UNAUTHORIZED);
//        }
//    }

            @PostMapping("/login")
            public ResponseEntity<?> signIn(
            @RequestBody LoginDto loginDto
            ){
                String token = appUserServiceImpl.verifyLoginByUsernameOrEmail(loginDto);
                JWTToken jwtToken = new JWTToken();

                if(token != null)
                {
                    jwtToken.setTokenType("JWT");
                    jwtToken.setToken(token);
                    return new ResponseEntity<>(jwtToken ,HttpStatus.OK);
                }
                else
                {
                    return new ResponseEntity<>("Invalid username/password",HttpStatus.UNAUTHORIZED);
                }
            }
        @PutMapping("/updateuser")
        public ResponseEntity<AppUserDto> updateUser(
                @RequestBody AppUserDto appUserDto ,
                @RequestParam long id)
        {
            AppUserDto appUserDto1 = appUserServiceImpl.updateAppUser(appUserDto, id);
            return new ResponseEntity<>(appUserDto1 ,HttpStatus.OK);
        }

}