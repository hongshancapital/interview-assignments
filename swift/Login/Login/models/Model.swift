//
//  Models.swift
//  Login
//
//  Created by xiwang wang on 2021/9/1.
//

import Foundation


struct Register: Hashable, Codable {
    var code: String
    var msg: String
    var stime: String
    var data: User
    
    struct User: Hashable, Codable {
        var userName: String
        var uid: String
    }
}

struct Login: Hashable, Codable {
    var code: String
    var msg: String
    var stime: String
    
}






