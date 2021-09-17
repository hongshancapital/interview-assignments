//
//  RegisterViewModel.swift
//  SwiftUIDemo
//
//  Created by Eric on 9/16/21.
//

import UIKit

class RegisterViewModel: NSObject {
    //创建账号
    func registerAccount(username:String,password:String,
                       result: @escaping (_ success:Bool,_ tip:String)->(Void)) {
        let parameters = ["username":username,"password":password];
        NetWorkManager.shared.request(urlString: RegisterUrl, methodType: .POST, parameters: parameters) { responseModel in
            //模拟创建账号
            let info = UserInfo()
            info.username = username
            info.password = password
            for model in CreaterUserArray {
                if model.username == username {
                     //已存在账号
                    result(false,"Account already exists!")
                    return;
                }
            }
            CreaterUserArray.append(info)
            result(true,"Account created successfully!")
        }
    }
}
