//
//  PullToRefreshModifier.swift
//  Demo
//
//  Created by 盼 on 2022/4/15.
//

import SwiftUI

extension View {
    func addPullToRefresh(isHeaderRefreshing: Binding<Bool>?, onHeaderRefresh: (() -> Void)?) -> some View {
        modifier(PullToRefreshModifier(isHeaderRefreshing: isHeaderRefreshing, isFooterRefreshing: nil, onHeaderRefresh: onHeaderRefresh, onFooterRefresh: nil))
    }
    
    func addPullToRefresh(isFooterRefreshing: Binding<Bool>?, onFooterRefresh: (() -> Void)?) -> some View {
        modifier(PullToRefreshModifier(isHeaderRefreshing: nil, isFooterRefreshing: isFooterRefreshing, onHeaderRefresh: nil, onFooterRefresh: onFooterRefresh))
    }
    
    func addPullToRefresh(isHeaderRefreshing: Binding<Bool>?, onHeaderRefresh: (() -> Void)?, isFooterRefreshing: Binding<Bool>, onFooterRefresh: (() -> Void)?) -> some View {
        modifier(PullToRefreshModifier(isHeaderRefreshing: isHeaderRefreshing, isFooterRefreshing: isFooterRefreshing, onHeaderRefresh: onHeaderRefresh, onFooterRefresh: onFooterRefresh))
        
    }
}

struct PullToRefreshModifier: ViewModifier {
    @Binding var isHeaderRefreshing: Bool
    @Binding var isFooterRefreshing: Bool
    
    let onHeaderRefresh: (() -> Void)?
    let onFooterRefresh: (() -> Void)?
    
    init(isHeaderRefreshing: Binding<Bool>?, isFooterRefreshing: Binding<Bool>?, onHeaderRefresh: (() -> Void)?, onFooterRefresh: (() -> Void)?) {
        self._isHeaderRefreshing = isHeaderRefreshing ?? .constant(false)
        self._isFooterRefreshing = isFooterRefreshing ?? .constant(false)
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
                .backgroundPreferenceValue(HeaderBoundsPreferenceKey.self) { value -> Color in
                    DispatchQueue.main.async {
                        caculateHeaderRefreshState(proxy, value: value)
                    }
                    return Color.clear
                }
                .backgroundPreferenceValue(FooterBoundsPreferenceKey.self) { value -> Color in
                    DispatchQueue.main.async {
                        caculateFooterRefreshState(proxy, value: value)
                    }
                    return Color.clear
                }
        }
    }
}

extension PullToRefreshModifier {
    private func caculateHeaderRefreshState(_ proxy: GeometryProxy, value: [HeaderBoundsPreferenceKey.Item]) {
    
        guard let bounds = value.first?.bounds else {
            return
        }
        
        // caculate state
        guard headerRefreshData.refreshState != .loading else {
            return
        }
        
        let headerFrame = proxy[bounds]
        
        let y = headerFrame.minY
        let thresold = headerFrame.height
        let topDistance: CGFloat = 0.0
        
        
        if thresold != headerRefreshData.thresold {
            headerRefreshData.thresold = thresold
        }
        
        if -y == headerRefreshData.thresold && headerFrame.width == proxy.size.width && headerRefreshData.refreshState == .invalid {
            headerRefreshData.refreshState = .stopped
        }
        
        var contentOffset = y + thresold
        
        if contentOffset == 0 {
            headerRefreshData.progress = 0.0
        }
        
        guard contentOffset > topDistance else {
            return
        }
        
        contentOffset -= topDistance
        
        // 需要通过geometry proxy 获取真正的frame
        print("the header frame is: \(headerFrame)")
        print("scroll view size is: \(proxy.size)")
        print("content offset is: \(contentOffset)")

        if contentOffset <= thresold && headerRefreshData.refreshState == .stopped {
            let oldProgress = headerRefreshData.progress
            let progress = Double(contentOffset / thresold)
            
            if progress < oldProgress {
                return
            }
            headerRefreshData.progress = (progress >= 1.0) ? 1.0 : progress
        }
        
        if contentOffset > thresold && headerRefreshData.refreshState == .stopped && headerRefreshData.refreshState != .triggered {
            headerRefreshData.refreshState = .triggered
            headerRefreshData.progress = 1.0
        }
        
        if contentOffset <= thresold && headerRefreshData.refreshState == .triggered && headerRefreshData.refreshState != .loading {
            headerRefreshData.refreshState = .loading
            headerRefreshData.progress = 1.0
            isHeaderRefreshing = true
            onHeaderRefresh?()
        }
    }
    
    private func caculateFooterRefreshState(_ proxy: GeometryProxy, value: [FooterBoundsPreferenceKey.Item]) {
        guard let bounds = value.first?.bounds else {
            return
        }
        
        // caculate state
        guard footerRefreshData.refreshState != .loading else {
            return
        }
        
        let footerFrame = proxy[bounds]
        
        let y = footerFrame.minY
        let thresold = footerFrame.height
        let bottomDistance: CGFloat = 0.0
        
        let scrollViewHeight = proxy.size.height
        
        if thresold != footerRefreshData.thresold {
            headerRefreshData.thresold = thresold
        }
        
        if y > proxy.size.height && footerFrame.width == proxy.size.width && footerRefreshData.refreshState == .invalid {
            footerRefreshData.refreshState = .stopped
        }
        
        var contentOffset = scrollViewHeight - y
        
        if contentOffset == 0 {
            footerRefreshData.progress = 0.0
        }
        
        guard contentOffset > bottomDistance else {
            return
        }
        
        contentOffset -= bottomDistance
        
        // 需要通过geometry proxy 获取真正的frame
        print("the footer frame is: \(footerFrame)")
        print("scroll view size is: \(proxy.size)")
        print("content offset is: \(contentOffset)")

        if contentOffset <= thresold && footerRefreshData.refreshState == .stopped {
            let oldProgress = footerRefreshData.progress
            let progress = Double(contentOffset / thresold)
            
            if progress < oldProgress {
                return
            }
            footerRefreshData.progress = (progress >= 1.0) ? 1.0 : progress
        }
        
        if contentOffset > thresold && footerRefreshData.refreshState == .stopped && footerRefreshData.refreshState != .triggered {
            footerRefreshData.refreshState = .triggered
            footerRefreshData.progress = 1.0
        }
        
        if contentOffset <= thresold && footerRefreshData.refreshState == .triggered && footerRefreshData.refreshState != .loading {
            footerRefreshData.refreshState = .loading
            footerRefreshData.progress = 1.0
            isFooterRefreshing = true
            onFooterRefresh?()
        }
    }
}
