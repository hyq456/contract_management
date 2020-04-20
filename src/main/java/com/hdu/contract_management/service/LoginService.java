package com.hdu.contract_management.service;

import com.hdu.contract_management.utils.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    public ResultUtil logout(HttpServletRequest request, HttpServletResponse response);


}
