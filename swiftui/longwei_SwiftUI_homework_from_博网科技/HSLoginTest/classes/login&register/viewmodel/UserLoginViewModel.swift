//
//  UserStateViewModel.swift
//  HSLoginTest
//
//  Created by 伟龙 on 2021/3/10
//

import Foundation
import  Combine
import  SwiftUI
class UserLoginViewModel: ObservableObject {
    var  resultCallBack:((_ result:ApiResult) -> Void)?=nil
    // Input
    @Published var userName = ""
    @Published var passWord = ""
    // OutPut
    @Published  var userNameTipMessge:String = ""
    @Published  var  userPassWordTipMessage:String = ""
    @Published var isValid = false
    @Published  var  displayText:String = "Login"
    @Published  var  alterMessage:(Bool,String) = (false,"")
    private var cancellableSet: Set<AnyCancellable> = []
    @Published  var  loading:Bool = false
    private var passwordValidPublisher: AnyPublisher<Bool, Never> {
        $passWord
            .debounce(for: 0.8, scheduler: RunLoop.main)
            .removeDuplicates()
            .map { password in
                return password.count >= 8
            }
            .eraseToAnyPublisher()
    }
    private var userNameValidPublisher: AnyPublisher<Bool, Never> {
        $userName
            .debounce(for: 0.8, scheduler: RunLoop.main)
            .removeDuplicates()
            .map { username in
                return username.count >= 4
            }
            .eraseToAnyPublisher()
    }
    private var userNamePasswordPublisher: AnyPublisher<Bool, Never> {
        Publishers
            .CombineLatest(userNameValidPublisher, passwordValidPublisher)
            .map { (userNameIsValid, passwordIsValid) -> Bool in
                userNameIsValid && passwordIsValid
            }
            .eraseToAnyPublisher()
    }
    
    init() {
        userNameValidPublisher
            .receive(on: RunLoop.main)
            .map { valid in
                valid ? "" : "User name must at least have 4 characters"
            }
            .assign(to: \.userNameTipMessge, on: self)
            .store(in: &cancellableSet)
        passwordValidPublisher
            .receive(on: RunLoop.main)
            .map { passvalid -> String in
                return passvalid == false ? "password must at least have 8 characters" : ""
            }
            .assign(to: \.userPassWordTipMessage, on: self)
            .store(in: &cancellableSet)
        userNamePasswordPublisher
            .receive(on: RunLoop.main)
            .assign(to: \.isValid, on: self)
            .store(in: &cancellableSet)
    }
    // pass  securure check and  encrypt safe data ... there  passed
    func checklogin() {
        if (userName.lowercased() == "lotawei" &&  passWord == "12345678" ){
            self.loading = true
            self.alterMessage.0 = false
            DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                let  userModel =  UserModel.init()
                userModel.userName = self.userName
                userModel.passWord = self.passWord
                let res = ApiResult.init(result: userModel)
                self.resultCallBack?(res)
                self.loading = false
            }
        }else{
            self.alterMessage.0 = true
            self.alterMessage.1 = "The user name and password do not match"
        }
    }

}


