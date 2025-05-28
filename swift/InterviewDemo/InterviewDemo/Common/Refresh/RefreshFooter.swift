//

//
//  RefreshFooter.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/2.
//  
//


import Foundation
import SwiftUI

//刷新组件
struct RefreshFooter: UIViewRepresentable {
    // 刷新状态
    @Binding var isRefreshing: Bool
    // 回调
    let action: (() -> Void)?
    // 加载中文字内容
    let loadingText: String?
    // 没有数据时文字内容
    let noMoreText: String?
    
    init(isRefreshing: Binding<Bool>, loadingText: String? = nil, noMoreText:String? = nil, action: (() -> Void)? = nil) {
        _isRefreshing = isRefreshing
        self.loadingText = loadingText
        self.noMoreText = noMoreText
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
            // 同步数据
            context.coordinator.noMoreText = noMoreText
            // 初始化底部视图
            context.coordinator.setupFooterView(scrollView)
            // 初始化监听
            context.coordinator.setupObserver(scrollView)
        }
    }
    
    static func dismantleUIView(_ uiView: UIView, coordinator: Coordinator) {
        coordinator.clearObserver()
    }
}

extension RefreshFooter {
    func makeCoordinator() -> Coordinator {
        return Coordinator(isRefreshing: $isRefreshing, loadingText: loadingText, noMoreText:noMoreText, action:action)
    }
    
    class Coordinator {
        // 刷新状态
        var isRefreshing: Binding<Bool>
        // 加载中文字内容
        var loadingText: String?
        // 没有数据时文字内容
        var noMoreText: String?
        // 回调
        let action: (() -> Void)?
        // 手势状态监听
        private var stateToken: NSKeyValueObservation?
        // 尺寸监听
        private var sizeToken: NSKeyValueObservation?
        // 高度
        private let height:CGFloat = 80
        // 底部View
        private var footerView:FooterView?
        
        init(isRefreshing: Binding<Bool>, loadingText: String?, noMoreText:String?, action: (() -> Void)?) {
            self.isRefreshing = isRefreshing
            self.loadingText = loadingText ?? "Loading..."
            self.noMoreText = noMoreText
            self.action = action
        }
    }
    
    // 获取ScrollView
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

private extension RefreshFooter.Coordinator {
    
    // 设置底部视图
    func setupFooterView(_ scrollView: UIScrollView) {
        if footerView == nil  {
            footerView = FooterView(frame: CGRect(x: 0, y: scrollView.contentSize.height, width: UIScreen.main.bounds.width, height: height))
        }
        guard let footerView = footerView  else {
            return
        }
        // 设置文本
        footerView.loadingText = loadingText
        footerView.noMoreText = noMoreText
        
        if footerView.isRefreshing != isRefreshing.wrappedValue {
            if footerView.isRefreshing { // 刷新状态由true变为false时播放结束动画
                DispatchQueue.main.async {
                    UIView.animate(withDuration: 0.3, animations: {
                        scrollView.contentInset.bottom = 0
                    })
                }
            } else { // 刷新状态由false变为true时增加底部距离
                scrollView.contentInset.bottom = self.height
            }
            //isRefreshing 状态发生变化则重新赋值
            footerView.isRefreshing = isRefreshing.wrappedValue
        }
        
        if footerView.superview == nil {
            scrollView.insertSubview(footerView, at: 0)
        }
    }
    
    func setupObserver(_ scrollView: UIScrollView) {
        
        // 手势监听
        stateToken = scrollView.observe(\.panGestureRecognizer.state) {
            [weak self] scrollView,_  in
            
            guard scrollView.panGestureRecognizer.state == .ended else { return }
            
            self?.scrollViewDidEndDragging(scrollView)
        }
        
        guard let footerView = footerView else {
            return
        }
        // 尺寸监听
        sizeToken = scrollView.observe(\.contentSize) { [weak self] scrollView, _ in
            // scrollView大小改变时更新footerView的位置
            footerView.frame = CGRect(x: 0, y: scrollView.contentSize.height, width: UIScreen.main.bounds.width, height: self?.height ?? 0)
        }
    }
    // 清除监听
    func clearObserver() {
        stateToken?.invalidate()
        sizeToken?.invalidate()
    }
    
    // 处理手势
    func scrollViewDidEndDragging(_ scrollView: UIScrollView) {
        // 正在刷新中不处理
        if isRefreshing.wrappedValue { return }
        // 上拉距离不足不处理
        if scrollView.contentOffset.y + scrollView.bounds.height - scrollView.contentSize.height - scrollView.contentInsetBottom < height {
            return
        }
        // 开始刷新
        isRefreshing.wrappedValue = true
        if let actionMethod = action {
            actionMethod()
        }
        // 增加scrollView底部距离
        scrollView.contentInset.bottom = self.height
    }
}

private class FooterView: UIView {
    // 刷新状态
    var isRefreshing = false {
        didSet {
            if isRefreshing {
                indicator.startAnimating()
                label.text = loadingText
                label.isHidden = false
            } else {
                indicator.stopAnimating()
                label.text = noMoreText
                label.isHidden = noMoreText == nil
            }
            label.sizeToFit()
        }
    }
    
    // 加载菊花
    lazy var indicator = UIActivityIndicatorView(style: .medium)
    // 加载中文字
    var loadingText: String? = nil
    // 没有更多文字
    var noMoreText: String? = nil
    
    // 文字
    private lazy var label: UILabel = {
        let label = UILabel()
        label.font = UIFont.systemFont(ofSize: 14)
        label.textColor = UIColor.lightGray
        return label
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(indicator)
        addSubview(label)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        if isRefreshing {
            // 加载菊花和加载文字整体居中，间隔为4
            let spacing: CGFloat = 5
            label.center = CGPoint(x: bounds.midX + indicator.frame.size.width / 2 + spacing / 2, y: 20)
            indicator.center = CGPoint(x: bounds.midX - label.frame.size.width / 2 - spacing / 2, y: 20)
        } else {
            // 加载结束文字居中
            label.center = CGPoint(x: bounds.midX, y: 20)
        }
    }
    
}

private extension UIScrollView {
    var contentInsetBottom: CGFloat {
        if #available(iOS 11.0, *) {
            return contentInset.bottom + adjustedContentInset.bottom
        } else {
            return contentInset.bottom
        }
    }
}

