//
//  RoundCornerPath.swift
//  HSLoginTest
//
//  Created by 伟龙 on 2021/3/10.
//

import Foundation
import SwiftUI
//custom path 
struct RoundCornerPath: Shape {
    var radius: CGFloat
    var corners: UIRectCorner
    func path(in rect: CGRect) -> Path {
        let path = UIBezierPath(roundedRect: rect, byRoundingCorners: corners, cornerRadii: CGSize(width: radius, height: radius))
        return Path(path.cgPath)
    }
}
