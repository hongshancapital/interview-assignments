//
//  GTMRefreshStyle.swift
//  GTMRefresh
//
//  Created by luoyang on 2017/8/8.
//  Copyright © 2017年 luoyang. All rights reserved.
//

import UIKit

extension UIScrollView {
    
    // MARK: - 自定义 header 文字
    @discardableResult
    final public func pullDownToRefreshText(_ text: String) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.pullDownToRefresh = text
        }
        return self
    }
    @discardableResult
    final public func releaseToRefreshText(_ text: String) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.releaseToRefresh = text
        }
        return self
    }
    @discardableResult
    final public func refreshSuccessText(_ text: String) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.refreshSuccess = text
        }
        return self
    }
    @discardableResult
    final public func refreshFailureText(_ text: String) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.refreshFailure = text
        }
        return self
    }
    @discardableResult
    final public func refreshingText(_ text: String) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.refreshing = text
        }
        return self
    }


    //MARK: - 自定义 footer 文字
    @discardableResult
    final public func pullUpToRefreshText(_ text: String) -> UIScrollView {
        if let footer = self.gtmFooter as? DefaultGTMLoadMoreFooter {
            footer.pullUpToRefreshText = text
        }
        return self
    }
    @discardableResult
    final public func loaddingText(_ text: String) -> UIScrollView {
        if let footer = self.gtmFooter as? DefaultGTMLoadMoreFooter {
            footer.loaddingText = text
        }
        return self
    }
    @discardableResult
    final public func noMoreDataText(_ text: String) -> UIScrollView {
        if let footer = self.gtmFooter as? DefaultGTMLoadMoreFooter {
            footer.noMoreDataText = text
        }
        return self
    }
    @discardableResult
    final public func releaseLoadMoreText(_ text: String) -> UIScrollView {
        if let footer = self.gtmFooter as? DefaultGTMLoadMoreFooter {
            footer.releaseLoadMoreText = text
        }
        return self
    }
    
    //MARK: - 自定义文字颜色
    @discardableResult
    final public func headerTextColor(_ color: UIColor) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.txtColor = color
        }
        return self
    }
    @discardableResult
    final public func footerTextColor(_ color: UIColor) -> UIScrollView {
        if let footer = self.gtmFooter as? DefaultGTMLoadMoreFooter {
            footer.txtColor = color
        }
        return self
    }
    
    //MARK: - 自定义文图片
    // header's
    @discardableResult
    final public func headerIdleImage(_ image: UIImage?) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.idleImage = image
        }
        return self
    }
    @discardableResult
    final public func headerSucImage(_ image: UIImage?) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.sucImage = image
        }
        return self
    }
    @discardableResult
    final public func headerFailImage(_ image: UIImage?) -> UIScrollView {
        if let header = self.gtmHeader as? DefaultGTMRefreshHeader {
            header.failImage = image
        }
        return self
    }
    // footer's
    @discardableResult
    final public func footerIdleImage(_ image: UIImage?) -> UIScrollView {
        if let footer = self.gtmFooter as? DefaultGTMLoadMoreFooter {
            footer.idleImage = image
        }
        return self
    }
    
}
