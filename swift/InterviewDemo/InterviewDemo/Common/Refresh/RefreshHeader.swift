//

//
//  RefreshHeader.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/2.
//
//


import Foundation
import SwiftUI

//下拉刷新组件
struct RefreshHeader: UIViewRepresentable {
    // 加载中
    @Binding var isRefreshing: Bool
    // 完成的回调
    let action: (() -> Void)?
    
    init(isRefreshing: Binding<Bool>, action: (() -> Void)? = nil) {
        _isRefreshing = isRefreshing
        self.action = action
    }
    
    func makeUIView(context: Context) -> some UIView {
        let uiView = UIView(frame: .zero)
        return uiView
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        DispatchQueue.main.asyncAfter(deadline: .now()) {
            guard let viewHost = uiView.superview?.superview else {
                return
            }
            guard let scrollView = self.scrollView(root: viewHost) else {
                return
            }
            // 通过RefreshControl控制刷新动画
            if let refreshControl = scrollView.refreshControl {
                // 开始/结束刷新
                if self.isRefreshing {
                    refreshControl.beginRefreshing()
                } else {
                    refreshControl.endRefreshing()
                }
            }else {
                // 没有RefreshControl时创建一个RefreshControl
                let refreshControl = UIRefreshControl()
                scrollView.refreshControl = refreshControl
                // 初始化位置
                context.coordinator.initOffset = scrollView.contentOffset.y
                // 设置监听
                context.coordinator.setupObserver(scrollView)
            }
        }
    }
    
    static func dismantleUIView(_ uiView: UIView, coordinator: Coordinator) {
        // 消失时清除监听
        coordinator.clearObserver()
    }
}

extension RefreshHeader {
    func makeCoordinator() -> Coordinator {
        return Coordinator(isRefreshing: $isRefreshing, action:action)
    }
    
    class Coordinator {
        // 刷新状态
        var isRefreshing: Binding<Bool>
        // 完成回调
        let action: (() -> Void)?
        // 手势状态
        var stateToken: NSKeyValueObservation?
        // 滑动初始位置
        var initOffset:CGFloat = 0
        
        init(isRefreshing: Binding<Bool>, action: (() -> Void)?) {
            self.isRefreshing = isRefreshing
            self.action = action
        }
    }
    
    // 查找scrollview
    private func scrollView(root: UIView) -> UIScrollView? {
        for subview in root.subviews {
            if subview.isKind(of: UIScrollView.self) {
                return subview as? UIScrollView
            } else if let scrollView = scrollView(root: subview) {
                return scrollView
            }
        }
        return nil
    }
}

private extension RefreshHeader.Coordinator {
    
    // 设置监听
    func setupObserver(_ scrollView: UIScrollView) {
        stateToken = scrollView.observe(\.panGestureRecognizer.state) { [weak self] scrollView,_  in
            guard scrollView.panGestureRecognizer.state == .ended else { return }
            // 手势结束时调用结束判断
            self?.scrollViewDidEndDragging(scrollView)
        }
    }
    
    // 清除监听
    func clearObserver() {
        stateToken?.invalidate()
    }
    
    // 处理手势结束
    func scrollViewDidEndDragging(_ scrollView: UIScrollView) {
        // 正在刷新则不处理
        if isRefreshing.wrappedValue { return }
        // 手势拖动取消刷新
        if initOffset - scrollView.contentOffset.y < 40 { return }
        // 开始刷新
        isRefreshing.wrappedValue = true
        if let actionMethod = action {
            actionMethod()
        }
    }
}


