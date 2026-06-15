//
//  PullToRefresh.swift
//  interview-assignments
//
//  Created by Pedro Pei on 2022/5/23.
//

import SwiftUI

struct PullToRefreshView<Header, Content, Footer> {
    private let header: Header
    private let footer: Footer
    
    private let content: () -> Content
    
    @Environment(\.headerRefreshData) private var headerRefreshData
    @Environment(\.footerRefreshData) private var footerRefreshData
}

extension PullToRefreshView: View where Header: View, Content: View, Footer: View {
    
    init(header: Header, footer: Footer, @ViewBuilder content: @escaping () -> Content) {
        self.header = header
        self.footer = footer
        self.content = content
    }
    
    var body: some View {
        VStack(spacing: 0) {
            header
                .opacity(dynamicHeaderOpacity)
                .frame(maxWidth: .infinity)
                .anchorPreference(key: HeaderBoundsPreferenceKey.self, value: .bounds, transform: {
                    [.init(bounds: $0)]
                })
            content()
                .anchorPreference(key: FooterBoundsPreferenceKey.self, value: .bounds, transform: {
                    [.init(bounds: $0)]
                })
            footer
                .opacity(dynamicFooterOpacity)
                .frame(maxWidth: .infinity)
                .anchorPreference(key: FooterBoundsPreferenceKey.self, value: .bounds, transform: {
                    [.init(bounds: $0)]
                })
        }
        .padding(.top, dynamicHeaderPadding)
        .padding(.bottom, dynamicFooterPadding)
    }
    
    var dynamicHeaderOpacity: Double {
        if headerRefreshData.refreshState == .invalid {
            return 0.0
        }
        if headerRefreshData.refreshState == .stopped {
            return headerRefreshData.progress
        }
        return 1.0
    }
    
    var dynamicFooterOpacity: Double {
        if footerRefreshData.refreshState == .invalid {
            return 0.0
        }
        if footerRefreshData.refreshState == .stopped {
            return footerRefreshData.progress
        }
        return 1.0
    }
    
    var dynamicHeaderPadding: CGFloat {
        return (headerRefreshData.refreshState == .loading) ? 0.0 : -headerRefreshData.thresold
    }
    
    var dynamicFooterPadding: CGFloat {
        return (footerRefreshData.refreshState == .loading) ? 0.0 : -footerRefreshData.thresold
    }
}

extension PullToRefreshView where Header: View, Content: View, Footer == EmptyView {
    init(header: Header, @ViewBuilder content: @escaping () -> Content) {
        self.init(header: header, footer: EmptyView(), content: content)
    }
}

extension PullToRefreshView where Header == EmptyView, Content: View, Footer: View {
    init(footer: Footer, @ViewBuilder content: @escaping () -> Content) {
        self.init(header: EmptyView(), footer: footer, content: content)
    }
}

extension PullToRefreshView where Header == RefreshDefaultHeader, Content: View, Footer == RefreshDefaultFooter {
    init(@ViewBuilder content: @escaping () -> Content) {
        self.init(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter(), content: content)
    }
}
