//
//  List+Refresh.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import SwiftUI

@available(iOS 13.0, macOS 10.15, *)
extension List {
    
    public func enableRefresh(_ enable: Bool = true) -> some View {
        modifier(Refresh.Modifier(enable: enable))
    }
}
