//
//  GTMRefreshFooter.swift
//  GTMRefresh
//
//  Created by luoyang on 2016/12/7.
//  Copyright © 2016年 luoyang. All rights reserved.
//

import UIKit

@objc public protocol SubGTMLoadMoreFooterProtocol {
    @objc optional func toNormalState()
    @objc optional func toNoMoreDataState()
    @objc optional func toWillRefreshState()
    @objc optional func toRefreshingState()
    
    /// 控件的高度(自定义控件通过该方法设置自定义高度)
    ///
    /// - Returns: 控件的高度
    func contentHeith() -> CGFloat
}

open class GTMLoadMoreFooter: GTMRefreshComponent, SubGTMRefreshComponentProtocol {
    
    /// 加载更多Block
    var loadMoreBlock: () -> Void = {}
    
    var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor.clear
        return view
    }()
    
    var lastBottomDelta: CGFloat = 0.0
    
    public var subProtocol: SubGTMLoadMoreFooterProtocol? {
        get { return self as? SubGTMLoadMoreFooterProtocol }
    }
    
    public var isNoMoreData: Bool = false {
        didSet {
            if isNoMoreData {
                state = .noMoreData
            } else {
                state = .idle
            }
        }
    }
    
    override var state: GTMRefreshState {
        didSet {
            guard oldValue != state, let scrollV = scrollView else {
                return
            }
            if let header = scrollV.gtmHeader, header.isRefreshing {
                return
            }
            switch state {
            case .idle:
                // 结束加载
                UIView.animate(withDuration: GTMRefreshConstant.slowAnimationDuration, animations: {
                    scrollV.mj_insetB = self.lastBottomDelta
                    //  print("self.lastBottomDelta = \(self.lastBottomDelta)")
                }, completion: { (isComplet) in
                    self.subProtocol?.toNormalState?()
                })
            case .noMoreData:
                self.subProtocol?.toNoMoreDataState?()
            case .refreshing:
                guard oldValue != .refreshing else {
                    return
                }
                self.loadMoreBlock()
                // 展示正在加载动效
                UIView.animate(withDuration: GTMRefreshConstant.fastAnimationDuration, animations: {
                    let overflowHeight = self.contentOverflowHeight
                    var toInsetB = self.mj_h + (self.scrollViewOriginalInset?.bottom)!
                    if overflowHeight < 0 {
                        // 如果内容还没占满
                        toInsetB -= overflowHeight
                    }
                    
                    //  self.lastBottomDelta = toInsetB - scrollV.mj_insetB
                    scrollV.mj_insetB = toInsetB
                    scrollV.mj_offsetY = self.footerWillShowOffsetY + self.mj_h
                }, completion: { (isComplet) in
                })
                self.subProtocol?.toRefreshingState?()
                
            case .willRefresh:
                self.subProtocol?.toWillRefreshState?()
                
            default: break
            }
        }
    }
    
    
    // MARK: - Life Cycle
    
    override public init(frame: CGRect) {
        super.init(frame: frame)
        
        self.addSubview(self.contentView)
        self.contentView.autoresizingMask = UIView.AutoresizingMask.flexibleWidth
    }
    
    required public init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    open override func willMove(toSuperview newSuperview: UIView?) {
        super.willMove(toSuperview: newSuperview)
        
        self.scollViewContentSizeDidChange(change: nil)
        
    }
    
    // MARK: - Layout
    
    override open func layoutSubviews() {
        super.layoutSubviews()
        
        self.contentView.frame = self.bounds
    }
    
    /// 即将触发加载的高度(特殊的控件需要重写该方法，返回不同的数值)
    ///
    /// - Returns: 触发刷新的高度
    open func willLoadMoreHeight() -> CGFloat {
        return self.mj_h // 默认使用控件高度
    }
    
    
    // MARK: - SubGTMRefreshComponentProtocol
    
    open func scollViewContentOffsetDidChange(change: [NSKeyValueChangeKey : Any]?) {
        // refreshing或者noMoreData状态，直接返回
        guard state != .refreshing, state != .noMoreData, let scrollV = scrollView else {
            return
        }
        
        self.scrollViewOriginalInset = scrollV.mj_inset
        
        let currentOffsetY = scrollV.mj_offsetY
        let footerWillShowOffsetY = self.footerWillShowOffsetY
        
        
        guard currentOffsetY >= footerWillShowOffsetY else {
            // footer还没出现， 直接返回
            return
        }
        
        if scrollV.isDragging {
            // 拖动状态
            let willLoadMoreOffsetY = footerWillShowOffsetY + self.willLoadMoreHeight()
            
            switch currentOffsetY {
            case footerWillShowOffsetY...willLoadMoreOffsetY:
                state = .pulling
            case willLoadMoreOffsetY...(willLoadMoreOffsetY + 1000):
                state = .willRefresh
            default: break
            }
        } else {
            // 停止拖动状态
            switch state {
            case .pulling:
                state = .idle
            case .willRefresh:
                state = .refreshing
            default: break
            }
        }
    }
    open func scollViewContentSizeDidChange(change: [NSKeyValueChangeKey : Any]?) {
        // here do nothing
        guard let scrollV = scrollView, let originInset = scrollViewOriginalInset else {
            return
        }
        // 重设位置
        let contentH = scrollV.mj_contentH  // 内容高度
        let visibleH = scrollV.mj_h - originInset.top - originInset.bottom  // 可见区域高度
        
        self.mj_y = contentH > visibleH ? contentH : visibleH
    }
    
    // MARK: - Public
    public func endLoadMore(isNoMoreData: Bool) {
        if isNoMoreData {
            state = .noMoreData
        } else {
            state = .idle
        }
    }
    
    // MARK: - Private
    
    /// ScrollView内容溢出的高度
    private var contentOverflowHeight: CGFloat {
        get {
            guard let scrollV = scrollView, let originInset = scrollViewOriginalInset else {
                return 0.0
            }
            // ScrollView内容占满的高度
            let fullContentHeight = scrollV.mj_h - originInset.bottom - originInset.top
            return scrollV.mj_contentH - fullContentHeight
        }
    }
    /// 上拉刷新控件即将出现时的OffsetY
    private var footerWillShowOffsetY: CGFloat {
        get {
            guard let _ = scrollView, let originInset = scrollViewOriginalInset else {
                return 0.0
            }
            let overflowHeight = contentOverflowHeight
            if overflowHeight > 0 {
                return overflowHeight - originInset.top
            } else {
                return -originInset.top
            }
        }
    }
    
}

