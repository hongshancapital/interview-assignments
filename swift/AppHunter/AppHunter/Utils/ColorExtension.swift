//
//  HomeView.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Foundation
import SwiftUI
extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let rgbA, rgbR, rgbG, rgbB: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (rgbA, rgbR, rgbG, rgbB) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (rgbA, rgbR, rgbG, rgbB) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (rgbA, rgbR, rgbG, rgbB) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (rgbA, rgbR, rgbG, rgbB) = (1, 1, 1, 0)
        }

        self.init(
            .sRGB,
            red: Double(rgbR) / 255,
            green: Double(rgbG) / 255,
            blue: Double(rgbB) / 255,
            opacity: Double(rgbA) / 255
        )
    }
}

extension Color {
    init(hex: UInt, alpha: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255,
            green: Double((hex >> 08) & 0xFF) / 255,
            blue: Double((hex >> 00) & 0xFF) / 255,
            opacity: alpha
        )
    }
}
