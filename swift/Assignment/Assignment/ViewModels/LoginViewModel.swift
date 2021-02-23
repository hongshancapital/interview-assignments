//
//  LoginViewModel.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import Foundation
import Combine

class LoginViewModel: ObservableObject {
    // Input
    @Published var username = ""
    @Published var password = ""
    
    // Output
    @Published var isValid = false
    @Published var isLoading = false
    lazy var onLogin = { [unowned self] in
        self.login()
    }
    lazy var onAppear = {
        self.appear()
    }
    
    private var cancellableSet = Set<AnyCancellable>()
    
    init() {
        isValidFormPublisher
            .receive(on: RunLoop.main)
            .assign(to: \.isValid, on: self)
            .store(in: &cancellableSet)
    }
    
    private var isValidFormPublisher: AnyPublisher<Bool, Never> {
        Publishers.CombineLatest(isValidUsernamePublisher, isValidPasswordPublisher)
            .map { isUsernameValid, isPasswordValid in
                return isUsernameValid && isPasswordValid
            }
            .eraseToAnyPublisher()
    }
    
    private var isValidUsernamePublisher: AnyPublisher<Bool, Never> {
        $username
            .removeDuplicates()
            .map { username in
                return Validator.validate(username: username)
            }
            .eraseToAnyPublisher()
    }
    
    private var isValidPasswordPublisher: AnyPublisher<Bool, Never> {
        $password
            .removeDuplicates()
            .map { password in
                return Validator.validate(password: password)
            }
            .eraseToAnyPublisher()
    }
    
    func login() {
        self.isLoading = true
        Server.default.login(username: self.username, password: self.password)
            .receive(on: RunLoop.main)
            .sink { [weak self] (completion) in
                guard let self = self else {
                    return
                }
                self.isLoading = false
                switch completion {
                case .finished: break
                case .failure(let error):
                    print(error)
                }
            } receiveValue: { user in
                appState.currentUser = user
            }
            .store(in: &self.cancellableSet)
    }
    
    func appear() {
        if let user = appState.createdUser {
            self.username = user.name
            self.password = user.password
        }
    }
    
}
