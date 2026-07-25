//
//  Font+Extension.swift
//  Demo
//
//  Created by 盼 on 2022/4/15.
//

import SwiftUI

public extension Font {
    static func font(_ ofSize: CGFloat) -> Font {
        return Font.system(size: ofSize, weight: .regular, design: .default)
    }
    
    static func boldFont(_ ofSize: CGFloat) -> Font {
        return Font.system(size: ofSize, weight: .bold, design: .default)
    }
    
    static func mediumFont(_ ofSize: CGFloat) -> Font {
        return Font.system(size: ofSize, weight: .medium, design: .default)
    }
}
