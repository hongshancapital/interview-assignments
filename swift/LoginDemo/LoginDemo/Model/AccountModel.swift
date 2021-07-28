//
//  AccountModel.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/27.
//

import Foundation

enum AccountStatus: String {
    case nameError = "用户名不合法"
    case passwordEmpty = "密码不能为空"
    case passwordLess = "密码不能小于8位"
    case repeatWrong = "两次密码不相同"
    case valid = ""
}

enum PageStyle {
    case Login
    case Signup
}

enum TextFieldType {
    case text
    case password
}

enum ButtonStyle: String {
    case Login = "Login"
    case Signup = "Create Account"
}

struct UserInfo: Codable {
    var name: String
    var avator: String
    ///
    ///....
}
