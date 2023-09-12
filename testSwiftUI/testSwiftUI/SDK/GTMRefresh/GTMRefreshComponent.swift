//
//  GTMRefreshComponent.swift
//  GTMRefresh
//
//  Created by luoyang on 2016/12/7.
//  Copyright © 2016年 luoyang. All rights reserved.
//

import UIKit

/// 状态枚举
///
/// - idle:         闲置
/// - pulling:      可以进行刷新
/// - refreshing:   正在刷新
/// - willRefresh:  即将刷新
/// - noMoreData:   没有更多数据
public enum GTMRefreshState {
    case idle           // 闲置
    case pulling        // 可以进行刷新
    case refreshing     // 正在刷新
    case willRefresh    // 即将刷新
    case noMoreData     // 没有更多数据
}

public protocol SubGTMRefreshComponentProtocol {
    func scollViewContentOffsetDidChange(change: [NSKeyValueChangeKey : Any]?)
    func scollViewContentSizeDidChange(change: [NSKeyValueChangeKey : Any]?)
}

open class GTMRefreshComponent: UIView {
    
    public weak var scrollView: UIScrollView?
    
    public var scrollViewOriginalInset: UIEdgeInsets?
    
    var state: GTMRefreshState = .idle
    
    // MARK: Life Cycle
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required public init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setup()
    }
    
    func setup() {
        self.autoresizingMask = UIView.AutoresizingMask.flexibleWidth
        self.backgroundColor = UIColor.clear
        
        self.state = .idle
    }
    
    override open func draw(_ rect: CGRect) {
        super.draw(rect)
        
        if self.state == .willRefresh {
            // 预防view还没显示出来就调用了beginRefreshing
            self.state = .refreshing
        }
    }
    
    deinit {
        if GTMRefreshConstant.debug { print("GTMRefreshComponent excute deinit() ... ")}
    }
    
    override open func willMove(toSuperview newSuperview: UIView?) {
        super.willMove(toSuperview: newSuperview)
        
        guard let scrollView = newSuperview as? UIScrollView else {
            return
        }
        
        self.mj_x = 0
        self.mj_w = scrollView.mj_w

        self.scrollView = scrollView
        // 设置永远支持垂直弹簧效果
        self.scrollView?.alwaysBounceVertical = true
        self.scrollViewOriginalInset = scrollView.mj_inset
        
        self.addObserver()
    }
    
    open override func willMove(toWindow newWindow: UIWindow?) {
        super.willMove(toWindow: newWindow)
        
        if newWindow != nil {
            self.addObserver()
        } else {
            self.removeAbserver()
        }
    }
    
    
    // MARK: KVO
    
    private func addObserver() {
        
        if GTMRefreshConstant.debug { print("GTMRefresh -> addObserver ... ", scrollView)}
        scrollView?.addObserver(self, forKeyPath: GTMRefreshConstant.keyPathContentOffset, options: .new, context: nil)
        scrollView?.addObserver(self, forKeyPath: GTMRefreshConstant.keyPathContentSize, options: .new, context: nil)
    }
    
    private func removeAbserver() {
        if GTMRefreshConstant.debug { print("GTMRefresh -> removeAbserver ... ", scrollView)}
        scrollView?.removeObserver(self, forKeyPath: GTMRefreshConstant.keyPathContentOffset)
        scrollView?.removeObserver(self, forKeyPath: GTMRefreshConstant.keyPathContentSize)
    }
    
    override open func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        guard isUserInteractionEnabled else {
            return
        }
        
        if let sub: SubGTMRefreshComponentProtocol = self as? SubGTMRefreshComponentProtocol {
            if keyPath == GTMRefreshConstant.keyPathContentSize {
                sub.scollViewContentSizeDidChange(change: change)
            }
            
            if self.isHidden {
                return
            }
            
            if keyPath == GTMRefreshConstant.keyPathContentOffset {
                sub.scollViewContentOffsetDidChange(change: change)
            }
            
        }
    }
    
    
    
}
