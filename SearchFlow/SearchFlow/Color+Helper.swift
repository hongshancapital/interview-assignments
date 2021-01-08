//
//  Color+Helper.swift
//  SearchFlow
//
//  Created by evan on 2021/1/6.
//

import SwiftUI

extension Color {
    init(hex: Int, alpha: Double = 1) {
        let components = (
            R: Double((hex >> 16) & 0xff) / 255,
            G: Double((hex >> 08) & 0xff) / 255,
            B: Double((hex >> 00) & 0xff) / 255
        )
        self.init(
            .sRGB,
            red: components.R,
            green: components.G,
            blue: components.B,
            opacity: alpha
        )
    }
    
    static var offWhite: Color { return Color(hex: 0xF2F2F7) }
    static var lightestGray: Color { return Color(hex: 0xF0F0F0) }
    static var lighterGray: Color { return Color(hex: 0xF4F4F4) }
    static var lightGray: Color { return Color(hex: 0xE9E9E9) }
    static var darkGray: Color { return Color(hex: 0x999999) }
    static var darkerGray: Color { return Color(hex: 0x666666) }
    static var darkestGray: Color { return Color(hex: 0x333333) }
}
