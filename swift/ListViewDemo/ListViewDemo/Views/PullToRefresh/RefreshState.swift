//
//  RefreshState.swift
//  ListViewDemo
//
//  Created by sky on 2022/9/30.
//

import SwiftUI

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

enum RefreshState: Int {
    case invalid    // 无效
    case stopped    // 停止
    case triggered  // 触发
    case loading    // 加载
    case noMore     // 没有更多了
}

struct RefreshData {
    var thresold: CGFloat = 0
    var progress: Double = 0
    var refreshState: RefreshState = .invalid
}


class ListState: ObservableObject {
    @Published private(set) var noMore: Bool
    
    init() {
        noMore = false
    }
    
    func setNoMore(_ newNoMore: Bool) {
        noMore = newNoMore
    }
}
