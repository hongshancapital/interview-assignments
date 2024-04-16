//
//  Utilities.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import SwiftUI

@available(iOS 13.0, macOS 10.15, *)
extension View {
    
    func clipped(_ value: Bool) -> some View {
        if value {
            return AnyView(self.clipped())
        } else {
            return AnyView(self)
        }
    }
}

@available(iOS 13.0, macOS 10.15, *)
extension EdgeInsets {
    
    static var zero: EdgeInsets {
        .init(top: 0, leading: 0, bottom: 0, trailing: 0)
    }
}
