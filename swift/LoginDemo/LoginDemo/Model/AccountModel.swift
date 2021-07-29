//
//  AccountModel.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/27.
//

import Foundation

/// 错误类型
enum AccountStatus: String {
    case nameError = "用户名不合法"
    case passwordEmpty = "密码不能为空"
    case passwordLess = "密码不能小于8位"
    case repeatWrong = "两次密码不相同"
    case valid = ""
}

/// 页面类型
enum PageStyle {
    case Login
    case Signup
}

/// TextField类型
enum TextFieldType {
    case text
    case password
}

/// Button类型
enum ButtonStyle: String {
    case Login = "Login"
    case Signup = "Create Account"
}

/// 数据模型
struct UserInfo: Codable {
    var name: String = ""
    var avator: String = ""
    ///
    ///....
}
