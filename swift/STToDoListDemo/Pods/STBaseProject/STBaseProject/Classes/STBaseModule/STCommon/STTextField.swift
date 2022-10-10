//
//  STTextField.swift
//  STBaseProject
//
//  Created by stack on 2018/10/12.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

public protocol STTextFieldDelegate: NSObjectProtocol {
    func st_textFieldEditingChanged(textField: STTextField)
}

open class STTextField: UITextField {
    
    private var limitCount: Int = -1
    private var orginLeft: CGFloat = 0
    private var orginRight: CGFloat = 0
    open var textIsCheck: Bool = false
    weak open var cusDelegate: STTextFieldDelegate?

    @IBInspectable var cornerRadius: CGFloat {
        get {
            return layer.cornerRadius
        }
        set {
            layer.cornerRadius = newValue
            layer.masksToBounds = newValue > 0
        }
    }
    
    @IBInspectable var borderWidth: CGFloat {
        get {
            return layer.borderWidth
        }
        set {
            layer.borderWidth = newValue > 0 ? newValue : 0
        }
    }
    
    @IBInspectable var borderColor: UIColor {
        get {
            return UIColor(cgColor: layer.borderColor!)
        }
        set {
            layer.borderColor = newValue.cgColor
        }
    }
    
    open override func awakeFromNib() {
        super.awakeFromNib()
        self.config()
    }

    public override init(frame: CGRect) {
        super.init(frame: frame)
        self.config()
    }
    
    required public init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    open override func textRect(forBounds bounds: CGRect) -> CGRect {
        let inset = CGRect.init(x: bounds.origin.x + self.orginLeft, y: bounds.origin.y, width: bounds.size.width - self.orginLeft - self.orginRight, height: bounds.size.height)
        return inset
    }
    
    open override func editingRect(forBounds bounds: CGRect) -> CGRect {
        let inset = CGRect.init(x: bounds.origin.x + self.orginLeft, y: bounds.origin.y, width: bounds.size.width - self.orginLeft - self.orginRight, height: bounds.size.height)
        return inset
    }
    
    open override func leftViewRect(forBounds bounds: CGRect) -> CGRect {
        if let newView = self.leftView {
            let frame = newView.frame
            return CGRect.init(x: 0, y: 0, width: frame.size.width, height: frame.size.height)
        }
        return CGRect.zero
    }
    
    open override func rightViewRect(forBounds bounds: CGRect) -> CGRect {
        if let newView = self.rightView {
            let frame = newView.frame
            return CGRect.init(x: 0, y: 0, width: frame.size.width, height: frame.size.height)
        }
        return CGRect.zero
    }
    
    private func config() {
        self.configContent()
        self.addEditingChangedTarget()
    }
    
    private func configContent() {
        self.clearButtonMode = .whileEditing
        self.autocorrectionType = UITextAutocorrectionType.no
        self.autocapitalizationType = UITextAutocapitalizationType.none
    }
    
    private func addEditingChangedTarget() {
        self.addTarget(self, action: #selector(st_textFieldEditingChanged(textField:)), for: .editingChanged)
    }
    
    /// 光标左右间距
    public func config(orginLeft: CGFloat, orginRight: CGFloat) -> Void {
        self.orginLeft = orginLeft
        self.orginRight = orginRight
    }
    
    /// 字符字数限制  textLimitCount <= 0 无限制
    public func config(textLimitCount: Int) -> Void {
        self.limitCount = textLimitCount
    }
        
    public func configAttributed(textColor: UIColor) -> Void {
        if let attributedText = self.attributedPlaceholder {
            let placeholderAttributedString = NSMutableAttributedString(attributedString: attributedText)
            placeholderAttributedString.addAttribute(.foregroundColor, value: textColor, range: NSRange(location: 0, length: placeholderAttributedString.length))
            self.attributedPlaceholder = placeholderAttributedString
        }
    }
    
    public func configAttributed(text: String, textColor: UIColor) -> Void {
        if text.count > 0 {
            let placeholderAttributedString = NSMutableAttributedString(attributedString: NSAttributedString.init(string: text))
            placeholderAttributedString.addAttribute(.foregroundColor, value: textColor, range: NSRange(location: 0, length: placeholderAttributedString.length))
            self.attributedPlaceholder = placeholderAttributedString
        }
    }
    
    @objc private func st_textFieldEditingChanged(textField: STTextField) {
        if self.limitCount > 0 {
            if let inputText = textField.text, inputText.count > self.limitCount {
                self.text = String(inputText.prefix(self.limitCount))
            }
        }
        self.cusDelegate?.st_textFieldEditingChanged(textField: textField)
    }
}
