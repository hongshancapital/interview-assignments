//
//  Color+Extension.swift
//  Demo
//
//  Created by 盼 on 2022/4/15.
//

import SwiftUI

public extension Color {
    static var borderColor: Color {
        return Color(hex: 0xF1F1F1)
    }
    
    static var bgColor: Color {
        return Color(hex: 0xF5F5F5)
    }
    
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
}
