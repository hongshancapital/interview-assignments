//
//  DefaultGTMLoadMoreFooter.swift
//  GTMRefresh
//
//  Created by 骆扬 on 2019/12/30.
//  Copyright © 2019 luoyang. All rights reserved.
//

import UIKit


public class DefaultGTMLoadMoreFooter: GTMLoadMoreFooter, SubGTMLoadMoreFooterProtocol {
    
    var pullUpToRefreshText: String = GTMRLocalize("pullUpToRefresh")
    public var loaddingText: String = GTMRLocalize("loadMore")
    public var noMoreDataText: String = GTMRLocalize("noMoreData")
    public var releaseLoadMoreText: String = GTMRLocalize("releaseLoadMore")
    
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
    
    
    lazy var pullingIndicator: UIImageView = {
        let pindicator = UIImageView()
        if let img = self.idleImage {
            pindicator.image = img
        } else {
            pindicator.image = UIImage(named: "arrow_down", in: Bundle(for: GTMLoadMoreFooter.self), compatibleWith: nil)
        }
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
    
    // MARK: Life Cycle
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        self.contentView.addSubview(self.messageLabel)
        self.contentView.addSubview(self.pullingIndicator)
        self.contentView.addSubview(self.loaddingIndicator)
    }
    
    required public init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override public func willMove(toSuperview newSuperview: UIView?) {
        super.willMove(toSuperview: newSuperview)
        
        guard newSuperview != nil else {
            return
        }
        
        self.pullingIndicator.transform = CGAffineTransform(rotationAngle: CGFloat(-Double.pi+0.000001))
    }
    
    // MARK: Layout
    
    override public func layoutSubviews() {
        super.layoutSubviews()
        
        let center = CGPoint(x: frame.width * 0.5 - 70 - 30, y: frame.height * 0.5)
        pullingIndicator.frame = CGRect(x: 0, y: 0, width: 20, height: 20)
        pullingIndicator.mj_center = center
        
        loaddingIndicator.frame = CGRect(x: 0, y: 0, width: 20, height: 20)
        loaddingIndicator.mj_center = center
        messageLabel.frame = self.bounds
    }
    
    // MARK: SubGTMLoadMoreFooterProtocol
    
    /// 控件的高度
    ///
    /// - Returns: 控件的高度
    public func contentHeith() -> CGFloat {
        return 50.0
    }
    
    public func toNormalState() {
        self.pullingIndicator.isHidden = false
        self.loaddingIndicator.isHidden = true
        self.loaddingIndicator.stopAnimating()
        
        self.messageLabel.text =  self.pullUpToRefreshText
      //  print(".........\(self.messageLabel.text ?? "")")
        UIView.animate(withDuration: 0.4, animations: {
            self.pullingIndicator.transform = CGAffineTransform(rotationAngle: CGFloat(-Double.pi+0.000001))
        })
    }
    public func toNoMoreDataState() {
        self.pullingIndicator.isHidden = true
        self.loaddingIndicator.isHidden = true
        self.loaddingIndicator.stopAnimating()
        
        self.messageLabel.text =  self.noMoreDataText
    }
    public func toWillRefreshState() {
        messageLabel.text =  self.releaseLoadMoreText
        UIView.animate(withDuration: 0.4, animations: {
            self.pullingIndicator.transform = CGAffineTransform.identity
        })
    }
    public func toRefreshingState() {
        self.pullingIndicator.isHidden = true
        self.loaddingIndicator.isHidden = false
        self.loaddingIndicator.startAnimating()
        
        messageLabel.text =  self.loaddingText
    }
    
}
