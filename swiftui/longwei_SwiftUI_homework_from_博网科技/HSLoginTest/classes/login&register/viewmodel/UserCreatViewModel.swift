//
//  UserCreatViewModel.swift
//  HSLoginTest
//
//  Created by 伟龙 on 2021/3/10.
//

import Foundation
import Combine
import SwiftUI
enum PassCheckResult:String {
    case  emptyPassword = "password is empty",
          passwordNotEqual = "password is not equal to confirm password",
          passwordSmallLength = "password must hava at least 8 characters ",
          checkValided = ""
}
class UserCreatViewModel: ObservableObject {
    var  resultcallback:((_ result:ApiResult) -> Void)?=nil
    @Published var registUserName = ""
    @Published var registPassword = ""
    @Published var passwordConfirm = ""
    @Published var  tipMessage = ""
    private var registUsernamePublished: AnyPublisher<Bool, Never> {
        $registUserName
            .debounce(for: 0.8, scheduler: RunLoop.main)
            .removeDuplicates()
            .map { username in
                return username.count >= 4
            }
            .eraseToAnyPublisher()
    }
    private var registPasswordPublished: AnyPublisher<Bool, Never> {
        $registPassword
            .debounce(for: 0.8, scheduler: RunLoop.main)
            .removeDuplicates()
            .map { pwd in
                 pwd.count >= 8
            }
            .eraseToAnyPublisher()
    }
    private var passwordConfirmPublisher: AnyPublisher<Bool, Never> {
        Publishers.CombineLatest($registPassword, $passwordConfirm)
            .debounce(for: 0.2, scheduler: RunLoop.main)
            .map { password, passwordAgain in
                 password == passwordAgain
            }
            .eraseToAnyPublisher()
    }
   
    private var isPasswordEmptyPublisher: AnyPublisher<Bool, Never> {
      $registPassword
        .debounce(for: 0.8, scheduler: RunLoop.main)
        .removeDuplicates()
        .map { password in
          return password == ""
        }
        .eraseToAnyPublisher()
    }
    private var isPasswordValidPublisher: AnyPublisher<PassCheckResult, Never> {
        Publishers.CombineLatest3(isPasswordEmptyPublisher, passwordConfirmPublisher, registPasswordPublished)
          .map { passwordIsEmpty, passwordsAreEqual, passwordIsStrongEnough in
            if (passwordIsEmpty) {
                return PassCheckResult.emptyPassword
            }
            else if (!passwordsAreEqual) {
              return PassCheckResult.passwordNotEqual
            }
            else if (!passwordIsStrongEnough) {
              return PassCheckResult.passwordSmallLength
            }
            else {
              return .checkValided
            }
          }
          .eraseToAnyPublisher()
    }
    private var isFormValidPublisher: AnyPublisher<Bool, Never> {
        Publishers.CombineLatest(registUsernamePublished, isPasswordValidPublisher)
            .map { userNameIsValid, passwordIsValid in
                return userNameIsValid && passwordIsValid == .checkValided
            }
            .eraseToAnyPublisher()
    }
    private var cancellableSet: Set<AnyCancellable> = []
    @Published var createUserValid = false
    @Published  var  alterMessage:(Bool,String) = (false,"")
    @Published  var  loading:Bool = false
    init() {
        registUsernamePublished
            .receive(on: RunLoop.main)
            .map { valid in
                valid ? "" : "User name must at least have 4 characters"
            }
            .assign(to: \.tipMessage, on: self)
            .store(in: &cancellableSet)
        registPasswordPublished
            .receive(on: RunLoop.main)
            .map{
                valid  in
                valid ? "" :  PassCheckResult.passwordSmallLength.rawValue
            }
            .assign(to: \.tipMessage, on: self)
            .store(in: &cancellableSet)
        isPasswordValidPublisher
          .receive(on: RunLoop.main)
          .map { passwordCheck in
                
            return passwordCheck.rawValue
          }
          .assign(to: \.tipMessage, on: self)
          .store(in: &cancellableSet)

        isFormValidPublisher
            .receive(on: RunLoop.main)
            .map{
                valid in
              return valid
            }
            .assign(to: \.createUserValid, on: self)
            .store(in: &cancellableSet)
    }
    func checkregist() {
        if self.createUserValid {
            self.loading = true
            DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                let  userModel =  UserModel.init()
                userModel.userName = self.registUserName
                userModel.passWord = self.registPassword
                let res = ApiResult.init(result: userModel)
                self.resultcallback?(res)
                self.loading = false
            }
        }else{
            self.alterMessage.0 = true
            self.alterMessage.1 = "Can not resgist"
        }
    }
    
}
