//
//  ExtensionView.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/9.
//

import Foundation
import SwiftUI

extension View {
    func hiddenKeyBorderView() -> some View {
        onTapGesture {
            dismissKeyBorder()
        }
    }
    func dismissKeyBorder() {
        UIApplication.shared.sendAction(
            #selector(UIResponder.resignFirstResponder),to: nil,from: nil,for: nil)
    }
    
    func lineBackView(isSelected: Bool,color: Color) -> some View {
        GeometryReader { (proxy)  in
            Path { (path) in
                path.addRect(.init(x: 0, y: proxy.frame(in: .local).height - 1, width: proxy.frame(in: .local).width, height: 1))
            }.fill(LinearGradient(gradient: Gradient(colors: [color]), startPoint: .leading, endPoint: .trailing)).scaleEffect( isSelected ? 1.1 : 1).animation(.easeInOut)
        }
    }
    
}

extension Color {
    
    static func rgbColor(_ rgb : CGFloat) -> Color{
        rgbColor(rgb, g: rgb, b: rgb);
    }
    static func rgbColor(_ r:CGFloat,g:CGFloat,b:CGFloat) -> Color{
        Color(red: Double(r) / 255.0, green: Double(g) / 255.0, blue: Double(b) / 255.0)
    }
    
    static var disableBackColor: Color {
        Color(UIColor.systemGray6)
    }
    static var normalBackColor: Color {
        rgbColor(52, g: 192, b: 94)
    }
    
    static var disableTextColor: Color {
        rgbColor(204)
    }
    static var normalTextColor: Color {
        .white
    }
    
    static var disableLineColor = [rgbColor(224),rgbColor(244)]
    static var normalLineColor = [rgbColor(52, g: 192, b: 94),rgbColor(152, g: 92, b: 94)]
    
}

extension String {
    var localizedValue: String {
        let value = NSLocalizedString(self, comment: self)
        return value
    }
}



