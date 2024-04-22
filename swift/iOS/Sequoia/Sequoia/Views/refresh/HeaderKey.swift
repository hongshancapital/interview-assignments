//
//  HeaderKey.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import SwiftUI

@available(iOS 13.0, macOS 10.15, *)
extension EnvironmentValues {
    
    var refreshHeaderUpdate: Refresh.HeaderUpdateKey.Value {
        get { self[Refresh.HeaderUpdateKey.self] }
        set { self[Refresh.HeaderUpdateKey.self] = newValue }
    }
}

@available(iOS 13.0, macOS 10.15, *)
extension Refresh {
    
    struct HeaderAnchorKey {
        static var defaultValue: Value = []
    }
    
    struct HeaderUpdateKey {
        static var defaultValue: Value = .init(enable: false)
    }
}

@available(iOS 13.0, macOS 10.15, *)
extension Refresh.HeaderAnchorKey: PreferenceKey {
    
    typealias Value = [Item]
    
    struct Item {
        let bounds: Anchor<CGRect>
        let refreshing: Bool
    }
    
    static func reduce(value: inout Value, nextValue: () -> Value) {
        value.append(contentsOf: nextValue())
    }
}

@available(iOS 13.0, macOS 10.15, *)
extension Refresh.HeaderUpdateKey: EnvironmentKey {
    
    struct Value {
        let enable: Bool
        var progress: CGFloat = 0
        var refresh: Bool = false
    }
}
