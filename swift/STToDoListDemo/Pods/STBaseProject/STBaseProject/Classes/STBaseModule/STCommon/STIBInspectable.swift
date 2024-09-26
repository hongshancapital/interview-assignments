//
//  STIBInspectable.swift
//  STBaseProject
//
//  Created by stack on 2017/02/24.
//  Copyright © 2017年 ST. All rights reserved.
//

import Foundation

extension NSLayoutConstraint {
    @IBInspectable open var isAdaptateScreen: Bool {
        set {
            self.constant = self.constant * STConstants.st_multiplier()
        }
        get {
            return true
        }
    }
}

extension UILabel {
    @IBInspectable open var isAdapterFont: Bool {
        set {
            let fontName = self.font.fontName
            let adapterSize = self.font.pointSize * STConstants.st_multiplier()
            self.font = UIFont.st_systemFont(ofSize: adapterSize, fontName: fontName)
        }
        get {
            return true
        }
    }
}
