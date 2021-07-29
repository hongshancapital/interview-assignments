//
//  AccountViewModel.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/27.
//

import Foundation
import SwiftUI
import Combine

final class AccountViewModel: ObservableObject {
    
    @Published var name: String = ""
    @Published var password: String = ""
    @Published var repeatpwd: String = ""
    @Published var valid: Bool = false
    @Published var tip: String = " "
    
    @Published var LoginSucess: Bool = false
    @Published var SignUpSucess: Bool = false
    
    @Published var userInfo: UserInfo = UserInfo()
        
    // 延迟时间 默认0.5秒
    private var delay = RunLoop.SchedulerTimeType.Stride(0.5)
    private var cancllable = Set<AnyCancellable>()
    
    /// 正则表达式 验证用户名长度是否在(3...13)且是否有特殊字符
    /// copy from : https://stackoverflow.com/questions/50302874/how-do-you-write-regular-expressions-for-a-valid-username-for-swift
    private static let predicate = NSPredicate(format: "SELF MATCHES %@", "^[a-zA-Z0-9_]{3,13}$")
    
    private let repository: UserRepositoryProtocol
    
    /// ViewModel初始化
    /// ```
    /// style  :  PageStyle.Login  登录页面
    ///           PageStyle.SignUp 注册页面
    /// ```
    init(style: PageStyle, repository: UserRepositoryProtocol = UserRepository()) {
        
        /// 初始化数据仓库
        self.repository = repository
        
        /// 订阅
        if .Login == style {
            isLoginAccountValid
                .dropFirst()
                .receive(on: RunLoop.main)
                .map {
                    $0.rawValue
                }
                .assign(to: \.tip, on: self)
                .store(in: &cancllable)
            
        } else {
            isSignUpAccountValid
                .dropFirst()
                .receive(on: RunLoop.main)
                .map {
                    $0.rawValue
                }
                .assign(to: \.tip, on: self)
                .store(in: &cancllable)
        }
        
        isPageButtonValided
            .receive(on: RunLoop.main)
            .assign(to: \.valid, on: self)
            .store(in: &cancllable)
    }
    
    /// 判断用户名长度是否有效
    /// ```
    /// 长度应在3与13字符之间
    /// ```
    private var isUsernameValided: AnyPublisher<Bool, Never> {
        $name
            .debounce(for: delay, scheduler: RunLoop.main)
            .removeDuplicates()
            .map {
                Self.predicate.evaluate(with: $0)
            }
            .eraseToAnyPublisher()
    }
    
    /// 判断密码是否为空
    private var isPasswordEmpty: AnyPublisher<Bool, Never> {
         $password
            .debounce(for: delay, scheduler: RunLoop.main)
            .removeDuplicates()
            .map {
                $0.isEmpty
            }
            .eraseToAnyPublisher()
    }
    
    /// 判断密码长度是否大于8
    private var isPasswordStrong: AnyPublisher<Bool, Never> {
         $password
            .debounce(for: delay, scheduler: RunLoop.main)
            .removeDuplicates()
            .map { $0.count > 8 }
            .eraseToAnyPublisher()
    }
    
    /// 判断密码和重复密码是否相同
    private var isPasswordAndRepeatedSame: AnyPublisher<Bool, Never> {
         Publishers.CombineLatest($password, $repeatpwd)
            .debounce(for: delay, scheduler: RunLoop.main)
            .map { $0 == $1 }
            .eraseToAnyPublisher()
    }
    
    /// 判断用户名和密码是否都有效
    /// ```
    /// 用于Login页面,username & password
    /// ```
    private var isLoginAccountValid: AnyPublisher<AccountStatus, Never> {
        
        Publishers.CombineLatest3(
            isUsernameValided,
            isPasswordEmpty,
            isPasswordStrong)
            .map {
                if !$0 { return AccountStatus.nameError }
                if  $1 { return AccountStatus.passwordEmpty }
                if !$2 { return AccountStatus.passwordLess }
                return AccountStatus.valid
            }
            .eraseToAnyPublisher()
    }
    
    /// 判断用户名和密码是否都有效
    /// ```
    /// 用于Sign up页面,username & password & repeated password
    /// ```
    private var isSignUpAccountValid: AnyPublisher<AccountStatus, Never> {
        
        Publishers.CombineLatest4(
            isUsernameValided,
            isPasswordEmpty,
            isPasswordStrong,
            isPasswordAndRepeatedSame)
            .map {
                if !$0 { return AccountStatus.nameError }
                if  $1 { return AccountStatus.passwordEmpty }
                if !$2 { return AccountStatus.passwordLess }
                if !$3 { return AccountStatus.repeatWrong }
                return AccountStatus.valid
            }
            .eraseToAnyPublisher()
    }
    
    /// 获得页面按钮是否有效标志
    /// ```
    /// true  : 有效
    /// false : 无效
    /// ```
    private var isPageButtonValided: AnyPublisher<Bool, Never> {
        $tip
            .map { $0 == AccountStatus.valid.rawValue }
            .eraseToAnyPublisher()
    }
    
    /// 登录请求
    func Login(user: String, password: String) {
        repository.Login(user: user, password: password, completion: { result in
            switch result {
            case .success(let data):
                self.userInfo = data
                self.LoginSucess = true
            case .failure(let error):
                #if DEBUG
                print(error.localizedDescription)
                #endif
            }
        })
    }
    
    /// 注册请求
    func Signup(user: String, password: String) {
        repository.Signup(user: user, password: password, completion: { result in
            switch result {
            case .success(let data):
                self.userInfo = data
                self.SignUpSucess = true
            case .failure(let error):
                #if DEBUG
                print(error.localizedDescription)
                #endif
            }
        })
    }
}

