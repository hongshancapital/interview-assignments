//
//  NetworkService.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/28.
//

import Foundation

protocol NetworkService {
    func Login(user: String, password: String, completion: @escaping (Bool) -> Void)
    func SignUp(user: String, password: String, completion: @escaping (Bool) -> Void)
}

class AccountNetworkService: NetworkService, ObservableObject {
    
    static let shared = AccountNetworkService()
    @Published var userInfo: UserInfo = UserInfo(name: "", avator: "")
    
    private init() {}
    
    func Login(user: String, password: String, completion: @escaping (Bool) -> Void) {
        /// Http request
        /// .....
        /// get user ifon
        /// ..... then
        self.userInfo = UserInfo(name: "Login name", avator: "person.circle")
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            completion(true)
        }
    }
    func SignUp(user: String, password: String, completion: @escaping (Bool) -> Void) {
        /// Http request
        /// .....
        /// .....
        /// get user ifon
        /// ..... then
        self.userInfo = UserInfo(name: "Signup name", avator: "person.badge.plus")
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            completion(true)
        }
    }
}
