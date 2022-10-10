//
//  STAlertAction.swift
//  Insight
//
//  Created by song on 2019/7/1.
//  Copyright © 2019年 ST. All rights reserved.
//

import UIKit

public extension UIAlertAction {
    /// 取属性列表
    static var propertyNames: [String] {
        var outCount: UInt32 = 0
        guard let ivars = class_copyIvarList(self, &outCount) else {
            return []
        }
        var result = [String]()
        let count = Int(outCount)
        for i in 0..<count {
            let ivar = ivars[Int(i)]
            if let key = ivar_getName(ivar) {
                let name = String(cString: key)
                result.append(name)
            }
        }
        return result
    }
    
    /// 是否存在某个属性
    func isPropertyExisted(_ propertyName: String) -> Bool {
        for name in UIAlertAction.propertyNames {
            if name == propertyName {
                return true
            }
        }
        return false
    }
    
    /// 设置文字颜色
    func setTextColor(_ color: UIColor) {
        let key = "_titleTextColor"
        guard isPropertyExisted(key) else {
            return
        }
        self.setValue(color, forKey: key)
    }
}
