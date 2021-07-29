//
//  NetworkService.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/28.
//

import Foundation

protocol NetworkService {
    func Login(user: String, password: String, completion: @escaping (Result<UserInfo, Error>) -> Void)
    func SignUp(user: String, password: String, completion: @escaping (Result<UserInfo, Error>) -> Void)
}

/// 网络服务
class AccountNetworkService: NetworkService, ObservableObject {
 
    /// 登录网络请求
    func Login(user: String, password: String, completion: @escaping (Result<UserInfo, Error>) -> Void) {
        /// Http request
        /// .....
        /// get user ifon
        /// ..... then
        let userInfo = UserInfo(name: "Login name", avator: "person.circle")
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            completion(.success(userInfo))
        }
    }
    
    /// 注册网络请求
    func SignUp(user: String, password: String, completion: @escaping (Result<UserInfo, Error>) -> Void) {
        /// Http request
        /// .....
        /// .....
        /// get user ifon
        /// ..... then
        let userInfo = UserInfo(name: "Signup name", avator: "person.badge.plus")
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            completion(.success(userInfo))
        }
    }
}
