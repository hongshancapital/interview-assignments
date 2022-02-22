//
//  Constant.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import Foundation

import UIKit

extension UIScreen {
    static var statusBarHeight: CGFloat {
        if #available(iOS 13.0, *) {
            return UIStatusBarManager.manager?.statusBarFrame.height ?? 20
        } else {
            return UIApplication.shared.statusBarFrame.height
        }
    }
    
    static var homeIndicatorHeight: CGFloat {
        UIApplication.windowScene?.keyWindow?.safeAreaInsets.bottom ?? 0
    }
}

extension UIStatusBarManager {
    static var manager: UIStatusBarManager? { UIApplication.windowScene?.statusBarManager }
}

extension UIApplication {
    static var windowScene: UIWindowScene? {
        for windowScene:UIWindowScene in ((UIApplication.shared.connectedScenes as?  Set<UIWindowScene>)!) {
            if windowScene.activationState == .foregroundActive {
                return windowScene
            }
        }
        return nil
    }
}


extension UIWindow {
    static var keyWindow: UIWindow? {
        var window:UIWindow? = nil
        
        if #available(iOS 13.0, *) {
            for windowScene:UIWindowScene in ((UIApplication.shared.connectedScenes as?  Set<UIWindowScene>)!) {
                if windowScene.activationState == .foregroundActive {
                    window = windowScene.windows.first
                  break
                }
            }
            return window
        }else{
            return  UIApplication.shared.keyWindow
        }
    }
}
