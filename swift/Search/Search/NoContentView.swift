//
//  NoContentView.swift
//  SearchDemo
//
//  Created by chx on 2021/4/18.
//

import UIKit

class NoContentView: UIView {
    let desc = UILabel.init()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.initUI()
    }
    
    func initUI()  {
        self.backgroundColor = UIColor.white
        self.desc.text = "no result"
        self.desc.frame = CGRect.init(x: 0, y: 100, width: frame.width, height: 30)
        self.desc.textColor = UIColor.lightGray
        self.desc.textAlignment = .center
        self.addSubview(self.desc)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    static func getNoResultView(frame : CGRect) -> NoContentView{
       return NoContentView.init(frame: frame)
    }
    
    
    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */

}
