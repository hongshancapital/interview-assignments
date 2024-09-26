//
//  STScanView.swift
//  STBaseProject
//
//  Created by stack on 2018/3/14.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

public enum STScanType {
    case STScanTypeQrCode
    case STScanTypeBarCode
    case STScanTypeAll
}

open class STScanView: UIView {

    open var needStop: Bool?
    open var tipTitle: UILabel?
    open var scanType: STScanType?
    open var lineImageView: UIImageView?

    var heightScale: CGFloat = 0.0
    let leftDistance: CGFloat = 60.0
    
    deinit {
        STLog("STScanView dealloc")
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        needStop = false
        self.backgroundColor = UIColor.clear
    }
    
    override open func layoutSubviews() {
        super.layoutSubviews()
        if let newLineImageView = lineImageView {
            let left: CGFloat = leftDistance / heightScale
            let sizeRetangle = CGSize.init(width: self.frame.size.width - left * 2,
                                           height: self.frame.size.width - left * 2)
            let YMinRetangle = self.frame.size.height / 2.0 - sizeRetangle.height / CGFloat(2 * self.heightScale)
            newLineImageView.frame = CGRect.init(x: left,
                                                 y: YMinRetangle + 2,
                                                 width: sizeRetangle.width - 4,
                                                 height: 5)
            self.addSubview(newLineImageView)
        }
    }
    
    required public init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    open func st_configScanType(scanType: STScanType) -> Void {
        if (scanType == .STScanTypeBarCode) {
            self.heightScale = 3.0
            self.lineImageView?.alpha = 0
        } else {
            self.heightScale = 1.0
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5, execute: {
                self.needStop = false
                self.st_startAnimating()
            })
        }
        self.layoutIfNeeded()
    }
    
    @objc open func st_startAnimating() -> Void {
        if self.needStop == true {
            return
        }
        if let newLineImageView = self.lineImageView {
            let Left: CGFloat = leftDistance / heightScale
            let sizeRetangle = CGSize.init(width: self.frame.size.width - Left * 2,
                                           height: (self.frame.size.width - Left * 2) / self.heightScale)
            let YMinRetangle = self.frame.size.height / 2.0 - sizeRetangle.height / 2.0
            let YMaxRetangle = YMinRetangle + sizeRetangle.height
            let initFrame = CGRect.init(x: Left,
                                        y: YMinRetangle + 2,
                                        width: sizeRetangle.width - 4,
                                        height: 5)
            newLineImageView.frame = initFrame
            newLineImageView.alpha = 1
            UIView.animate(withDuration: 1.5, animations: {
                newLineImageView.frame = CGRect.init(x: initFrame.origin.x, y: YMaxRetangle - 2, width: initFrame.size.width, height: initFrame.size.height)
            }) { (state) in
                newLineImageView.alpha = 0
                newLineImageView.frame = initFrame
                self.perform(#selector(self.st_startAnimating), with: nil, afterDelay: 0.3)
            }
        }
    }
    
    open func st_stopAnimating() -> Void {
        self.needStop = false
    }
    
    override open func draw(_ rect: CGRect) {
        super.draw(rect)
        self.st_drawScanRect()
    }
    
    func st_drawScanRect() -> Void {
        let left = leftDistance / self.heightScale
        let sizeRetangle = CGSize.init(width: self.frame.size.width - left * 2,
                                       height: self.frame.size.width - left * 2)
        let YMinRetangle = self.frame.size.height / 2.0 - sizeRetangle.height / (2.0 * self.heightScale)
        let YMaxRetangle = YMinRetangle + sizeRetangle.height / self.heightScale
        let XRetangleRight = self.frame.size.width - left
        
        let context: CGContext = UIGraphicsGetCurrentContext()!
        //非扫码区域半透明
        //设置非识别区域颜色
        context.setFillColor(red: 0, green: 0, blue: 0, alpha: 0.6)

        //扫码区域上面填充
        var rect: CGRect = CGRect.init(x: 0,
                                       y: 0,
                                       width: self.frame.size.width,
                                       height: YMinRetangle)
        context.fill(rect)

        //扫码区域左边填充
        rect = CGRect.init(x: 0,
                           y: YMinRetangle,
                           width: left,
                           height: sizeRetangle.height / self.heightScale)
        context.fill(rect)

        //扫码区域右边填充
        rect = CGRect.init(x: XRetangleRight,
                           y: YMinRetangle,
                           width: left,
                           height: sizeRetangle.height / self.heightScale)
        context.fill(rect)

        //扫码区域下面填充
        rect = CGRect.init(x: 0,
                           y: YMinRetangle + sizeRetangle.height / self.heightScale,
                           width: self.frame.size.width,
                           height: self.frame.size.height - (YMinRetangle + sizeRetangle.height / self.heightScale))

        context.fill(rect)
        context.strokePath()

        //中间画矩形(正方形)
        context.setStrokeColor(UIColor.init(red: 255, green: 255, blue: 255, alpha: 1).cgColor)
        context.setLineWidth(1)
        context.addRect(CGRect.init(x: left,
                                    y: YMinRetangle,
                                    width: sizeRetangle.width,
                                    height: sizeRetangle.height / self.heightScale))
        context.strokePath()
        
        //画矩形框4格外围相框角
        //相框角的宽度和高度
        let wAngle: CGFloat = 15.0
        let hAngle: CGFloat = 15.0
        
        //4个角的 线的宽度
        let linewidthAngle: CGFloat = 4.0
        
        //画扫码矩形以及周边半透明黑色坐标参数
        let diffAngle: CGFloat = linewidthAngle / 3.0
        
        context.setStrokeColor(UIColor.init(red: 0.110, green: 0.659, blue: 0.894, alpha: 1).cgColor)
        context.setFillColor(red: 1.0, green: 1.0, blue: 1.0, alpha: 1.0)

        context.setLineWidth(linewidthAngle)
    
        let leftX: CGFloat = left - diffAngle
        let topY: CGFloat = YMinRetangle - diffAngle
        let rightX: CGFloat = XRetangleRight + diffAngle
        let bottomY: CGFloat = YMaxRetangle + diffAngle
        
        //左上角水平线
        context.move(to: CGPoint.init(x: leftX - linewidthAngle / 2.0, y: topY))
        context.addLine(to: CGPoint.init(x: leftX + wAngle, y: topY))
        
        //左上角垂直线
        context.move(to: CGPoint.init(x: leftX, y: topY - linewidthAngle / 2.0))
        context.addLine(to: CGPoint.init(x: leftX, y: topY + hAngle))

        //左下角水平线
        context.move(to: CGPoint.init(x: leftX - linewidthAngle / 2.0, y: bottomY))
        context.addLine(to: CGPoint.init(x: leftX + wAngle, y: bottomY))

        //左下角垂直线
        context.move(to: CGPoint.init(x: leftX, y: bottomY + linewidthAngle / 2.0))
        context.addLine(to: CGPoint.init(x: leftX, y: bottomY - hAngle))
        
        //右上角水平线
        context.move(to: CGPoint.init(x: rightX + linewidthAngle / 2.0, y: topY))
        context.addLine(to: CGPoint.init(x: rightX - wAngle, y: topY))
        
        //右上角垂直线
        context.move(to: CGPoint.init(x: rightX, y: topY - linewidthAngle / 2.0))
        context.addLine(to: CGPoint.init(x: rightX, y: topY + hAngle))
      
        //右下角水平线
        context.move(to: CGPoint.init(x: rightX + linewidthAngle / 2.0, y: bottomY))
        context.addLine(to: CGPoint.init(x: rightX - wAngle, y: bottomY))
        
        //右下角垂直线
        context.move(to: CGPoint.init(x: rightX, y: bottomY+linewidthAngle / 2.0))
        context.addLine(to: CGPoint.init(x: rightX, y: bottomY - hAngle))
        context.strokePath()
        
        self.st_drawTitle(orginY: YMinRetangle + sizeRetangle.height / self.heightScale + 15)
    }
}

extension STScanView {

    func st_drawTitle(orginY: CGFloat) -> Void {
        guard self.tipTitle != nil else {
            self.tipTitle = UILabel.init(frame: CGRect.init(x: 0, y: orginY, width: self.frame.size.width, height: 50))
            self.tipTitle?.numberOfLines = 0
            self.tipTitle?.layer.zPosition = 1
            self.tipTitle?.textAlignment = .center
            self.tipTitle?.textColor = UIColor.white
            self.tipTitle?.text = "将二维码放入框内,即可自动扫描"
            self.tipTitle?.font = UIFont.systemFont(ofSize: 13)
            self.addSubview(self.tipTitle!)
            self.bringSubviewToFront(self.tipTitle!)
            return
        }
    }
}
