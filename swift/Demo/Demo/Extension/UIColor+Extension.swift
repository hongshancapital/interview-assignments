//
//  UIColor+Extension.swift
//  Demo
//
//  Created by 盼 on 2022/4/15.
//

import Foundation
import UIKit

public extension UIColor {
    static var bgColor: UIColor {
        return UIColor(hexInt: 0xF5F5F5)
    }
    
    
    /// 十六进制字符串 转颜色
    /// - Parameter hexString: 十六进制字符串
    /// - 必须是 #204282 或者 204282 类似字符串
    convenience init(hex: String) {
        let string = hex.uppercased()
        let scanner = Scanner(string: string)
        if hex.hasPrefix("#") {
            scanner.scanLocation = 1
        }
        
        var color: UInt32 = 0
        scanner.scanHexInt32(&color)
        
        let red = CGFloat((color & 0xFF0000) >> 16) / 255.0
        let green = CGFloat((color & 0x00FF00) >> 8) / 255.0
        let blue = CGFloat(color & 0x0000FF) / 255.0
        
        self.init(red: red, green: green, blue: blue, alpha: 1)
    }
    
    /// 十六进制数 转颜色
    /// - Parameter hexInt: 十六进制数
    convenience init(hexInt: Int) {
        let r = CGFloat(((hexInt & 0x00FF0000) >> 16)) / 255.0
        let g = CGFloat(((hexInt & 0x0000FF00) >> 8)) / 255.0
        let b = CGFloat(hexInt & 0x000000FF) / 255.0
        
        self.init(red: r, green: g, blue: b, alpha: 1)
    }
}
