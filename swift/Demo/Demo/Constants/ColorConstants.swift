//
//  ColorConstants.swift
//  Demo
//
//  Created by 杨立鹏 on 2022/3/30.
//

import SwiftUI

extension Color {
    /// 背景色
    static let bgColor = Color(red: 244, green: 244, blue: 247)!
}

extension Color {
    init?(red: Int, green: Int, blue: Int, opacity: CGFloat = 1) {
        guard red >= 0 && red <= 255 else { return nil }
        guard green >= 0 && green <= 255 else { return nil }
        guard blue >= 0 && blue <= 255 else { return nil }

        var trans = opacity
        if trans < 0 { trans = 0 }
        if trans > 1 { trans = 1 }

        self.init(.displayP3, red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, opacity: trans)
    }

    init?(hex: Int, opacity: CGFloat = 1) {
        var trans = opacity
        if trans < 0 { trans = 0 }
        if trans > 1 { trans = 1 }

        let red = (hex >> 16) & 0xFF
        let green = (hex >> 8) & 0xFF
        let blue = hex & 0xFF
        self.init(red: red, green: green, blue: blue, opacity: trans)
    }

    init?(hexString: String, opacity: CGFloat = 1) {
        var string = ""
        if hexString.lowercased().hasPrefix("0x") {
            string = hexString.replacingOccurrences(of: "0x", with: "")
        } else if hexString.hasPrefix("#") {
            string = hexString.replacingOccurrences(of: "#", with: "")
        } else {
            string = hexString
        }

        if string.count == 3 {
            var str = ""
            string.forEach { str.append(String(repeating: String($0), count: 2)) }
            string = str
        }

        guard let hexValue = Int(string, radix: 16) else { return nil }

        self.init(hex: hexValue, opacity: opacity)
    }
}
