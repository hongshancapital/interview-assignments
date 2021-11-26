//
//  STBaseViewController.swift
//  STBaseProject
//
//  Created by stack on 2017/10/4.
//  Copyright © 2017年 ST. All rights reserved.
//

import UIKit

public enum STNavBtnShowType {
    case none               // 默认什么都不显示
    case showBothBtn        // 显示左侧、右侧按钮和title
    case showLeftBtn        // 显示左侧按钮和title
    case showRightBtn       // 显示右侧按钮和title
    case onlyShowTitle      // 只显示title
}

open class STBaseViewController: UIViewController {

    open var topBgView: UIView!
    open var navBgView: UIView!
    open var leftBtn: UIButton!
    open var rightBtn: UIButton!
    open var titleLabel: UILabel!
    
    private var defaultValue: CGFloat = 44

    open var leftBtnAttributeLeft: NSLayoutConstraint!
    open var leftBtnAttributeWidth: NSLayoutConstraint!
    open var leftBtnAttributeHeight: NSLayoutConstraint!
    
    open var rightBtnAttributeRight: NSLayoutConstraint!
    open var rightBtnAttributeWidth: NSLayoutConstraint!
    open var rightBtnAttributeHeight: NSLayoutConstraint!
    
    open var titleLabelAttributeLeft: NSLayoutConstraint!
    open var titleLabelAttributeRight: NSLayoutConstraint!
    open var titleLabelAttributeHeight: NSLayoutConstraint!

    open var topViewAttributeHeight: NSLayoutConstraint!
    open var navBgViewAttributeHeight: NSLayoutConstraint!

    deinit {
        
    }

    override open func viewDidLoad() {
        super.viewDidLoad()
        self.st_baseConfig()
        self.st_navigationBarView()
    }
    
    private func st_baseConfig() -> Void {
        self.hidesBottomBarWhenPushed = true
        self.view.backgroundColor = UIColor.white
        self.automaticallyAdjustsScrollViewInsets = false
        self.navigationController?.setNavigationBarHidden(true, animated: false)
        self.navigationController?.interactivePopGestureRecognizer?.delegate = nil
    }
    
    private func st_navigationBarView() -> Void {
        self.topBgView = UIView.init()
        self.topBgView.isHidden = true
        self.topBgView.translatesAutoresizingMaskIntoConstraints = false
        self.view.addSubview(self.topBgView)
        
        self.navBgView = UIView.init()
        self.navBgView.isHidden = true
        self.navBgView.translatesAutoresizingMaskIntoConstraints = false
        self.topBgView.addSubview(self.navBgView)
        
        self.titleLabel = UILabel.init()
        self.titleLabel.textAlignment = NSTextAlignment.center
        self.titleLabel.font = UIFont.boldSystemFont(ofSize: 20)
        self.titleLabel.translatesAutoresizingMaskIntoConstraints = false
        self.navBgView.addSubview(self.titleLabel)
    
        self.leftBtn = UIButton.init(type: UIButton.ButtonType.custom)
        self.leftBtn.isHidden = true
        self.leftBtn.translatesAutoresizingMaskIntoConstraints = false
        self.leftBtn.addTarget(self, action: #selector(st_leftBarBtnClick), for: UIControl.Event.touchUpInside)
        self.navBgView.addSubview(self.leftBtn)
        
        self.rightBtn = UIButton.init(type: UIButton.ButtonType.custom)
        self.rightBtn.isHidden = true
        self.rightBtn.translatesAutoresizingMaskIntoConstraints = false
        self.rightBtn.addTarget(self, action: #selector(st_rightBarBtnClick), for: UIControl.Event.touchUpInside)
        self.navBgView.addSubview(self.rightBtn)
        
        self.st_beginLayoutSubviews()
    }
    
    public func st_showNavBtnType(type: STNavBtnShowType) -> Void {
        switch type {
        case .showLeftBtn:
            self.leftBtn.isHidden = false
            self.rightBtn.isHidden = true
            self.topBgView.isHidden = false
            self.navBgView.isHidden = false
            break
        case .showRightBtn:
            self.leftBtn.isHidden = true
            self.rightBtn.isHidden = false
            self.topBgView.isHidden = false
            self.navBgView.isHidden = false
            break
        case .showBothBtn:
            self.leftBtn.isHidden = false
            self.rightBtn.isHidden = false
            self.topBgView.isHidden = false
            self.navBgView.isHidden = false
            break
        case .onlyShowTitle:
            self.leftBtn.isHidden = true
            self.rightBtn.isHidden = true
            self.topBgView.isHidden = false
            self.navBgView.isHidden = false
        default:
            self.leftBtn.isHidden = true
            self.rightBtn.isHidden = true
            self.topBgView.isHidden = true
            self.navBgView.isHidden = true
            break
        }
    }
    
    private func st_beginLayoutSubviews() -> Void {
        self.topViewAttributeHeight = NSLayoutConstraint.init(item: self.topBgView!, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: STConstants.st_navHeight())
        self.view.addConstraints([
            NSLayoutConstraint.init(item: self.topBgView!, attribute: .top, relatedBy: .equal, toItem: self.view, attribute: .top, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.topBgView!, attribute: .left, relatedBy: .equal, toItem: self.view, attribute: .left, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.topBgView!, attribute: .right, relatedBy: .equal, toItem: self.view, attribute: .right, multiplier: 1, constant: 0),
            self.topViewAttributeHeight
        ])
        
        self.navBgViewAttributeHeight = NSLayoutConstraint.init(item: self.navBgView!, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: 44)
        self.view.addConstraints([
            NSLayoutConstraint.init(item: self.navBgView!, attribute: .bottom, relatedBy: .equal, toItem: self.topBgView, attribute: .bottom, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.navBgView!, attribute: .left, relatedBy: .equal, toItem: self.topBgView, attribute: .left, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.navBgView!, attribute: .right, relatedBy: .equal, toItem: self.topBgView, attribute: .right, multiplier: 1, constant: 0),
            self.navBgViewAttributeHeight
        ])
        
        self.titleLabelAttributeLeft = NSLayoutConstraint.init(item: self.titleLabel!, attribute: .left, relatedBy: .equal, toItem: self.navBgView, attribute: .left, multiplier: 1, constant: 0)
        self.titleLabelAttributeRight = NSLayoutConstraint.init(item: self.titleLabel!, attribute: .right, relatedBy: .equal, toItem: self.navBgView, attribute: .right, multiplier: 1, constant: 0)
        self.titleLabelAttributeHeight = NSLayoutConstraint.init(item: self.titleLabel!, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: defaultValue)
        self.view.addConstraints([
            NSLayoutConstraint.init(item: self.titleLabel!, attribute: .centerY, relatedBy: .equal, toItem: self.navBgView, attribute: .centerY, multiplier: 1, constant: 0),
            self.titleLabelAttributeLeft,
            self.titleLabelAttributeRight,
            self.titleLabelAttributeHeight
        ])
        
        self.leftBtnAttributeLeft = NSLayoutConstraint.init(item: self.leftBtn!, attribute: .left, relatedBy: .equal, toItem: self.navBgView, attribute: .left, multiplier: 1, constant: 0)
        self.leftBtnAttributeWidth = NSLayoutConstraint.init(item: self.leftBtn!, attribute: .width, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: defaultValue)
        self.leftBtnAttributeHeight = NSLayoutConstraint.init(item: self.leftBtn!, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: defaultValue)
        self.view.addConstraints([
            NSLayoutConstraint.init(item: self.leftBtn!, attribute: .centerY, relatedBy: .equal, toItem: self.navBgView, attribute: .centerY, multiplier: 1, constant: 0),
            self.leftBtnAttributeLeft,
            self.leftBtnAttributeHeight,
            self.leftBtnAttributeWidth
        ])
                
        self.rightBtnAttributeWidth = NSLayoutConstraint.init(item: self.rightBtn!, attribute: .width, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: defaultValue)
        self.rightBtnAttributeHeight = NSLayoutConstraint.init(item: self.rightBtn!, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: defaultValue)
        self.rightBtnAttributeRight = NSLayoutConstraint.init(item: self.rightBtn!, attribute: .right, relatedBy: .equal, toItem: self.navBgView, attribute: .right, multiplier: 1, constant: 0)
        self.view.addConstraints([
            NSLayoutConstraint.init(item: self.rightBtn!, attribute: .centerY, relatedBy: .equal, toItem: self.navBgView, attribute: .centerY, multiplier: 1, constant: 0),
            self.rightBtnAttributeRight,
            self.rightBtnAttributeHeight,
            self.rightBtnAttributeWidth
        ])
    }
    
    @objc open func st_leftBarBtnClick() -> Void {
        if self.navigationController != nil, self.navigationController?.viewControllers.count ?? 0 > 1 {
            self.navigationController?.popViewController(animated: true)
        } else {
            self.dismiss(animated: true) {}
        }
    }
    
    @objc open func st_rightBarBtnClick() -> Void {}
}

extension STBaseViewController: UIGestureRecognizerDelegate {
    open func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        if let nav = self.navigationController {
            if nav.viewControllers.count <= 1 {
                return false
            }
        }
        return true
    }

    open func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        if gestureRecognizer.isKind(of: UIPanGestureRecognizer.self) == true && otherGestureRecognizer.isKind(of: UIScreenEdgePanGestureRecognizer.self) == true {
            return true
        }
        return false
    }
}

public extension UIViewController {
    func isDark() -> Bool {
        if #available(iOS 12.0, *) {
            if self.traitCollection.userInterfaceStyle == .dark {
                return true
            }
        }
        return false
    }
    
    /// effect as present(_ viewControllerToPresent: UIViewController, animated flag: Bool, completion: (() -> Void)? = nil)
    func setPushAnimatedPresentWithTransition(customSelf: UINavigationController) {
        let animation = CATransition.init()
        animation.duration = 0.3
        animation.type = CATransitionType.moveIn
        animation.subtype = CATransitionSubtype.fromTop
        customSelf.view.layer.add(animation, forKey: nil)
    }
    
    /// effect as dismiss(animated: Bool, completion: (() -> Void)? = nil)
    func setPushAnimatedDismissWithTransition(customSelf: UINavigationController) {
        let animation = CATransition.init()
        animation.duration = 0.3
        animation.type = CATransitionType.moveIn
        animation.subtype = CATransitionSubtype.fromBottom
        customSelf.view.layer.add(animation, forKey: nil)
    }
    
    func st_currentVC() -> UIViewController {
        if let rootViewController = UIApplication.shared.keyWindow?.rootViewController {
            let currentViewController = self.st_getCurrentViewControllerFrom(rootVC: rootViewController)
            return currentViewController
        }
        return UIViewController()
    }
    
    func st_getCurrentViewControllerFrom(rootVC: UIViewController) -> UIViewController {
        var currentVC = rootVC
        if let currentViewContoller = rootVC.presentedViewController {
            currentVC = currentViewContoller
        }
        if rootVC.isKind(of: UINavigationController.self) {
            let nav = rootVC as! UINavigationController
            if let visibleViewController = nav.visibleViewController {
                currentVC = self.st_getCurrentViewControllerFrom(rootVC: visibleViewController)
            }
        } else if rootVC.isKind(of: UITabBarController.self) {
            let tabBar = rootVC as! UITabBarController
            if let selectedViewController = tabBar.selectedViewController {
                currentVC = self.st_getCurrentViewControllerFrom(rootVC: selectedViewController)
            }
        }
        return currentVC
    }
}
