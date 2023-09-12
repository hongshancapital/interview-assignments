//
//  GTMRefreshHeader.swift
//  GTMRefresh
//
//  Created by luoyang on 2016/12/7.
//  Copyright © 2016年 luoyang. All rights reserved.
//

import UIKit

@objc public protocol SubGTMRefreshHeaderProtocol {
    /// 状态变成.idle
    @objc optional func toNormalState()
    /// 状态变成.refreshing
    @objc optional func toRefreshingState()
    /// 状态变成.pulling
    @objc optional func toPullingState()
    /// 状态变成.willRefresh
    @objc optional func toWillRefreshState()
    /// 下拉高度／触发高度 值改变
    @objc optional func changePullingPercent(percent: CGFloat)
    /// 开始结束动画前执行
    @objc optional func willEndRefreshing(isSuccess: Bool)
    /// 结束动画完成后执行
    @objc optional func didEndRefreshing()
    
    /// 控件的高度
    ///
    /// - Returns: 控件的高度
    func contentHeight() -> CGFloat
}

// MARK: -
open class GTMRefreshHeader: GTMRefreshComponent, SubGTMRefreshComponentProtocol {
    
    /// 刷新数据Block
    var refreshBlock: () -> Void = { }
    
    public var contentView: UIView = {
        let view = UIView()
        view.backgroundColor = UIColor.clear
        return view
    }()
    
    var insetTDelta: CGFloat = 0  // inset top 差值
    
    public var headerProtocolImp: SubGTMRefreshHeaderProtocol? {
        get { return self as? SubGTMRefreshHeaderProtocol }
    }
    
    var pullingPercent: CGFloat = 0 {
        didSet {
            headerProtocolImp?.changePullingPercent?(percent: pullingPercent)
        }
    }
    var isRefreshing: Bool {
        return state == .refreshing
    }
    
    override var state: GTMRefreshState {
        didSet {
            print("GTMRefresh -> header state = \(state) ")
            guard oldValue != state else {
                return
            }
            switch self.state {
            case .idle:   // refreshing -> idle
                guard oldValue != .idle else {
                    return
                }
                if oldValue == .refreshing {
                    DispatchQueue.main.async {
                        UIView.animate(withDuration: GTMRefreshConstant.slowAnimationDuration, animations: {
                            self.scrollView?.mj_insetT += self.insetTDelta
                        }, completion: { (isFinish) in
                            self.headerProtocolImp?.didEndRefreshing?()
                            // 执行刷新操作
                            self.headerProtocolImp?.toNormalState?()
                        })
                    }
                } else {
                    self.headerProtocolImp?.toNormalState?()
                }
            case .pulling:
                guard oldValue != .pulling else {
                    return
                }
                DispatchQueue.main.async {
                    self.headerProtocolImp?.toPullingState?()
                }
            case .willRefresh:
                guard oldValue != .willRefresh else {
                    return
                }
                DispatchQueue.main.async {
                    self.headerProtocolImp?.toWillRefreshState?()
                }
            case .refreshing:
                guard oldValue != .refreshing else {
                    return
                }
                DispatchQueue.main.async {
                    UIView.animate(withDuration: GTMRefreshConstant.fastAnimationDuration, animations: {
                        self.headerProtocolImp?.toRefreshingState?()
                        
                        guard let originInset = self.scrollViewOriginalInset else {
                            return
                        }
                        let top: CGFloat = originInset.top + self.refreshingHoldHeight()
                        // 增加滚动区域top
                        self.scrollView?.mj_insetT = top
                        // 设置滚动位置
                        self.scrollView?.contentOffset = CGPoint(x: 0, y: -top)
                    }, completion: { (isFinish) in
                        // 执行刷新操作
                        self.refreshBlock()
                    })
                }
            default: break
            }
            
            
        }
    }
    
    // MARK: - Life Cycle
    
    override public init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required public init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setup()
    }
    override func setup() {
        self.addSubview(self.contentView)
        self.contentView.autoresizingMask = UIView.AutoresizingMask.flexibleWidth
    }
    
    open override func willMove(toSuperview newSuperview: UIView?) {
        super.willMove(toSuperview: newSuperview)
        
        guard let _ = newSuperview else {
            // newSuperview == nil 被移除的时候
            return
        }
        
        // newSuperview != nil 被添加到新的View上
        self.mj_y = -self.mj_h
    }
    
    // MARK: - Layout
    
    override open func layoutSubviews() {
        super.layoutSubviews()
        
        self.contentView.frame = self.bounds
    }
    
    /// Loadding动画显示区域的高度(特殊的控件需要重写该方法，返回不同的数值)
    ///
    /// - Returns: Loadding动画显示区域的高度
    open func refreshingHoldHeight() -> CGFloat {
        return self.mj_h // 默认使用控件高度
    }
    
    /// 即将触发刷新的高度(特殊的控件需要重写该方法，返回不同的数值)
    ///
    /// - Returns: 触发刷新的高度
    open func willRefresHeight() -> CGFloat {
        return self.mj_h // 默认使用控件高度
    }
    
    // MARK: - SubGTMRefreshComponentProtocol
    open func scollViewContentOffsetDidChange(change: [NSKeyValueChangeKey : Any]?) {
        
        guard let scrollV = self.scrollView else {
            return
        }
        
        let originalInset = self.scrollViewOriginalInset!
        
        // 下拉时 offsetY 都是负值, 值越小表示下拉的距离越大
        if state == .refreshing {
            guard let _ = self.window else {
                return
            }
            // 考虑SectionHeader停留时的高度
            let holdInsetT = self.refreshingHoldHeight() + originalInset.top
            var insetT: CGFloat = (-scrollV.mj_offsetY > originalInset.top) ? -scrollV.mj_offsetY : originalInset.top
            insetT = (insetT > holdInsetT) ? holdInsetT : insetT
            
            scrollV.mj_insetT = insetT
            self.insetTDelta = originalInset.top - insetT
            
            return
        }
        
        // 跳转到下一个控制器时，contentInset可能会变
        self.scrollViewOriginalInset = scrollV.mj_inset
        
        let offsetY: CGFloat = scrollV.mj_offsetY // 当前的contentOffset
        let headerAllShowOffsetY: CGFloat = -originalInset.top // 头部控件全部展示的offsetY
        
        // 头部控件还没完全出现，直接返回
        guard offsetY <= headerAllShowOffsetY else {
            return
        }
        
        // 下拉时 offsetY 都是负值, 值越小表示下拉的距离越大
        // idle 和 pulling 的临界点
        let idle2pullingOffsetY = headerAllShowOffsetY
        // pulling 和 willRefresh 的临界点
        let pulling2willRefreshOffsetY: CGFloat = headerAllShowOffsetY - self.willRefresHeight()
        
        if scrollV.isDragging {
            switch state {
            case .idle:
                // idle -> pulling
                if offsetY <= idle2pullingOffsetY-5 {
                    state = .pulling
                }
            case .pulling:
                if offsetY <= pulling2willRefreshOffsetY {
                    // pulling -> willRefresh
                    state = .willRefresh
                } else if offsetY <= idle2pullingOffsetY-5 {
                    // pulling -> pulling # do precent change
                    self.pullingPercent = -(offsetY - idle2pullingOffsetY) / self.willRefresHeight()
                } else {
                    // pulling -> idle
                    state = .idle
                }
            case .willRefresh:
                // willRefresh -> pulling
                if offsetY > pulling2willRefreshOffsetY {
                    state = .pulling
                }
            default: break
            }
        } else {
            // 停止Drag && 并且是即将刷新状态
            if state == .willRefresh {
                self.pullingPercent = 1.0
                if self.window != nil {
                    // willRefresh -> refreshing
                    state = .refreshing
                }
                else {
                    // 预防正在刷新中时，调用本方法使得header inset回置失败
                    if state != .refreshing {
                        state = .willRefresh
                        // 刷新(预防从另一个控制器回到这个控制器的情况，回来要重新刷新一下)
                        self.setNeedsDisplay()
                    }
                }
            }
            if state == .pulling {
                state = .idle
            }
        }
    }
    open func scollViewContentSizeDidChange(change: [NSKeyValueChangeKey : Any]?) {
        // here do nothing
    }
    
    // MARK: - Public API
    
    final public func autoRefreshing(){
        DispatchQueue.main.async {
            if self.window != nil {
                self.state = .refreshing
            }else{
                if self.state != .refreshing{
                    self.state = .willRefresh
                }
            }
        }
    }
    
    /// 结束刷新
    final public func endRefresing(isSuccess: Bool) {
        DispatchQueue.main.async {
            self.headerProtocolImp?.willEndRefreshing?(isSuccess: isSuccess)
            self.state = .idle
        }
    }
    
}

