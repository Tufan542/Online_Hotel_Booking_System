package com.airbnb.service;

import com.airbnb.dto.AppUserDto;
import com.airbnb.dto.LoginDto;

public interface AppUserService {
    public AppUserDto createAppUser(AppUserDto appUserDto);
   // public boolean verifyLogin(LoginDto loginDto);

    public String verifyLoginByUsernameOrEmail(LoginDto loginDto);

    public AppUserDto createPropertyOwnerDetails(AppUserDto appUserDto);
    public AppUserDto createPropertyManagerDetails(AppUserDto appUserDto);

    public AppUserDto updateAppUser(AppUserDto appUserDto, long id);

}
