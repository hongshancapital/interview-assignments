//
//  User.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import Foundation

struct User: Codable {
    var name: String
    var password: String
    var isAnonymous = true
    
    static let `default` = User(name: "Sequoia", password: "12345678")
}
