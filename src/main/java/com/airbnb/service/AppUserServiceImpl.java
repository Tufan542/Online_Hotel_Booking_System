package com.airbnb.service;

import com.airbnb.dto.AppUserDto;
import com.airbnb.dto.LoginDto;
import com.airbnb.entity.AppUser;
import com.airbnb.exception.UserExists;
import com.airbnb.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{
@Autowired
    private  AppUserRepository appUserRepository;
    private JWTService jwtService;
    public AppUserServiceImpl(AppUserRepository appUserRepository, JWTService jwtService)
    {
        this.appUserRepository=appUserRepository;
        this.jwtService = jwtService;
    }


    AppUser mapToEntity(AppUserDto appUserDto)
    {
        AppUser user = new AppUser();
        user.setName(appUserDto.getName());
        user.setEmail(appUserDto.getEmail());
        user.setUsername(appUserDto.getUsername());
        user.setPassword(appUserDto.getPassword());
        user.setRole(appUserDto.getRole());
        return user;
    }
    AppUserDto mapToDto(AppUser appUser)
    {
     AppUserDto appUserDto = new AppUserDto();
     appUserDto.setId(appUser.getId());
     appUserDto.setName(appUser.getName());
     appUserDto.setEmail(appUser.getEmail());
     appUserDto.setUsername(appUser.getUsername());
     appUserDto.setPassword(appUser.getPassword());
     appUserDto.setRole(appUser.getRole());
     return appUserDto;
    }

    @Override
    public AppUserDto createAppUser(AppUserDto appUserDto) {
        AppUser appUser = mapToEntity(appUserDto);
        Optional<AppUser> opEmail = appUserRepository.findByEmail(appUser.getEmail());
        if(opEmail.isPresent()){
            throw new UserExists("Email ID already exists.");
        }
        Optional<AppUser> opUsername = appUserRepository.findByUsername(appUser.getUsername());
        if(opUsername.isPresent()){
            throw new UserExists("Username exists.");
        }
        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(10));
        appUser.setPassword(hashpw);//password going to database will be encoded password
//        appUser.setRole("ROLE_USER");
        AppUser save = appUserRepository.save(appUser);
        AppUserDto saveddto = mapToDto(save);
        return saveddto;
    }



//    @Override
//    public boolean verifyLogin(LoginDto loginDto) {
//        Optional<AppUser> opUser =
//                appUserRepository.findByUsername(loginDto.getUsername());
//
//        if(opUser.isPresent())//this will help us to find the data present in db with this username
//        {
//            AppUser appuser = opUser.get();
//            return BCrypt.checkpw(loginDto.getPassword(), appuser.getPassword());
//        }
//        return false;
//    }

    @Override
    public String verifyLoginByUsernameOrEmail(LoginDto loginDto) {
        Optional<AppUser> opUser = appUserRepository.findByEmailOrUsername(loginDto.getUsername(),loginDto.getUsername());//because in json in same entity either we write email or username
        if(opUser.isPresent())
        {
            AppUser appUser = opUser.get();
            if( BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword()))
            {
              return   jwtService.generateToken(appUser);
            }
        }
        return null;
    }

    @Override
    public AppUserDto createPropertyOwnerDetails(AppUserDto appUserDto) {
        AppUser appUser = mapToEntity(appUserDto);
        Optional<AppUser> opEmail = appUserRepository.findByEmail(appUser.getEmail());
        if(opEmail.isPresent()){
            throw new UserExists("Email ID already exists.");
        }
        Optional<AppUser> opUsername = appUserRepository.findByUsername(appUser.getUsername());
        if(opUsername.isPresent()){
            throw new UserExists("Username exists.");
        }
        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(10));
        appUser.setPassword(hashpw);//password going to database will be encoded password
//        appUser.setRole("ROLE_OWNER");
        AppUser save = appUserRepository.save(appUser);
        AppUserDto saveddto = mapToDto(save);
        return saveddto;
    }

    @Override
    public AppUserDto createPropertyManagerDetails(AppUserDto appUserDto) {
        AppUser appUser = mapToEntity(appUserDto);
        Optional<AppUser> opEmail = appUserRepository.findByEmail(appUser.getEmail());
        if(opEmail.isPresent()){
            throw new UserExists("Email ID already exists.");
        }
        Optional<AppUser> opUsername = appUserRepository.findByUsername(appUser.getUsername());
        if(opUsername.isPresent()){
            throw new UserExists("Username exists.");
        }
        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(10));
        appUser.setPassword(hashpw);//password going to database will be encoded password
//        appUser.setRole("ROLE_MANAGER");
        AppUser save = appUserRepository.save(appUser);
        AppUserDto saveddto = mapToDto(save);
        return saveddto;
    }

    @Override
    public AppUserDto updateAppUser(AppUserDto appUserDto, long id) {
        String hashpw = BCrypt.hashpw(appUserDto.getPassword(),BCrypt.gensalt(10));
        AppUser appUser = appUserRepository.findById(id).get();
        appUser.setName(appUserDto.getName());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setUsername(appUserDto.getUsername());
//        if(appUserDto.getRole() == null) {
//            throw new IllegalArgumentException("Role must not be null");
//        }
//        appUser.setRole(appUserDto.getRole());


        appUser.setPassword(hashpw);
        AppUser save = appUserRepository.save(appUser);
        AppUserDto saveddto = mapToDto(save);
        return saveddto;
    }


}
