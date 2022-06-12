//
//  SwiftUIView.swift
//  ListProject
//
//  Created by shencong on 2022/6/9.
//

import SwiftUI

///颜色设置 R、G、B  A
public func RGBA(_ red: Int, _ green: Int, _ blue: Int, _ alpha: Double) -> Color {
    return Color(.sRGB, red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, opacity: alpha)
}
///颜色设置 R、G、B
public func RGB(_ red: Int, _ green: Int, _ blue: Int) -> Color {
    return Color(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0)
}
///颜色设置 R=G=B
public func RGB(_ value: Int) -> Color {
    return RGB(value, value, value)
}
///颜色设置 R=G=B  A
public func RGBA(_ value: Int, _ a: Double) -> Color {
    return RGBA(value, value, value, a)
}
///十六进制颜色设置
func HexColor(rgbValue: UInt) -> Color {
   return Color (
       red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
       green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
       blue: CGFloat(rgbValue & 0x0000FF) / 255.0
   )
}


