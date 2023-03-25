//
//  DataVerificationManger.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/9.
//

import Foundation
// 数据校验管理

struct DataVerificationManger {
    
    static func isValidUser(userName: String) -> Bool {
        
        // 用户名 必须大于1位  并且只能是数字 或大小写字母 或下划线 或邮箱形式
        let regex = "[a-zA-Z0-9_@\\.-]{2,40}"
        let predictor = NSPredicate(format: "SELF MATCHES %@", regex)
        
        return predictor.evaluate(with: userName)
        
    }
    
    static func isVaildPassWord(password: String) -> Bool {
        // 密码 必须大于8位  并且只能是数字 和大小写字母
        let regex = "[a-zA-Z0-9]{9,12}"
        let predictor = NSPredicate(format: "SELF MATCHES %@", regex)
        
        
        return predictor.evaluate(with: password)
    }
}
