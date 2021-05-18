import Foundation
import Combine

protocol LoginService {

    func userRegistration(login: LoginModel) -> AnyPublisher<Bool, Never>

    func userLogin(login: LoginModel) -> AnyPublisher<Bool, Never>
}

struct Service: LoginService {
    func userLogin(login: LoginModel) -> AnyPublisher<Bool, Never> {
        let userName = UserDefaults.standard.string(forKey: "UserName")
        let userPassword = UserDefaults.standard.string(forKey: "UserPassword")
        guard login.name == userName, login.password == userPassword  else {
            return  Just(false).eraseToAnyPublisher()
        }
        return  Just(true).eraseToAnyPublisher()
    }

    func userRegistration(login: LoginModel) -> AnyPublisher<Bool, Never> {
        guard login.password.count > 8 else {
            return  Just(false).eraseToAnyPublisher()
        }

        UserDefaults.standard.set(login.name, forKey: "UserName")
        UserDefaults.standard.set(login.password, forKey: "UserPassword")
        return  Just(true).eraseToAnyPublisher()
    }
}
