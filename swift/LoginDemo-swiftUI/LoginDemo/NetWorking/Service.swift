import Foundation
import Combine

protocol LoginService {

    func createAccount(login: UserModel) -> AnyPublisher<Bool,Never>

    func loginAccount(login: UserModel) -> AnyPublisher<Bool,Never>
}

struct Service: LoginService {
    func createAccount(login: UserModel) -> AnyPublisher<Bool, Never> {
        let cache = UserCache()
        let users = cache.searchUser(login.userName)
        guard (users != nil) else {
            cache.addUser(login)
            return  Just(true).eraseToAnyPublisher()
        }
        return  Just(false).eraseToAnyPublisher()
    }
    
    func loginAccount(login: UserModel) -> AnyPublisher<Bool, Never> {
        let cache = UserCache()
        let user = cache.searchUser(login.userName)
        guard (user != nil) else {
            /// user name does not exist
            return  Just(false).eraseToAnyPublisher()
        }
        guard user?.passWord == login.passWord else {
            /// Wrong user name and password
            return  Just(false).eraseToAnyPublisher()
        }
        return  Just(true).eraseToAnyPublisher()
    }
}
