//
//  ColorExtension.swift
//  todoDemo
//
//  Created by 朱伟 on 2022/2/11.
//

import SwiftUI

let ColorBackGround = Color(hex:0xffeeeeee)

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
}
