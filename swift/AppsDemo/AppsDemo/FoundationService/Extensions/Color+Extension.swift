//
//  Color+Extension.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/7.
//

import SwiftUI

public extension Color {
    var rgba: (r: Double, g: Double, b: Double, a: Double) {
        if let components = self.cgColor?.components {
            let r = Double(components[0])
            let g = Double(components[1])
            let b = Double(components[2])
            let a = Double(components[3])

            return (r, g, b, a)
        }
        
        return (0, 0, 0, 1)
    }
    
    init(hexColor: Int) {
        self.init(hexColor: hexColor, alpha: 1.0)
    }
    
    init(hexColor: Int, alpha: Double) {
        let r = Double(((hexColor & 0xFF0000) >> 16)) / 255.0
        let g = Double((hexColor & 0xFF00) >> 8) / 255.0
        let b = Double(hexColor & 0xFF) / 255.0
        self.init(.sRGB, red: r, green: g, blue: b, opacity: alpha)
    }
    
    static func random() -> Color {
        return Color(.sRGB,
                     red: .random(),
                     green: .random(),
                     blue: .random(),
                     opacity: 1)
    }
}

public extension Double {
    static func random() -> Double {
        return Double(arc4random()) / Double(UInt32.max)
    }
}
