//
//  ColoExtension.swift
//  Demo
//
//  Created by Kai on 2022/2/16.
//

import SwiftUI

extension Color {
   
    public static func rgba(_ r: Double, _ g: Double, _ b: Double, _ a: Double = 1) -> Color {
        return Color(.sRGB, red: r / 255.0, green: g / 255.0, blue: b / 255.0, opacity: a)
    }
}

extension UIColor {
    convenience init(_ r: Int, _ g: Int, _ b: Int) {
        assert(r >= 0 && r <= 255, "Invalid red component")
        assert(g >= 0 && g <= 255, "Invalid green component")
        assert(b >= 0 && b <= 255, "Invalid blue component")
        
        self.init(red: CGFloat(r) / 255.0, green: CGFloat(g) / 255.0, blue: CGFloat(b) / 255.0, alpha: 1.0)
    }
}
