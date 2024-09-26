//
//  STBtn.swift
//  STBaseProject
//
//  Created by stack on 2017/10/14.
//  Copyright © 2017年 ST. All rights reserved.
//

import UIKit
import MBProgressHUD

public enum STHUDLocation {
    case center
    case top
    case bottom
}

/// 监听`hud`状态
/// - Parameter state: `hud` 是否展示
/// - Returns: `true` hud is show elsewise `hud` is hidden
public typealias STHUDCompletionBlock = (_ state: Bool) -> Void

open class STHUD: NSObject {

    open var labelFont: UIFont?
    open var customView: UIView?
    open var labelColor: UIColor?
    open var customBgColor: UIColor?
    open var detailLabelFont: UIFont?
    open var errorIconImageStr: String?
    open var detailLabelColor: UIColor?
    open var activityViewColor: UIColor?
    open var hudMode: MBProgressHUDMode?
    open var afterDelay: TimeInterval = 1.5
    
    public var progressHUD: MBProgressHUD?
    public static let sharedHUD: STHUD = STHUD()
    private var stCompletionBlock: STHUDCompletionBlock?

    private override init() {
        super.init()
    }

    public func show(text: String) -> Void {
        self.show(text: text, detailText: "")
    }
    
    public func show(text: String, detailText: String) -> Void {
        self.progressHUD?.label.text = text
        self.progressHUD?.detailsLabel.text = detailText
        self.progressHUD?.areDefaultMotionEffectsEnabled = false
        self.progressHUD?.show(animated: true)
        if let block = self.stCompletionBlock {
            block(true)
        }
    }
    
    /// 配置展示
    @objc public func configHUD(showInView: UIView, icon: String, offset: CGPoint) -> Void {
        self.progressHUD?.isHidden = true
        if self.progressHUD?.superview != nil {
            self.progressHUD?.hide(animated: true)
        }
        self.progressHUD = MBProgressHUD.init(view: showInView)
        self.configHUBCommonProperty()
        if icon.count > 0 {
            self.progressHUD?.customView = UIImageView.init(image: UIImage.init(named: icon))
            self.progressHUD?.mode = .customView
        } else {
            self.progressHUD?.mode = .text
        }
        self.progressHUD?.offset = offset
        self.progressHUD?.isHidden = false
        showInView.addSubview(self.progressHUD ?? MBProgressHUD())
    }
    
    /// 配置展示
    @objc public func configManualHiddenHUD(showInView: UIView) -> Void {
        if self.progressHUD?.superview != nil {
            self.progressHUD?.hide(animated: true)
        }
        self.progressHUD = MBProgressHUD.init()
        self.configHUBCommonProperty()
        showInView.addSubview(self.progressHUD ?? MBProgressHUD())
    }
    
    private func configHUBCommonProperty() {
        guard self.progressHUD != nil else { return }
        self.progressHUD?.delegate = self
        self.progressHUD?.label.numberOfLines = 0
        self.progressHUD?.contentColor = UIColor.white
        self.progressHUD?.bezelView.style = .solidColor
        self.progressHUD?.removeFromSuperViewOnHide = true
        if let font = self.labelFont {
            self.progressHUD?.label.font = font
        } else {
            self.progressHUD?.label.font = UIFont.st_systemFont(ofSize: 14, weight: .medium)
        }
        if let color = self.labelColor {
            self.progressHUD?.label.textColor = color
        } else {
            self.progressHUD?.label.textColor = UIColor.black
        }
        if let detailsLabelFont = self.labelFont {
            self.progressHUD?.detailsLabel.font = detailsLabelFont
        } else {
            self.progressHUD?.detailsLabel.font = UIFont.st_systemFont(ofSize: 14, weight: .medium)
        }
        if let detailsLabelColor = self.detailLabelColor {
            self.progressHUD?.detailsLabel.textColor = detailsLabelColor
        } else {
            self.progressHUD?.detailsLabel.textColor = UIColor.white
        }
        if let customColor = self.customBgColor {
            self.progressHUD?.bezelView.backgroundColor = customColor
        } else {
            self.progressHUD?.bezelView.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        }
        if let color = self.activityViewColor {
            self.progressHUD?.bezelView.color = color
        } else {
            self.progressHUD?.bezelView.color = UIColor.black.withAlphaComponent(0.6)
        }
        if let mode = self.hudMode {
            self.progressHUD?.mode = mode
        }
        if let cusView = self.customView {
            self.progressHUD?.customView = cusView
        }
    }
    
    public func hide(animated: Bool) {
        if self.progressHUD?.superview != nil {
            self.progressHUD?.hide(animated: animated)
        }
    }
    
    public func hide(animated: Bool, afterDelay: TimeInterval) {
        if self.progressHUD?.superview != nil {
            self.progressHUD?.hide(animated: animated, afterDelay: afterDelay)
        }
    }
    
    public func hudComplection(block: @escaping STHUDCompletionBlock) -> Void {
        self.stCompletionBlock = block
    }
}

extension STHUD: MBProgressHUDDelegate {
    public func hudWasHidden(_ hud: MBProgressHUD) {
        if let block = self.stCompletionBlock {
            block(false)
        }
    }
}

public extension UIView {
    /// 显示HUD
    ///
    /// 完成后会自动关闭，默认添加到`UIApplication.shared.keyWindow`
    ///
    /// - Parameter text: 展示文字
    func showAutoHidden(text: String) -> Void {
        self.showAutoHidden(text: text, toView: UIApplication.shared.keyWindow ?? UIView())
    }
    
    /// 显示HUD
    ///
    /// 完成后会自动关闭
    ///
    /// - Parameter text: 展示文字
    /// - Parameter toView: 添加到指定`view`
    func showAutoHidden(text: String, toView: UIView) -> Void {
        self.showAutoHidden(text: text, detailText: "", toView: toView)
    }
    
    func showAutoHidden(text: String, detailText: String) -> Void {
        self.showAutoHidden(text: text, detailText: detailText, toView: UIApplication.shared.keyWindow ?? UIView())
    }
    
    func showAutoHidden(text: String, detailText: String, toView: UIView) -> Void {
        self.showAutoHidden(text: text, detailText: detailText, offset: CGPoint.zero, toView: toView)
    }
    
    func showAutoHidden(text: String, detailText: String, offset: CGPoint, toView: UIView) -> Void {
        self.show(text: text, detailText: detailText, icon: "", offset: offset, afterDelay: STHUD.sharedHUD.afterDelay, toView: toView)
    }
    
    func showAutoHidden(text: String, location: STHUDLocation) -> Void {
        self.showAutoHidden(text: text, location: location, toView: UIApplication.shared.keyWindow ?? self)
    }
    
    func showAutoHidden(text: String, location: STHUDLocation, toView: UIView) -> Void {
        self.showAutoHidden(text: text, detailText: "", location: location, toView: toView)
    }
    
    func showAutoHidden(text: String, detailText: String, location: STHUDLocation) -> Void {
        self.showAutoHidden(text: text, detailText: detailText, location: location, toView: UIApplication.shared.keyWindow ?? self)
    }
    
    func showAutoHidden(text: String, detailText: String, location: STHUDLocation, toView: UIView) -> Void {
        self.show(text: text, detailText: detailText, icon: "", afterDelay: STHUD.sharedHUD.afterDelay, location: location, toView: toView)
    }
}

public extension UIView {
    /// 显示HUD
    ///
    /// 切记需要手动关闭
    func showLoadingManualHidden() -> Void {
        self.showLoadingManualHidden(text: "")
    }
    
    func showLoadingManualHidden(text: String) -> Void {
        self.showLoadingManualHidden(text: text, toView: UIApplication.shared.keyWindow ?? self)
    }
    
    func showLoadingManualHidden(text: String, toView: UIView) -> Void {
        self.showLoadingManualHidden(text: text, detailText: "", toView: toView)
    }

    private func showLoadingManualHidden(text: String, detailText: String, toView: UIView) -> Void {
        DispatchQueue.main.async {
            let hud = STHUD.sharedHUD
            if toView.superview != nil {
                hud.configManualHiddenHUD(showInView: toView)
            } else {
                hud.configManualHiddenHUD(showInView: UIApplication.shared.keyWindow ?? self)
            }
            hud.show(text: text, detailText: detailText)
        }
    }

    /// 关闭MBProgressHUD
    func hideHUD() -> Void {
        DispatchQueue.main.async {
            STHUD.sharedHUD.hide(animated: true)
        }
    }
}

public extension UIView {
    func show(text: String, icon: String, toView: UIView) -> Void {
        self.show(text: text, detailText: "", icon: icon, toView: toView)
    }
    
    func show(text: String, detailText: String, icon: String, toView: UIView) -> Void {
        self.show(text: text, detailText: detailText, icon: icon, offset: CGPoint.zero, afterDelay: STHUD.sharedHUD.afterDelay, toView: toView)
    }
    
    private func show(text: String, detailText: String, icon: String, afterDelay: TimeInterval, location: STHUDLocation, toView: UIView) -> Void {
        var point = CGPoint.zero
        if location == .top {
            point = CGPoint.init(x: 0, y: -toView.frame.size.height / 6.0)
        } else if location == .bottom {
            point = CGPoint.init(x: 0, y: toView.frame.size.height / 6.0)
        }
        self.show(text: text, detailText: detailText, icon: icon, offset: point, afterDelay: afterDelay, toView: toView)
    }
    
    private func show(text: String, detailText: String, icon: String, offset: CGPoint, afterDelay: TimeInterval, toView: UIView) -> Void {
        let hud = STHUD.sharedHUD
        if toView.superview != nil {
            hud.configHUD(showInView: toView, icon: icon, offset: offset)
        } else {
            hud.configHUD(showInView: UIApplication.shared.keyWindow ?? self, icon: icon, offset: offset)
        }
        hud.show(text: text, detailText: detailText)
        hud.hide(animated: true, afterDelay: afterDelay)
    }
}
