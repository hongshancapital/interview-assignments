//
//  AccountCreationViewModel.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import Foundation
import Combine

class AccountCreationViewModel: ObservableObject {
    
    // Input
    @Published var username = ""
    @Published var password = ""
    @Published var passwordAgain = ""
    var dismiss: (() -> Void)?
    
    // Output
    @Published var isValid = false
    @Published var isLoading = false
    lazy var onCreateAccount: () -> Void = { [unowned self] in
        self.createAccount()
    }
    
    private var cancellableSet = Set<AnyCancellable>()
    
    init() {
        isValidFormPublisher
            .receive(on: RunLoop.main)
            .assign(to: \.isValid, on: self)
            .store(in: &cancellableSet)
    }
    
    private var isValidFormPublisher: AnyPublisher<Bool, Never> {
        Publishers.CombineLatest3(isValidUsernamePublisher, isValidPasswordPublisher, arePasswordsEqualPublisher)
            .map { isValidUsername, isValidPassword, arePasswordsEqual in
                return isValidUsername && isValidPassword && arePasswordsEqual
            }
            .eraseToAnyPublisher()
    }
    
    private var isValidUsernamePublisher: AnyPublisher<Bool, Never> {
        $username
            .map { username in
                return Validator.validate(username: username)
            }
            .eraseToAnyPublisher()
    }
    
    private var isValidPasswordPublisher: AnyPublisher<Bool, Never> {
        $password
            .map { password in
                return Validator.validate(password: password)
            }
            .eraseToAnyPublisher()
    }
    
    private var arePasswordsEqualPublisher: AnyPublisher<Bool, Never> {
        Publishers.CombineLatest($password, $passwordAgain)
            .map { username, password in
                return username == password
            }
            .eraseToAnyPublisher()
    }
    
    func createAccount() {
        self.isLoading = true
        Server.default.createAccount(username: self.username, password: self.password)
            .receive(on: RunLoop.main)
            .sink { [weak self] (completion) in
                guard let self = self else {
                    return
                }
                self.isLoading = false
            } receiveValue: { [weak self] user in
                appState.createdUser = user
                self?.dismiss?()
            }
            .store(in: &self.cancellableSet)
    }
}
