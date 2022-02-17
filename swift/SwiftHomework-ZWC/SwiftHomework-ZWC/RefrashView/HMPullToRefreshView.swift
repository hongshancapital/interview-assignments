//
//  HMPullToRefreshView.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import SwiftUI

struct PullToRefreshView<Header, Content, Footer> {
    private let header: Header
    private let footer: Footer
    @Binding var isHaveMoreData: Bool
    private let content: () -> Content
    
    @Environment(\.headerRefreshData) private var headerRefreshData
    @Environment(\.footerRefreshData) private var footerRefreshData
}

extension PullToRefreshView: View where Header: View, Content: View, Footer: View {
    
    init(header: Header, footer: Footer, isHaveMoreData: Binding<Bool>, @ViewBuilder content: @escaping () -> Content) {
        self.header = header
        self.footer = footer
        self.content = content
        _isHaveMoreData = isHaveMoreData
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
            if isHaveMoreData {
                return footerRefreshData.progress
            } else {
                return 1
            }
        }
        return 1.0
    }
    
    var dynamicHeaderPadding: CGFloat {
        -headerRefreshData.threshold
    }
    
    var dynamicFooterPadding: CGFloat {
        if isHaveMoreData {
            return (footerRefreshData.refreshState == .loading) ? 0.0 : -footerRefreshData.threshold
        } else {
            return 0
        }
    }
}
