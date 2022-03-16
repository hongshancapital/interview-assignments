//
//  Cardfiy.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/9.
//

import SwiftUI

/// 将 View 卡片化
struct CardModifier: ViewModifier {
    var cornerRadius: CGFloat = preferredCornerRadius
    var backgroundColor: Color = .white

    func body(content: Content) -> some View {
        ZStack {
            RoundedRectangle(cornerRadius: cornerRadius)
                .fill(backgroundColor)
            content
        }
    }
}

extension View {
    /// 卡片化 View 的便利方法
    func cardify(cornerRadius: CGFloat = preferredCornerRadius,
                 backgroundColor: Color = .white) -> some View {
        self.modifier(CardModifier(cornerRadius: cornerRadius,
                              backgroundColor: backgroundColor))
    }
}
