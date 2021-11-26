//
//  STCircleButton.swift
//  STToDoListDemo
//
//  Created by song on 2021/11/23.
//

import UIKit

class STCircleButton: UIButton {
    
    var indexPath: IndexPath?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.addSubview(self.smallCircleView)
        self.smallCircleView.snp.makeConstraints { make in
            make.centerX.equalTo(self.snp.centerX)
            make.centerY.equalTo(self.snp.centerY)
            make.width.height.equalTo(16)
        }
    }
    
    func beginDrawCircle(lineWidth: CGFloat, fillColor: UIColor, strokeColor: UIColor) {
        let shapeLayer = CAShapeLayer()
        shapeLayer.frame = frame
        shapeLayer.lineWidth = lineWidth
        shapeLayer.fillColor = fillColor.cgColor
        shapeLayer.strokeColor = strokeColor.cgColor
        let path = UIBezierPath.init(ovalIn: frame)
        shapeLayer.path = path.cgPath
        self.layer.addSublayer(shapeLayer)
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    func showSmallCircleView(isShow: Bool) {
        self.smallCircleView.isHidden = !isShow
    }
    
    private lazy var smallCircleView: UIView = {
        var view = UIView()
        view.isHidden = true
        view.backgroundColor = STConstant.c3c3c3()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.isUserInteractionEnabled = false
        view.layer.cornerRadius = 8
        view.isHidden = true
        return view
    }()
}
