//
//  STToastView.swift
//  STToDoListDemo
//
//  Created by song on 2021/11/26.
//

import UIKit

class STToastView: NSObject {
    
    class func showToastView(str: String) {
        let toastView = UIView()
        toastView.alpha = 0.8
        toastView.backgroundColor = UIColor.black
        toastView.frame = CGRect.init(x: 20, y: 300, width: 100, height: 200)
        STKeyWindow().addSubview(toastView)
        
        let toastLab = UILabel()
        toastLab.text = str
        toastLab.numberOfLines = 0
        toastLab.textAlignment = NSTextAlignment(rawValue: 1)!
        toastLab.textColor = UIColor.white
        toastLab.font = UIFont.systemFont(ofSize: 15)
        toastView.addSubview(toastLab)

        let size = toastLab.sizeThatFits(CGSize.init(width: STDeviceWidth() - 30, height: CGFloat(MAXFLOAT)))
        toastView.frame = CGRect.init(x: (STDeviceWidth() - (size.width + 30)) / 2.0, y: (STDeviceHeight() - (size.height + 10)) / 2, width: size.width + 30, height: size.height + 10)
        toastLab.frame = CGRect.init(x: 15, y: 5, width: size.width, height: size.height)
        toastView.normoalCornerRadius(radius: 6)
        
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 1) {
            UIView.animate(withDuration: 0.5, animations: {
                toastView.alpha = 0
            }) { (true) in
                toastView.removeFromSuperview()
            }
        }
    }
    
    class func STKeyWindow() -> UIView {
        if #available(iOS 13.0, *) {
            return UIApplication.shared.windows[0]
        } else {
            return ((UIApplication.shared.delegate?.window)!)!
        }
    }
    
    class func STLargeViewHeight() -> CGFloat {
        return 52
    }
    
    class func STDeviceWidth() -> CGFloat {
        return UIScreen.main.bounds.size.width
    }
    
    class func STDeviceHeight() -> CGFloat {
        return UIScreen.main.bounds.size.height
    }
}
