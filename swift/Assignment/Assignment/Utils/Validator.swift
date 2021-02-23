//
//  Validator.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import Foundation

class Validator {
    
    public static func validate(username: String?) -> Bool {
        guard let uname = username else {
            return false
        }
        
        return uname.count > 0
    }
    
    public static func validate(password: String?) -> Bool {
        guard let psd = password else {
            return false
        }
        
        return psd.count >= 8
    }

}
