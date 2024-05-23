//
//  KeyboardListener.swift
//  memo
//
//  Created by LI on 2021/11/16.
//

import Foundation
import UIKit

class KeyboardListener: ObservableObject {
    
    var keyboardHeight: CGFloat = 0 {
        willSet {
            objectWillChange.send()
        }
    }
    
    var animationOption: UIView.AnimationOptions { _animationOption }
    private var _animationOption: UIView.AnimationOptions = .curveEaseInOut
    
    var animationDuration: Double { _animationDuration }
    private var _animationDuration: Double = 0.25
    
    
    init() {
        let willChangeFrameName = UIResponder.keyboardWillChangeFrameNotification
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillChangeFrame(_:)), name: willChangeFrameName, object: nil)
    }
    
    @objc private func keyboardWillChangeFrame(_ noti: Notification) {
        guard let json = noti.userInfo as? [String: Any], json.isEmpty == false else { return }
        
        if let value = json[UIResponder.keyboardAnimationCurveUserInfoKey] as? UInt {
            _animationOption = UIView.AnimationOptions.init(rawValue: value)
        }
        if let value = json[UIResponder.keyboardAnimationDurationUserInfoKey] as? Double {
            _animationDuration = value
        }
        if let value = json[UIResponder.keyboardFrameBeginUserInfoKey] as? NSValue {
            keyboardHeight = value.cgRectValue.height
        }
    }
    
}
