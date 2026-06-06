//
//  UserRepositoreis.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/30.
//

import Foundation

protocol UserRepositoryProtocol {
    func Login(user: String, password: String, completion: @escaping (Result<UserInfo, Error>) -> Void)
    func Signup(user: String, password: String, completion: @escaping (Result<UserInfo, Error>) -> Void)
}

/// 数据仓库
/// ```
/// 调用方 : ViewModel
/// 调用   : NetworkService
/// ````
final class UserRepository: UserRepositoryProtocol {
    
    /// 初始化数据服务
    private let netService: NetworkService
    init(service: NetworkService = AccountNetworkService()) {
        self.netService = service
    }
    
    /// 登录
    /// ```
    /// user     :   用户名
    /// password :   密码
    /// ```
    func Login(user: String, password: String, completion: @escaping (Result<UserInfo, Error>) -> Void) {
        /// 调用数据服务获取数据
        netService.Login(user: user, password: password, completion: completion)
    }
    
    /// 注册
    /// ```
    /// user     :   用户名
    /// password :   密码
    /// ```
    func Signup(user: String, password: String, completion: @escaping (Result<UserInfo, Error>) -> Void) {
        /// 调用数据服务获取数据
        netService.SignUp(user: user, password: password, completion: completion)
    }
}
