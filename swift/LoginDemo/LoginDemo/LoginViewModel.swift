//
//  LoginViewModel.swift
//  LoginDemo
//
//  Created by MC on 2021/4/9.
//

import Foundation
import Combine

enum LoginType {
    case login
    case createAccount
}

class LoginViewModel: ObservableObject {
    @Published var userName: String = ""
    @Published var password: String = ""
    @Published var repeatPassword: String = ""

    @Published var loginBtnEnable = false
    @Published var type: LoginType = .login
    
    @Published var loginSuccessed = false
    
    private var cancellables = Set<AnyCancellable>()
    
    var userNamePublisher: AnyPublisher<String, Never> {
           return $userName
               .receive(on: RunLoop.main)
               .map { value in
                   value
               }
               .eraseToAnyPublisher()
       }
       
       var passwordPublisher: AnyPublisher<String, Never> {
           return $password
               .receive(on: RunLoop.main)
               .map { value in
                   value
               }
               .eraseToAnyPublisher()
       }
    
    var repeatPasswordPublisher: AnyPublisher<String, Never> {
        return $repeatPassword
            .receive(on: RunLoop.main)
            .map { value in
                value
            }
            .eraseToAnyPublisher()
    }
    
    init() {
        Publishers
            .CombineLatest3(userNamePublisher, passwordPublisher, repeatPasswordPublisher)
            .map { v1, v2, v3 -> Bool in
                switch self.type {
                case .login:
                    return !v1.isEmpty && !v2.isEmpty && v2.count >= 8
                case .createAccount:
                    return !v1.isEmpty && !v2.isEmpty && v2.count >= 8 && v3 == v2
                }
            }
            .receive(on: RunLoop.main)
            .assign(to: \.loginBtnEnable, on: self)
            .store(in: &cancellables)
    }
    
    func login() {
        /// 省略了网络加载
        loginSuccessed = true
    }
}
