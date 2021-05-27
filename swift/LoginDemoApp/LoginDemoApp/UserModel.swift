//
//  UserAuth.swift
//  DemoApp
//
//  Created by kim on 2021/5/26.
//
import Combine

class UserModel: ObservableObject {
    @Published var isLogin = false
    @Published var userName = ""

    func logout() {
        self.isLogin = false
        self.userName = ""
    }
    
    func login(_ userName: String, _ password: String) {
        self.isLogin = true
        self.userName = userName
    }
}
