//
//  GTMRefreshExtension.swift
//  GTMRefresh
//
//  Created by luoyang on 2016/12/7.
//  Copyright © 2016年 luoyang. All rights reserved.
//

import UIKit
import ObjectiveC

extension UIScrollView {
    
    internal var gtmHeader: GTMRefreshHeader? {
        get {
            return objc_getAssociatedObject(self, &GTMRefreshConstant.associatedObjectGtmHeader) as? GTMRefreshHeader
        }
        set {
            objc_setAssociatedObject(self, &GTMRefreshConstant.associatedObjectGtmHeader, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }
    internal var gtmFooter: GTMLoadMoreFooter? {
        get {
            return objc_getAssociatedObject(self, &GTMRefreshConstant.associatedObjectGtmFooter) as? GTMLoadMoreFooter
        }
        set {
            objc_setAssociatedObject(self, &GTMRefreshConstant.associatedObjectGtmFooter, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }
    
    /// 添加下拉刷新
    ///
    /// - Parameters:
    ///   - refreshHeader: 下拉刷新动效View必须继承GTMRefreshHeader并且要实现SubGTMRefreshHeaderProtocol，不传值的时候默认使用 DefaultGTMRefreshHeader
    ///   - refreshBlock: 刷新数据Block
    @discardableResult
    final public func gtm_addRefreshHeaderView(refreshHeader: GTMRefreshHeader? = DefaultGTMRefreshHeader(), refreshBlock:@escaping () -> Void) -> UIScrollView {
        
        guard let newHeader = refreshHeader else { return self }
        
        guard newHeader is SubGTMRefreshHeaderProtocol  else {
            fatalError("refreshHeader must implement SubGTMRefreshHeaderProtocol")
        }
        
        newHeader.frame = CGRect(x: 0, y: 0, width: self.mj_w, height: newHeader.headerProtocolImp!.contentHeight())
        
        if let oldHeader = gtmHeader {
            oldHeader.removeFromSuperview()
        }
        
        newHeader.refreshBlock = refreshBlock
        self.insertSubview(newHeader, at: 0)
        self.gtmHeader = newHeader
        
        return self
    }
    
    
    
    /// 添加上拉加载
    ///
    /// - Parameters:
    ///   - loadMoreFooter: 上拉加载动效View必须继承GTMLoadMoreFooter，不传值的时候默认使用 DefaultGTMLoadMoreFooter
    ///   - refreshBlock: 加载更多数据Block
    @discardableResult
    final public func gtm_addLoadMoreFooterView(loadMoreFooter: GTMLoadMoreFooter? = DefaultGTMLoadMoreFooter(), loadMoreBlock:@escaping () -> Void) -> UIScrollView {
        
        guard let newFooter = loadMoreFooter else { return self }
        
        guard loadMoreFooter is SubGTMLoadMoreFooterProtocol  else {
            fatalError("loadMoreFooter must implement SubGTMLoadMoreFooterProtocol")
        }
        newFooter.frame = CGRect(x: 0, y: 0, width: self.mj_w, height: newFooter.subProtocol!.contentHeith())
        
        if let oldFooter = gtmFooter {
            oldFooter.removeFromSuperview()
        }
        
        newFooter.loadMoreBlock = loadMoreBlock
        self.insertSubview(newFooter, at: 0)
        self.gtmFooter = newFooter
        return self
    }
    
    final public func triggerRefreshing(){
        self.gtmHeader?.autoRefreshing()
    }
    
    final public func endRefreshing(isSuccess: Bool = true) {
        self.gtmHeader?.endRefresing(isSuccess: isSuccess)
        // 重置footer状态（防止footer还处在数据加载完成状态）
        self.gtmFooter?.state = .idle
    }
    final public func endLoadMore(isNoMoreData: Bool = false) {
        self.gtmFooter?.endLoadMore(isNoMoreData: isNoMoreData)
    }
}

extension UIScrollView {
    var mj_inset: UIEdgeInsets {
        get {
            if #available(iOS 11, *) {
                return self.adjustedContentInset
            } else {
                return self.contentInset
            }
        }
    }
    var mj_insetT: CGFloat {
        get { return mj_inset.top }
        set {
            var inset = self.contentInset
            inset.top = newValue
            if #available(iOS 11, *) {
                inset.top -= self.safeAreaInsets.top
            }
            self.contentInset = inset
        }
    }
    var mj_insetB: CGFloat {
        get { return mj_inset.bottom }
        set {
            var inset = self.contentInset
            inset.bottom = newValue
            if #available(iOS 11, *) {
                inset.bottom -= self.safeAreaInsets.bottom
            }
            self.contentInset = inset
        }
    }
    var mj_insetL: CGFloat {
        get { return mj_inset.left }
        set {
            var inset = self.contentInset
            inset.left = newValue
            if #available(iOS 11, *) {
                inset.left -= self.safeAreaInsets.left
            }
            self.contentInset = inset
        }
    }
    var mj_insetR: CGFloat {
        get { return mj_inset.right }
        set {
            var inset = self.contentInset
            inset.right = newValue
            if #available(iOS 11, *) {
                inset.right -= self.safeAreaInsets.right
            }
            self.contentInset = inset
        }
    }
    
    
    var mj_offsetX: CGFloat {
        get { return contentOffset.x }
        set {
            var offset = self.contentOffset
            offset.x = newValue
            self.contentOffset = offset
        }
    }
    var mj_offsetY: CGFloat {
        get { return contentOffset.y }
        set {
            var offset = self.contentOffset
            offset.y = newValue
            self.contentOffset = offset
        }
    }
    
    
    var mj_contentW: CGFloat {
        get { return contentSize.width }
        set {
            var size = self.contentSize
            size.width = newValue
            self.contentSize = size
        }
    }
    var mj_contentH: CGFloat {
        get { return contentSize.height }
        set {
            var size = self.contentSize
            size.height = newValue
            self.contentSize = size
        }
    }
}

extension UIView {
    
    var mj_x: CGFloat {
        get { return frame.origin.x }
        set {
            var frame = self.frame
            frame.origin.x = newValue
            self.frame = frame
        }
    }
    
    var mj_y: CGFloat {
        get { return frame.origin.y }
        set {
            var frame = self.frame
            frame.origin.y = newValue
            self.frame = frame
        }
    }
    
    var mj_w: CGFloat {
        get { return frame.size.width }
        set {
            var frame = self.frame
            frame.size.width = newValue
            self.frame = frame
        }
    }
    
    var mj_h: CGFloat {
        get { return frame.size.height }
        set {
            var frame = self.frame
            frame.size.height = newValue
            self.frame = frame
        }
    }
    
    var mj_size: CGSize {
        get { return frame.size }
        set {
            var frame = self.frame
            frame.size = newValue
            self.frame = frame
        }
    }
    
    var mj_origin: CGPoint {
        get { return frame.origin }
        set {
            var frame = self.frame
            frame.origin = newValue
            self.frame = frame
        }
    }
    
    var mj_center: CGPoint {
        get { return CGPoint(x: (frame.size.width-frame.origin.x)*0.5, y: (frame.size.height-frame.origin.y)*0.5) }
        set {
            var frame = self.frame
            frame.origin = CGPoint(x: newValue.x - frame.size.width*0.5, y: newValue.y - frame.size.height*0.5)
            self.frame = frame
        }
    }
    
}
