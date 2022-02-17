//
//  HMRefreshViewModifier.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/17.
//

import SwiftUI

extension View {
    func addPullToRefresh(isHeaderRefreshing: Binding<Bool>?, onHeaderRefresh: (() -> Void)?, isHaveMoreData: Binding<Bool>?) -> some View {
        addPullToRefresh(isHeaderRefreshing: isHeaderRefreshing, onHeaderRefresh: onHeaderRefresh, isFooterRefreshing: nil, onFooterRefresh: nil,isHaveMoreData: isHaveMoreData)
    }
    
    func addPullToRefresh(isFooterRefreshing: Binding<Bool>?, onFooterRefresh: (() -> Void)?, isHaveMoreData: Binding<Bool>?) -> some View {
        addPullToRefresh(isHeaderRefreshing: nil, onHeaderRefresh: nil, isFooterRefreshing: isFooterRefreshing, onFooterRefresh: onFooterRefresh,isHaveMoreData: isHaveMoreData)
    }
    
    func addPullToRefresh(isHeaderRefreshing: Binding<Bool>?, onHeaderRefresh: (() -> Void)?, isFooterRefreshing: Binding<Bool>?, onFooterRefresh: (() -> Void)?, isHaveMoreData: Binding<Bool>?) -> some View {
        modifier(PullToRefreshModifier(isHeaderRefreshing: isHeaderRefreshing, isFooterRefreshing: isFooterRefreshing, isHaveMoreData: isHaveMoreData, onHeaderRefresh: onHeaderRefresh, onFooterRefresh: onFooterRefresh))
    }
}

struct PullToRefreshModifier: ViewModifier {
    @Binding var isHeaderRefreshing: Bool
    @Binding var isFooterRefreshing: Bool
    @Binding var isHaveMoreData: Bool
    
    let onHeaderRefresh: (() -> Void)?
    let onFooterRefresh: (() -> Void)?
    
    init(isHeaderRefreshing: Binding<Bool>?, isFooterRefreshing: Binding<Bool>?, isHaveMoreData: Binding<Bool>?, onHeaderRefresh: (() -> Void)?, onFooterRefresh: (() -> Void)?) {
        _isHeaderRefreshing = isHeaderRefreshing ?? .constant(false)
        _isFooterRefreshing = isFooterRefreshing ?? .constant(false)
        _isHaveMoreData = isHaveMoreData ?? .constant(false)
        self.onHeaderRefresh = onHeaderRefresh
        self.onFooterRefresh = onFooterRefresh
    }
    
    @State private var headerRefreshData = RefreshData()
    @State private var footerRefreshData = RefreshData()
    
    func body(content: Content) -> some View {
        GeometryReader { proxy in
            content
                .environment(\.headerRefreshData, headerRefreshData)
                .environment(\.footerRefreshData, footerRefreshData)
                .onChange(of: isHeaderRefreshing, perform: { value in
                    if !value {
                        self.headerRefreshData.refreshState = .stopped
                    }
                })
                .onChange(of: isFooterRefreshing, perform: { value in
                    if !value {
                        self.footerRefreshData.refreshState = .stopped
                    }
                })
                .onAppear {
                    self.headerRefreshData.refreshState = .loading
                    self.headerRefreshData.progress = 1.0
                    self.isHeaderRefreshing = true
                    self.onHeaderRefresh?()
                }
                .backgroundPreferenceValue(HeaderBoundsPreferenceKey.self) { value -> Color in
                    DispatchQueue.main.async {
                        calculateHeaderRefreshState(proxy, value: value)
                    }
                    return Color.clear
                }
                
                .backgroundPreferenceValue(FooterBoundsPreferenceKey.self) { value -> Color in
                    DispatchQueue.main.async {
                        calculateFooterRefreshState(proxy, value: value)
                    }
                    return Color.clear
                }
        }
    }
}

extension PullToRefreshModifier {
    private func calculateHeaderRefreshState(_ proxy: GeometryProxy, value: [HeaderBoundsPreferenceKey.Item]) {
        guard let bounds = value.first?.bounds else {
            return
        }
        
        // caculate state
        guard headerRefreshData.refreshState != .loading else {
            return
        }
        
        let headerFrame = proxy[bounds] // we need geometry proxy to get real frame
        
        let y = headerFrame.minY
        let threshold = headerFrame.height
        let topDistance: CGFloat = 30.0
        
        if threshold != headerRefreshData.threshold {
            headerRefreshData.threshold = threshold
        }
        
        if -y == headerRefreshData.threshold && headerFrame.width == proxy.size.width && headerRefreshData.refreshState == .invalid {
            headerRefreshData.refreshState = .stopped
        }
        
        var contentOffset = y + threshold
        
        if contentOffset == 0 {
            headerRefreshData.progress = 0.0
        }
        
        guard contentOffset > topDistance else {
            return
        }
        
        contentOffset -= topDistance
        
        if contentOffset <= threshold && headerRefreshData.refreshState == .stopped {
            let oldProgress = headerRefreshData.progress
            let progress = Double(contentOffset / threshold)
            if progress < oldProgress {
                return
            }
            headerRefreshData.progress = (progress >= 1.0) ? 1.0 : progress
        }
        
        if contentOffset > threshold && headerRefreshData.refreshState == .stopped && headerRefreshData.refreshState != .triggered {
            headerRefreshData.refreshState = .triggered
            headerRefreshData.progress = 1.0
        }
        
        if contentOffset <= threshold && headerRefreshData.refreshState == .triggered && headerRefreshData.refreshState != .loading {
            headerRefreshData.refreshState = .loading
            headerRefreshData.progress = 1.0
            isHeaderRefreshing = true
            onHeaderRefresh?()
        }
    }
    
    private func calculateFooterRefreshState(_ proxy: GeometryProxy, value: [FooterBoundsPreferenceKey.Item]) {
        
        guard isHaveMoreData else {
            return
        }
        
        guard let bounds = value.last?.bounds else {
            return
        }
        
        guard let contentBounds = value.first?.bounds else {
            return
        }
                
        guard footerRefreshData.refreshState != .loading else {
            return
        }
        
        let footerFrame = proxy[bounds]
        let contentFrame = proxy[contentBounds]
        
        let footerFrameY = footerFrame.minY
        let threshold = footerFrame.height
        let bottomDistance: CGFloat = 100
        
        let scrollViewHeight = min(proxy.size.height, contentFrame.height)
        
        if threshold != footerRefreshData.threshold {
            footerRefreshData.threshold = threshold
        }
        
        if footerFrameY >= scrollViewHeight && footerRefreshData.refreshState == .invalid {
            footerRefreshData.refreshState = .stopped
        }
        
        // if contentOffset < 0 , out fo scroll view
        // if contentOffset = 0 , at threshold
        // if contentOffset > 0 , in scroll view
        let contentOffset = scrollViewHeight - footerFrameY
        
        if footerRefreshData.refreshState == .stopped, isHaveMoreData {
            let progress = Double(contentOffset / bottomDistance)
            footerRefreshData.progress = (progress >= 1.0) ? 1.0 : progress
        } else {
            if contentOffset == 0 {
                footerRefreshData.progress = 0.0
            }
        }
        
        if contentOffset > bottomDistance && footerRefreshData.refreshState == .stopped && footerRefreshData.refreshState != .triggered {
            footerRefreshData.refreshState = .triggered
            footerRefreshData.progress = 1.0
        }
        
        if contentOffset <= bottomDistance && footerRefreshData.refreshState == .triggered && footerRefreshData.refreshState != .loading {
            footerRefreshData.refreshState = .loading
            footerRefreshData.progress = 0.0
            isFooterRefreshing = true
            onFooterRefresh?()
        }
    }
}

