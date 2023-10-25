//
//  Global.swift
//  SwiftUIDemo
//
//  Created by Eric on 9/16/21.
//
//存储全局用到的变量
import Foundation
import UIKit
//宏定义
//屏幕宽度
let ScreenWidth = UIScreen.main.bounds.width
//密码最小长度 > 8
let PasswordMinLength:Int = 9
//当前登录的用户信息
var CurrentUser:UserInfo?
//当前注册的用户信息 暂存本地
var CreaterUserArray:[UserInfo] = Array()


//MARK: - 请求地址 -
let SeverUrl  = "https://192.168.1.1:8080/"
//登录地址
let LoginUrl = "login"
//注册地址
let RegisterUrl = "register"
//退出地址
let LogoutUrl = "logout"
