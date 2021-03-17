//
//  UserModel.swift
//  HSLoginTest
//
//  Created by 伟龙 on 2021/3/10.
//

import Foundation
enum LoginState:String {
    case  login = "login",
          regist = "regist" ,
          logined = "logined"
}

class UserModel:Codable {
    var  userName:String = ""
     var  passWord:String = "" //ignore secury issue
    // dimiss local save data mock
    static func  currentUser() -> UserModel? {
        return nil
    }
}
