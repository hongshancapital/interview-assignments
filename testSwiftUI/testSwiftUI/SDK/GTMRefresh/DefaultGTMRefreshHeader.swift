//
//  DefaultGTMRefreshHeader.swift
//  GTMRefresh
//
//  Created by 骆扬 on 2019/12/30.
//  Copyright © 2019 luoyang. All rights reserved.
//

import UIKit


public class DefaultGTMRefreshHeader: GTMRefreshHeader, SubGTMRefreshHeaderProtocol {
    
    var pullDownToRefresh = GTMRLocalize("pullDownToRefresh")
    var releaseToRefresh = GTMRLocalize("releaseToRefresh")
    var refreshSuccess = GTMRLocalize("refreshSuccess")
    var refreshFailure = GTMRLocalize("refreshFailure")
    var refreshing = GTMRLocalize("refreshing")
    
    var txtColor: UIColor? {
        didSet {
            if let color = txtColor {
                self.messageLabel.textColor = color
            }
        }
    }
    var idleImage: UIImage? {
        didSet {
            if let idleImg = idleImage {
                self.pullingIndicator.image = idleImg
            }
        }
    }
    var sucImage: UIImage?
    var failImage: UIImage?
    
    lazy var pullingIndicator: UIImageView = {
        let pindicator = UIImageView()
        pindicator.image = UIImage(named: "arrow_down", in: Bundle(for: GTMRefreshHeader.self), compatibleWith: nil)
        
        return pindicator
    }()
    
    lazy var loaddingIndicator: UIActivityIndicatorView = {
        let indicator = UIActivityIndicatorView()
        indicator.hidesWhenStopped = true
        indicator.style = .gray
        //indicator.backgroundColor = UIColor.white
        
        return indicator
    }()
    
    lazy var messageLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.font = UIFont.systemFont(ofSize: 15)
        
        return label
    }()
    
    // MARK:- Life Cycle
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        self.contentView.addSubview(self.messageLabel)
        self.contentView.addSubview(self.pullingIndicator)
        self.contentView.addSubview(self.loaddingIndicator)
    }
    
    required public init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    // MARK:- Layout
    
    override public func layoutSubviews() {
        super.layoutSubviews()
        
        let center = CGPoint(x: frame.width * 0.5 - 70 - 20, y: frame.height * 0.5)
        pullingIndicator.frame = CGRect(x: 0, y: 0, width: 20, height: 20)
        pullingIndicator.mj_center = center
        
        loaddingIndicator.frame = CGRect(x: 0, y: 0, width: 20, height: 20)
        loaddingIndicator.mj_center = center
        messageLabel.frame = self.bounds
    }
    
    // MARK:- SubGTMRefreshHeaderProtocol
    
    /// 控件的高度
    ///
    /// - Returns: 控件的高度
    public func contentHeight() -> CGFloat {
        return 60.0
    }
    
    public func toNormalState() {
        self.loaddingIndicator.isHidden = true
        self.pullingIndicator.isHidden = false
        self.loaddingIndicator.stopAnimating()
        
        messageLabel.text =  self.pullDownToRefresh
        if let img = self.idleImage {
            pullingIndicator.image = img
        } else {
            pullingIndicator.image = UIImage(named: "arrow_down", in: Bundle(for: DefaultGTMRefreshHeader.self), compatibleWith: nil)
        }
    }
    public func toRefreshingState() {
        self.pullingIndicator.isHidden = true
        self.loaddingIndicator.isHidden = false
        self.loaddingIndicator.startAnimating()
        messageLabel.text = self.refreshing
    }
    public func toPullingState() {
        self.loaddingIndicator.isHidden = true
        messageLabel.text = self.pullDownToRefresh
        
        guard pullingIndicator.transform == CGAffineTransform(rotationAngle: CGFloat(-Double.pi+0.000001))  else{
            return
        }
        UIView.animate(withDuration: 0.4, animations: {
            self.pullingIndicator.transform = CGAffineTransform.identity
        })
    }
    public func toWillRefreshState() {
        messageLabel.text = self.releaseToRefresh
        self.loaddingIndicator.isHidden = true
        
        guard pullingIndicator.transform == CGAffineTransform.identity else{
            return
        }
        UIView.animate(withDuration: 0.4, animations: {
            self.pullingIndicator.transform = CGAffineTransform(rotationAngle: CGFloat(-Double.pi+0.000001))
        })
    }
    public func changePullingPercent(percent: CGFloat) {
        // here do nothing
    }
    
    public func willEndRefreshing(isSuccess: Bool) {
        self.pullingIndicator.isHidden = false
        self.pullingIndicator.transform = CGAffineTransform.identity
        self.loaddingIndicator.isHidden = true
        
        if isSuccess {
            messageLabel.text =  self.refreshSuccess
            if let img = self.sucImage {
                pullingIndicator.image = img
            } else {
                pullingIndicator.image = UIImage(named: "success", in: Bundle(for: DefaultGTMRefreshHeader.self), compatibleWith: nil)
            }
        } else {
            messageLabel.text =  self.refreshFailure
            if let img = self.failImage {
                pullingIndicator.image = img
            } else {
                pullingIndicator.image = UIImage(named: "failure", in: Bundle(for: DefaultGTMRefreshHeader.self), compatibleWith: nil)
            }
        }
    }
    
    public func didEndRefreshing() {
        
    }
}
