//
//  STView.swift
//  STToDoListDemo
//
//  Created by song on 2021/11/26.
//

import UIKit

extension UIView {
    
    var tlx : CGFloat {
        get {
            return self.frame.origin.x
        }
        set(newValue){
            self.frame.origin = CGPoint.init(x: newValue, y: self.frame.origin.y)
        }
    }
    
    var tly : CGFloat {
        get{
            return self.frame.origin.y
        }
        set(newValue){
            self.frame.origin = CGPoint.init(x: self.frame.origin.x, y: newValue)
        }
    }
    
    var tlwidth : CGFloat {
        get {
            return self.bounds.size.width
        }
        set(newValue){
            self.frame.size = CGSize.init(width: newValue, height: self.frame.size.height)
        }
    }
    
    var tlheight : CGFloat {
        get {
            return self.bounds.size.height
        }
        set(newValue){
            self.frame.size = CGSize.init(width: self.frame.size.width, height: newValue)
        }
    }
    
    func tlmaxX() -> CGFloat {
        return self.tlx + self.tlwidth
    }
    
    func tlmaxY() -> CGFloat {
        return self.tly + self.tlheight
    }

    var allSubViews : [UIView] {
        var array = [self.subviews].flatMap {$0}
        array.forEach { array.append(contentsOf: $0.allSubViews) }
        return array
    }
    
    private func dealLabLine() {
        if self.isMember(of: UILabel.self) {
            self.frame = CGRect.init(x: Int(self.tlx), y: Int(self.tly), width: Int(self.tlwidth), height: Int(self.tlheight))
        }
    }
    
    func normoalCornerRadius(radius: CGFloat) {
        self.dealLabLine()
        
        self.layer.cornerRadius = radius
        if self.isMember(of: UILabel.self) || self.isMember(of: UIImageView.self) {
            self.layer.masksToBounds = true
        }
    }
}
