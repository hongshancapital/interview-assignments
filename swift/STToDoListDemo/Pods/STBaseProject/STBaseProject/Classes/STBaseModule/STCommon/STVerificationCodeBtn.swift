//
//  STVerificationCodeBtn.swift
//  STBaseProject
//
//  Created by stack on 2020/2/8.
//  Copyright © 2020 ST. All rights reserved.
//

import UIKit

open class STVerificationCodeBtn: STBtn {

    private var timer: Timer?
    private var originTitle: String?
    private var tempTimerInterval: Int = 0

    /// 显示后缀 10s 、10秒
    open var titleSuffix: String = ""
    
    /// 倒计时间隔时间
    open var interval: TimeInterval = 1
    
    /// 倒计时结束时间
    open var timerInterval: Int = 60

    deinit {
        self.invalidTimer()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required public init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    override open func awakeFromNib() {
        super.awakeFromNib()
    }
    
    public func st_configDone() {
        self.originTitle = self.titleLabel?.text
        self.tempTimerInterval = self.timerInterval
    }
    
    public func beginTimer() -> Void {
        self.timer = Timer.scheduledTimer(withTimeInterval: self.interval, repeats: true, block: {[weak self] (resultTimer) in
            guard let strongSelf = self else { return }
            strongSelf.timerSelector()
        })
    }
    
    @objc private func timerSelector() -> Void {
        DispatchQueue.main.async {
            if self.timerInterval == 0 {
                self.invalidTimer()
                self.isUserInteractionEnabled = true
                self.timerInterval = self.tempTimerInterval
                if let title = self.originTitle {
                    self.setTitle(title, for: UIControl.State.normal)
                } else {
                    self.setTitle("发送验证码", for: UIControl.State.normal)
                }
                return
            }
            self.timerInterval -= 1
            self.isUserInteractionEnabled = false
            self.setTitle("\(self.timerInterval)\(self.titleSuffix)", for: UIControl.State.normal)
        }
    }
    
    public func invalidTimer() -> Void {
        if self.timer?.isValid ?? false {
            self.timer?.invalidate()
            self.timer = nil
        }
    }
}
