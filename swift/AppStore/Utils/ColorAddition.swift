//
//  ColorAddition.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import Foundation
import SwiftUI

extension UIColor {
    static func aHexColor(ahex: UInt) -> UIColor {
        var red, green, blue, alpha: UInt
        blue = ahex & 0x000000FF
        green = ((ahex & 0x0000FF00) >> 8)
        red = ((ahex & 0x00FF0000) >> 16)

        alpha = ((ahex & 0xFF000000) >> 24)

        let color = UIColor(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: CGFloat(alpha) / 255.0)
        return color
    }

    static let background = UIColor(named: "background")!
    static let border = UIColor(named: "border")!
}

extension Color {
    static func aHexColor(ahex: UInt) -> Color {
        let cgColor = UIColor.aHexColor(ahex: ahex).cgColor
        return Color(cgColor)
    }

    static let background: Color = Color(UIColor.background.cgColor)
    static let border: Color = Color(UIColor.border.cgColor)
}
