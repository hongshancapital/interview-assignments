//
//  ColorExtension.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import SwiftUI

extension Color {
    static let c0099FF = 0x0099FF.color
    static let cEAF6FE = 0xEAF6FE.color
    static let c000A11 = 0x000A11.color
    static let c31A8F7 = 0x31A8F7.color
}

extension Int {
  public var color: Color {
    let red = CGFloat(self >> 16 & 0xff) / 255
    let green = CGFloat(self >> 8 & 0xff) / 255
    let blue  = CGFloat(self & 0xff) / 255
    return Color(red: red, green: green, blue: blue, opacity: 1)
  }
}
