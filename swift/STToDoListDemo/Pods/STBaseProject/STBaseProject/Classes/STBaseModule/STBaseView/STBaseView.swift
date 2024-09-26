//
//  STBaseView.swift
//  STBaseProject
//
//  Created by stack on 2018/3/14.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

open class STBaseView: UIView {
    
    private var extraContentSizeOffset: CGFloat = 0
        
    deinit {
        STLog("🌈 -> \(self) 🌈 ----> 🌈 dealloc")
    }
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        self.st_baseViewAddScrollView()
    }
    
    required public init?(coder: NSCoder) {
        super.init(coder: coder)
        self.st_baseViewAddScrollView()
    }
    
    private func st_baseViewAddScrollView() -> Void {
        self.addSubview(self.baseScrollView )
        self.baseScrollView.addSubview(self.baseContentView)
        self.addConstraints([
                            NSLayoutConstraint.init(item: self.baseScrollView, attribute: .top, relatedBy: .equal, toItem: self, attribute: .top, multiplier: 1, constant: 0),
                            NSLayoutConstraint.init(item: self.baseScrollView, attribute: .leading, relatedBy: .equal, toItem: self, attribute: .leading, multiplier: 1, constant: 0),
                            NSLayoutConstraint.init(item: self.baseScrollView, attribute: .bottom, relatedBy: .equal, toItem: self, attribute: .bottom, multiplier: 1, constant: 0),
                            NSLayoutConstraint.init(item: self.baseScrollView, attribute: .trailing, relatedBy: .equal, toItem: self, attribute: .trailing, multiplier: 1, constant: 0)
        ])
        self.baseScrollView.addConstraints([
                                            NSLayoutConstraint.init(item: self.baseContentView, attribute: .width, relatedBy: .equal, toItem: self.baseScrollView, attribute: .width, multiplier: 1, constant: 0),
                                            NSLayoutConstraint.init(item: self.baseContentView, attribute: .top, relatedBy: .equal, toItem: self.baseScrollView, attribute: .top, multiplier: 1, constant: 0),
                                            NSLayoutConstraint.init(item: self.baseContentView, attribute: .leading, relatedBy: .equal, toItem: self.baseScrollView, attribute: .leading, multiplier: 1, constant: 0),
                                            NSLayoutConstraint.init(item: self.baseContentView, attribute: .bottom, relatedBy: .equal, toItem: self.baseScrollView, attribute: .bottom, multiplier: 1, constant: 0),
                                            NSLayoutConstraint.init(item: self.baseContentView, attribute: .trailing, relatedBy: .equal, toItem: self.baseScrollView, attribute: .trailing, multiplier: 1, constant: 0)
        ])
    }
    
    public func updateExtraContentSize(offset: CGFloat) {
        self.extraContentSizeOffset = offset
    }
    
    open override func layoutSubviews() {
        super.layoutSubviews()
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
            var height: CGFloat = 0
            if let lastView = self.baseContentView.subviews.last {
                height = lastView.frame.maxY
            }
            self.baseScrollView.contentSize = CGSize.init(width: self.bounds.size.width, height: height + self.extraContentSizeOffset)
        }
    }
    
    private lazy var baseScrollView: UIScrollView = {
        let scrollView = UIScrollView()
        scrollView.isPagingEnabled = true
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        return scrollView
    }()
    
    public lazy var baseContentView: UIView = {
        let contentView = UIView()
        contentView.translatesAutoresizingMaskIntoConstraints = false
        return contentView
    }()
}

extension UIView {
    public func currentViewController() -> (UIViewController?) {
        var window = UIApplication.shared.keyWindow
        if window?.windowLevel != UIWindow.Level.normal {
            let windows = UIApplication.shared.windows
            for windowTemp in windows {
                if windowTemp.windowLevel == UIWindow.Level.normal {
                    window = windowTemp
                    break
                }
            }
        }
        let vc = window?.rootViewController
        return currentViewController(vc)
    }

    func currentViewController(_ vc :UIViewController?) -> UIViewController? {
        if vc == nil {
            return nil
        }
        if let presentVC = vc?.presentedViewController {
            return currentViewController(presentVC)
        } else if let tabVC = vc as? UITabBarController {
            if let selectVC = tabVC.selectedViewController {
                return currentViewController(selectVC)
            }
            return nil
        } else if let naiVC = vc as? UINavigationController {
            return currentViewController(naiVC.visibleViewController)
        } else {
            return vc
        }
    }
}
