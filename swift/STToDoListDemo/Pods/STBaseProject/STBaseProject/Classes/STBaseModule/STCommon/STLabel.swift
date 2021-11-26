//
//  STLabel.swift
//  STBaseProject
//
//  Created by stack on 2017/10/14.
//  Copyright © 2017年 ST. All rights reserved.
//

import UIKit

/// Label 的对齐类型
public enum STLabelVerticalAlignment {
    case top
    case middle
    case bottom
}

public class STLabel: UILabel {
    
    private var verticalAlignment: STLabelVerticalAlignment?
    
    public init(frame: CGRect, type: STLabelVerticalAlignment) {
        super.init(frame: frame)
        self.verticalAlignment = type
    }
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        self.verticalAlignment = STLabelVerticalAlignment.middle
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    public override func textRect(forBounds bounds: CGRect, limitedToNumberOfLines numberOfLines: Int) -> CGRect {
        var textRect: CGRect = super.textRect(forBounds: bounds, limitedToNumberOfLines: numberOfLines)
        switch self.verticalAlignment {
        case .top?:
            textRect.origin.y = bounds.origin.y
        case .bottom?:
            textRect.origin.y = bounds.origin.y + bounds.size.height - textRect.size.height
        case .middle?:
            fallthrough
        default:
            textRect.origin.y = bounds.origin.y + (bounds.size.height - textRect.size.height) / 2.0
        }
        return textRect
    }
    
    public override func draw(_ rect: CGRect) {
        let rect: CGRect = self.textRect(forBounds: rect, limitedToNumberOfLines: self.numberOfLines)
        super.drawText(in: rect)
    }
}
