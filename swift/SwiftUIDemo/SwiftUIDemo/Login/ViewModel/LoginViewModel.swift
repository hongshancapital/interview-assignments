//
//  LoginViewModel.swift
//  SwiftUIDemo
//
//  Created by Eric on 9/16/21.
//

import UIKit

class LoginViewModel: NSObject {
    //模拟登录接口
    func login(username:String,password:String,
               result: @escaping (_ success:Bool,_ tip:String)->(Void)) {
        let parameters = ["username":username,"password":password];
        NetWorkManager.shared.request(urlString: LoginUrl, methodType: .POST, parameters: parameters) { responseModel in
             //处理请求返回数据
            if(responseModel.success) {
                if(responseModel.data != nil) {
                    //处理内部的数据
                    //模拟登录
                    for model in CreaterUserArray {
                        if model.username == username {
                            if(model.password == password) {
                                //登录成功
                                CurrentUser = model;
                                result(true,responseModel.msg)
                                return;
                            } else {
                                result(false,"Password error")
                                return;
                            }
                        }
                    }
                    //登录失败
                    CurrentUser = nil
                    responseModel.msg = "Login failed, please create an account first"
                    result(false,responseModel.msg)
                } else {
                    result(false,responseModel.msg)
                }
            } else {
                //登录失败
                result(false,responseModel.msg)
            }
        }
    }
    
}
