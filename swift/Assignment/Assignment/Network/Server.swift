//
//  Server.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import Foundation
import Combine

enum ServerError: Error {
    case usernameOrPasswordIncorrect(code: Int, desc: String)
    case accountExist(code: Int, desc: String)
}

public class Server {
    
    static let `default` = Server()
    
    func login(username: String, password: String) -> AnyPublisher<User, ServerError> {
        return
            Future<User, ServerError> { promise in
                DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                    let user = User(name: username, password: password, isAnonymous: false)
                    let result = APIResult<User>(code: 0, msg: "loggedin successfully.", data: user)
                    promise(.success(result.data!))
                }
            }
            .eraseToAnyPublisher()
    }
    
    func logout() -> AnyPublisher<Void, ServerError> {
        return
            Future<Void, ServerError> { promise in
                DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                    promise(.success(()))
                }
            }
            .eraseToAnyPublisher()
    }
    
    func createAccount(username: String, password: String) -> AnyPublisher<User, ServerError> {
        return
            Future<User, ServerError> { promise in
                DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                    let user = User(name: username, password: password, isAnonymous: false)
                    let result = APIResult<User>(code: 0, msg: "created account successfully.", data: user)
                    promise(.success(result.data!))
                }
            }
            .eraseToAnyPublisher()
    }

}
