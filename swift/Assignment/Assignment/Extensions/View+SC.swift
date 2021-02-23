//
//  View+SC.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/23.
//

import SwiftUI

extension View {
    public func scTextFiledStyle() -> some View {
        self
            .autocapitalization(.none)
            .overlay(
                Rectangle()
                    .frame(height: 2)
                    .offset(y: 8)
                    .foregroundColor(.scSeparator)
                    .padding(.horizontal, -8),
                alignment: .bottom)
            .padding(10)
    }
}
