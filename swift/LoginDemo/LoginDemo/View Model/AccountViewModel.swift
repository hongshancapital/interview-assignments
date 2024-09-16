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
    
    @Published var name_err_tip: String = " "
    @Published var password_err_tip: String = " "
    
    @Published var LoginSucess: Bool = false
    @Published var SignUpSucess: Bool = false
    
    @Published var userInfo: UserInfo = UserInfo()
        
    // 延迟时间 默认1秒
    private var delay = RunLoop.SchedulerTimeType.Stride(1.0)
    private var cancllable = Set<AnyCancellable>()
    
    // 正则表达式 验证用户名长度是否在(3...13)且是否有特殊字符
    // copy from : https://stackoverflow.com/questions/50302874/how-do-you-write-regular-expressions-for-a-valid-username-for-swift
    private static let predicate = NSPredicate(format: "SELF MATCHES %@", "^[a-zA-Z0-9_]{3,13}$")
    
    private let repository: UserRepositoryProtocol
    
    /// ViewModel初始化
    /// ```
    /// style  :  PageStyle.Login  登录页面
    ///           PageStyle.SignUp 注册页面
    ///
    /// Modify at 07.30.2021 @Chr1s78
    /// ```
    init(style: PageStyle, repository: UserRepositoryProtocol = UserRepository()) {
        
        // 初始化数据仓库
        self.repository = repository
        
        // 订阅用户名是否合法，注入到 name_err_tip 字符串
        isUsernameValided
            .dropFirst()
            .receive(on: RunLoop.main)
            .map {
                $0 ? AccountStatus.valid.rawValue : AccountStatus.nameError.rawValue
            }
            .assign(to: \.name_err_tip, on: self)
            .store(in: &cancllable)
        
        // 订阅密码是否合法，注入到 password_err_tip 字符串
        if .Login == style {
            isLoginPasswordValid
                .dropFirst()
                .receive(on: RunLoop.main)
                .map {
                    $0.rawValue
                }
                .assign(to: \.password_err_tip, on: self)
                .store(in: &cancllable)
            
        } else {
            isSignUpPasswordValid
                .dropFirst()
                .receive(on: RunLoop.main)
                .map {
                    $0.rawValue
                }
                .assign(to: \.password_err_tip, on: self)
                .store(in: &cancllable)
        }
        
        // 订阅是否可以点击按钮
        isContinueValid
            .receive(on: RunLoop.main)
            .assign(to: \.valid, on: self)
            .store(in: &cancllable)
    }
}

// Publisher
extension AccountViewModel {
    /// 判断用户名是否有效
    /// ```
    /// 长度应在3与13字符之间
    /// 且不能有特殊字符
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
    /// ```
    /// 如果密码和重复密码有一个为空，返回.repeatWarning
    /// 如果密码相同，返回.valid
    /// 如果密码不同，返回.repeatWrong
    /// Modify at 07.30.2021 @Chr1s78
    /// ```
    private var isPasswordAndRepeatedSame: AnyPublisher<AccountStatus, Never> {
         Publishers.CombineLatest($password, $repeatpwd)
            .debounce(for: delay, scheduler: RunLoop.main)
            .map {
                if $0.count == 0 || $1.count == 0 {
                    return AccountStatus.repeatWarning
                } else if $0 == $1 {
                    return AccountStatus.valid
                } else {
                    return AccountStatus.repeatWrong
                }
            }
            .eraseToAnyPublisher()
    }
    
    /// 判断密码是否都有效
    /// ```
    /// 用于Login页面,username & password
    /// ```
    private var isLoginPasswordValid: AnyPublisher<AccountStatus, Never> {
        
        Publishers.CombineLatest(
            isPasswordEmpty,
            isPasswordStrong)
            .map {
                if  $0 { return AccountStatus.passwordEmpty }
                if !$1 { return AccountStatus.passwordLess }
                return AccountStatus.valid
            }
            .eraseToAnyPublisher()
    }
    
    /// 判断密码是否都有效
    /// ```
    /// 用于Sign up页面,username & password & repeated password
    /// ```
    private var isSignUpPasswordValid: AnyPublisher<AccountStatus, Never> {
        
        Publishers.CombineLatest3(
            isPasswordEmpty,
            isPasswordStrong,
            isPasswordAndRepeatedSame)
            .map {
                if  $0 { return AccountStatus.passwordEmpty }
                if !$1 { return AccountStatus.passwordLess }
                return $2
            }
            .eraseToAnyPublisher()
    }
    
    /// 订阅用户名错误信息
    /// ```
    /// AccountStatus.valid  : 无错误
    /// 其他                  : 错误
    /// ```
    private var isNameTipValided: AnyPublisher<Bool, Never> {
        $name_err_tip
            .map { $0 == AccountStatus.valid.rawValue }
            .eraseToAnyPublisher()
    }
    
    /// 订阅密码错误信息
    /// ```
    /// AccountStatus.valid  : 无错误
    /// 其他                  : 错误
    /// ```
    private var isPasswordTipValided: AnyPublisher<Bool, Never> {
        $password_err_tip
            .map { $0 == AccountStatus.valid.rawValue }
            .eraseToAnyPublisher()
    }
    
    /// 订阅两个错误描述字段
    /// ```
    /// password_err_tip and name_err_tip
    /// both equal "" : true
    /// else          : false
    /// ```
    private var isContinueValid: AnyPublisher<Bool, Never> {
        Publishers.CombineLatest(
            isNameTipValided,
            isPasswordTipValided)
            .map {
                $0 && $1
            }
            .eraseToAnyPublisher()
    }
}

// 数据请求
extension AccountViewModel {
    
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

