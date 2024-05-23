//
//  HMRefreshState.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import SwiftUI

// MARK: - Preferences
struct HeaderBoundsPreferenceKey: PreferenceKey {
    struct Item {
        let bounds: Anchor<CGRect>
    }
    static var defaultValue: [Item] = []
    
    static func reduce(value: inout [Item], nextValue: () -> [Item]) {
        value.append(contentsOf: nextValue())
    }
}

struct FooterBoundsPreferenceKey: PreferenceKey {
    struct Item {
        let bounds: Anchor<CGRect>
    }
    static var defaultValue: [Item] = []
    
    static func reduce(value: inout [Item], nextValue: () -> [Item]) {
        value.append(contentsOf: nextValue())
    }
}

// MARK: - Environment
struct HeaderRefreshDataKey: EnvironmentKey {
    static var defaultValue: RefreshData = .init()
}

struct FooterRefreshDataKey: EnvironmentKey {
    static var defaultValue: RefreshData = .init()
}

extension EnvironmentValues {
    var headerRefreshData: RefreshData {
        get { self[HeaderRefreshDataKey.self] }
        set { self[HeaderRefreshDataKey.self] = newValue }
    }
    
    var footerRefreshData: RefreshData {
        get { self[FooterRefreshDataKey.self] }
        set { self[FooterRefreshDataKey.self] = newValue }
    }
}

// MARK: - Refresh State Data
enum RefreshState: Int {
    case invalid
    case stopped
    case triggered
    case loading
}

struct RefreshData {
    var threshold: CGFloat = 0
    var progress: Double = 0
    var refreshState: RefreshState = .invalid
}
