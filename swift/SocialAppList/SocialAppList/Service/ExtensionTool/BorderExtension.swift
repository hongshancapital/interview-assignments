//
//  BorderExtension.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import SwiftUI

@available(iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0, *)
extension View {
    func border<S>(_ content: S, cornerRadius: CGFloat, width: CGFloat = 1) -> some View where S : ShapeStyle {
        overlay(content: {
            RoundedRectangle(cornerRadius: cornerRadius)
                .stroke(content, lineWidth: width)
        })
    }
}

struct Corners: View {
    var color: Color = .black
    var tl: CGFloat = 0.0
    var tr: CGFloat = 0.0
    var bl: CGFloat = 0.0
    var br: CGFloat = 0.0

    var body: some View {
        GeometryReader { geometry in
            Path { path in
                let w = geometry.size.width
                let h = geometry.size.height

                let tr = min(min(tr, h/2), w/2)
                let tl = min(min(tl, h/2), w/2)
                let bl = min(min(bl, h/2), w/2)
                let br = min(min(br, h/2), w/2)

                path.move(to: CGPoint(x: w / 2.0, y: 0))
                path.addLine(to: CGPoint(x: w - tr, y: 0))
                path.addArc(center: CGPoint(x: w - tr, y: tr), radius: tr, startAngle: Angle(degrees: -90), endAngle: Angle(degrees: 0), clockwise: false)
                path.addLine(to: CGPoint(x: w, y: h - br))
                path.addArc(center: CGPoint(x: w - br, y: h - br), radius: br, startAngle: Angle(degrees: 0), endAngle: Angle(degrees: 90), clockwise: false)
                path.addLine(to: CGPoint(x: bl, y: h))
                path.addArc(center: CGPoint(x: bl, y: h - bl), radius: bl, startAngle: Angle(degrees: 90), endAngle: Angle(degrees: 180), clockwise: false)
                path.addLine(to: CGPoint(x: 0, y: tl))
                path.addArc(center: CGPoint(x: tl, y: tl), radius: tl, startAngle: Angle(degrees: 180), endAngle: Angle(degrees: 270), clockwise: false)
            }
            .fill(color)
        }
    }
}

extension Corners {
    static func top(_ top: CGFloat, color: Color) -> some View {
        Corners(color: color, tl: top, tr: top)
    }
    
    static func bottom(_ bottom: CGFloat, color: Color) -> some View {
        Corners(color: color, bl: bottom, br: bottom)
    }
    
    static func left(_ left: CGFloat, color: Color) -> some View {
        Corners(color: color, tl: left, bl: left)
    }
    
    static func right(_ right: CGFloat, color: Color) -> some View {
        Corners(color: color, tr: right, br: right)
    }
    
    static func all(_ value: CGFloat, color: Color) -> some View {
        Corners(color: color, tl: value, tr: value, bl: value, br: value)
    }
    
    static func none(color: Color) -> some View {
        Corners.all(0, color: color)
    }
}
