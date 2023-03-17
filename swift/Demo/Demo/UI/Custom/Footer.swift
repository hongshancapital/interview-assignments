//
//  Footer.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/16.
//

import SwiftUI

/// 从开源组件中抽出来的 ScrollView 上拉 footer
/// https://github.com/wxxsw/Refresh
public struct Footer<Label>: View where Label: View {
    
    let action: () -> Void
    let label: () -> Label
    
    @Binding var refreshing: Bool
    
    private var noMore: Bool = false
    private var preloadOffset: CGFloat = 0

    public init(refreshing: Binding<Bool>, action: @escaping () -> Void, @ViewBuilder label: @escaping () -> Label) {
        self.action = action
        self.label = label
        self._refreshing = refreshing
    }
    
    @Environment(\.refreshFooterUpdate) var update
    
    public func noMore(_ noMore: Bool) -> Self {
        var view = self
        view.noMore = noMore
        return view
    }
    
    public func preload(offset: CGFloat) -> Self {
        var view = self
        view.preloadOffset = offset
        return view
    }
    
    public var body: some View {
        if !noMore, update.refresh, !refreshing {
            DispatchQueue.main.async {
                self.refreshing = true
                self.action()
            }
        }
        
        return Group {
            if update.enable {
                VStack(alignment: .center, spacing: 0) {
                    if refreshing || noMore {
                        label()
                    } else {
                        EmptyView()
                    }
                    Spacer()
                }
                .frame(maxWidth: .infinity)
            } else {
                EmptyView()
            }
        }
        .listRowInsets(.init(top: 0, leading: 0, bottom: 0, trailing: 0))
        .anchorPreference(key: FooterAnchorKey.self, value: .bounds) {
            if self.noMore || self.refreshing {
                return []
            } else {
                return [FooterAnchorKey.Item(bounds: $0, preloadOffset: self.preloadOffset, refreshing: self.refreshing)]
            }
        }
    }
}

extension EnvironmentValues {
    
    var refreshFooterUpdate: FooterUpdateKey.Value {
        get { self[FooterUpdateKey.self] }
        set { self[FooterUpdateKey.self] = newValue }
    }
}

extension FooterUpdateKey: EnvironmentKey {
    
    struct Value: Equatable {
        let enable: Bool
        var refresh: Bool = false
    }
}

struct FooterAnchorKey: PreferenceKey {
    typealias Value = [Item]

    struct Item {
        let bounds: Anchor<CGRect>
        let preloadOffset: CGFloat
        let refreshing: Bool
    }
    
    static var defaultValue: Value = []
    
    static func reduce(value: inout Value, nextValue: () -> Value) {
        value.append(contentsOf: nextValue())
    }
}

struct FooterUpdateKey {
    static var defaultValue: Value = .init(enable: false)
}


struct FooterModifier: ViewModifier {
    let isEnabled: Bool
    
    @State private var id: Int = 0
//    @State private var headerUpdate: HeaderUpdateKey.Value
    @State private var headerPadding: CGFloat = 0
    @State private var headerPreviousProgress: CGFloat = 0
    
    @State private var footerUpdate: FooterUpdateKey.Value
    @State private var footerPreviousRefreshAt: Date?
    
    init(enable: Bool) {
        isEnabled = enable
//        _headerUpdate = State(initialValue: .init(enable: enable))
        _footerUpdate = State(initialValue: .init(enable: enable))
    }
    
    @Environment(\.defaultMinListRowHeight) var rowHeight
    
    func body(content: Content) -> some View {
        return GeometryReader { proxy in
            content
                .environment(\.refreshFooterUpdate, self.footerUpdate)
                .padding(.top, self.headerPadding)
                .clipped(proxy.safeAreaInsets == .zero)
                .backgroundPreferenceValue(FooterAnchorKey.self) { v -> Color in
                    DispatchQueue.main.async { self.update(proxy: proxy, value: v) }
                    return Color.clear
                }
                .id(self.id)
        }
    }
 
    func update(proxy: GeometryProxy, value: FooterAnchorKey.Value) {
        guard let item = value.first else { return }
        
        let bounds = proxy[item.bounds]
        var update = footerUpdate
        
        if bounds.minY <= rowHeight || bounds.minY <= bounds.height {
            update.refresh = false
        } else if update.refresh && !item.refreshing {
            update.refresh = false
        } else {
            update.refresh = proxy.size.height - bounds.minY + item.preloadOffset > 0
        }
        
        if update.refresh, !footerUpdate.refresh {
            if let date = footerPreviousRefreshAt, Date().timeIntervalSince(date) < 0.1 {
                update.refresh = false
            }
            footerPreviousRefreshAt = Date()
        }
        
        footerUpdate = update
    }
}

extension View {
    
    func clipped(_ value: Bool) -> some View {
        if value {
            return AnyView(self.clipped())
        } else {
            return AnyView(self)
        }
    }
}

extension EdgeInsets {
    
    static var zero: EdgeInsets {
        .init(top: 0, leading: 0, bottom: 0, trailing: 0)
    }
}
