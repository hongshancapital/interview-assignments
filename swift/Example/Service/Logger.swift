//
//  Logger.swift
//  Example
//
//  Created by 聂高涛 on 2022/3/2.
//

import UIKit

class Logger {
    static let isDebug = false
    
    class func print(_ items: Any) {
        if isDebug == false {return}
        Swift.print(items)
    }
}
