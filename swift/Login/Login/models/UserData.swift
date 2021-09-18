//
//  UserData.swift
//  Login
//
//  Created by xiwang wang on 2021/9/1.
//

import Foundation
import Combine

final class UserData: ObservableObject {
    @Published var login = loginData
    @Published var register = registerData
}
