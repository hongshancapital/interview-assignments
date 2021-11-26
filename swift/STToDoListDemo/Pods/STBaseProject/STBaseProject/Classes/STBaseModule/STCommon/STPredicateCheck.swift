//
//  STPredicateCheck.swift
//  STBaseProject
//
//  Created by stack on 2018/06/12.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

public class STPredicateCheck: NSObject {
    /// 匹配用户密码是否包含大写字母
    ///
    /// - Parameter password: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkCapitalPassword(password: String) -> Bool {
        let pattern = ".*[A-Z].*$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: password)
        return isMatch
    }
    
    /// 匹配用户密码是否包含小写字母
    ///
    /// - Parameter password: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkLowercasePassword(password: String) -> Bool {
        let pattern = ".*[a-z].*$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: password)
        return isMatch
    }

    /// 匹配用户密码是否包含数字
    ///
    /// - Parameter password: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkNumberPassword(password: String) -> Bool {
        let pattern = ".*[0-9].*$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: password)
        return isMatch
    }
    
    /// 匹配用户密码：8-32位数字+大写字母+小写字母组合
    ///
    /// - Parameter password: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkPassword(password: String) -> Bool {
        let pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: password)
        return isMatch
    }

    /// 匹配用户姓名： 1-32位的中文或英文或数字
    ///
    /// - Parameter userName: 输入用户名
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkUserName(userName: String) -> Bool {
        return STPredicateCheck.st_checkUserName(userName: userName, hasSpace: false)
    }
    
    /// 匹配用户姓名： 1-32位的中文或英文或数字或空格
    ///
    /// - Parameter userName: 输入用户名
    /// - Parameter hasSpace: 是否包含空格
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkUserName(userName: String, hasSpace: Bool) -> Bool {
        var pattern = "[\u{4e00}-\u{9fa5}a-zA-Z0-9]{1,32}"
        if hasSpace {
            pattern = "[\u{4e00}-\u{9fa5}a-zA-Z0-9\\s]{1,32}"
        }
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: userName)
        return isMatch
    }

    /// 匹配邮箱
    ///
    /// - Parameter email: 输入邮箱
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkEmail(email: String) -> Bool {
        let pattern = "^([a-zA-Z0-9]+([._\\-])*[a-zA-Z0-9]*)+@([a-zA-Z0-9])+(.([a-zA-Z])+)+$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: email)
        return isMatch
    }
    
    /// 匹配手机号
    ///
    /// - Parameter phoneNum: 输入手机号
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkPhoneNum(phoneNum: String) -> Bool {
        let pattern = "^1(3[0-9]|4[56789]|5[0-9]|6[6]|7[0-9]|8[0-9]|9[189])\\d{8}$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: phoneNum)
        return isMatch
    }
    
    /// 匹配是数字
    ///
    /// - Parameter text: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkIsDigit(text: String) -> Bool {
        let pattern = "^[0-9]*$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: text)
        return isMatch
    }
    
    /// 匹配整数
    ///
    /// - Parameter text: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkIsInteger(text: String) -> Bool {
        let pattern = "^-?[1-9]\\d*|0$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: text)
        return isMatch
    }
    
    /// 匹配浮点数
    ///
    /// - Parameter text: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkIsFloat(text: String) -> Bool {
        let pattern = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: text)
        return isMatch
    }
    
    /// 中英文标点
    ///
    /// - Parameter text: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkPunctuation(text: String) -> Bool {
        let pattern = "^[\\p{P}]*$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: text)
        return isMatch
    }
    
    /// utf-8编码中的所有中文字符
    ///
    /// - Parameter text: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_checkChinaChar(text: String) -> Bool {
        let pattern = "^[\\p{Han}]*$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: text)
        return isMatch
    }
    
    /// 中文、数字、字母、标点符号
    ///
    /// - Parameter text: 输入字符串
    ///
    /// - Returns: 返回匹配结果
    public class func st_normalWithPunctuation(text: String) -> Bool {
        let pattern = "^([\\p{Han}\\p{P}A-Za-z0-9])*$"
        let pred = NSPredicate.init(format: "SELF MATCHES %@", pattern)
        let isMatch = pred.evaluate(with: text)
        return isMatch
    }
}
