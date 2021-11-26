//
//  STListTableViewCell.swift
//  STToDoListDemo
//
//  Created by song on 2021/11/23.
//

import UIKit

class STListTableViewCell: UITableViewCell {
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.contentView.backgroundColor = self.backgroundColor
        self.backgroundColor = STConstant.f6f6f6()
        self.selectionStyle = .none
        self.initialUI()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    private func initialUI() {
        self.contentView.addSubview(self.bgContentView)
        self.bgContentView.addSubview(self.bigCircleView)
        self.bgContentView.addSubview(self.contentLabel)
        
        self.bgContentView.snp.makeConstraints { make in
            make.left.equalTo(0)
            make.right.equalTo(-0)
            make.top.equalTo(5)
            make.bottom.equalTo(-5)
        }
    
        self.bigCircleView.snp.makeConstraints { make in
            make.left.equalTo(20)
            make.width.height.equalTo(30)
            make.centerY.equalTo(self.bgContentView.snp.centerY)
        }
        
        self.contentLabel.snp.makeConstraints { make in
            make.centerY.equalTo(self.bgContentView.snp.centerY)
            make.left.equalTo(self.bigCircleView.snp.right).offset(10)
            make.right.equalTo(-10)
        }
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func update(content: String, isDeleted: Bool) {
        if isDeleted {
            let attrStr = NSMutableAttributedString.init(string: content)
            attrStr.addAttribute(NSAttributedString.Key.strikethroughStyle, value: 2, range: NSRange.init(location: 0, length: content.count))
            attrStr.addAttributes([NSAttributedString.Key.foregroundColor: STConstant.d7dbe1(), NSAttributedString.Key.strikethroughColor: STConstant.d7dbe1()], range: NSRange.init(location: 0, length: content.count))
            self.contentLabel.attributedText = NSAttributedString.init(attributedString: attrStr)
        } else {
            let attrStr = NSMutableAttributedString.init(string: content)
            attrStr.addAttributes([NSAttributedString.Key.foregroundColor: STConstant.a343c53(), NSAttributedString.Key.strikethroughColor: STConstant.a343c53()], range: NSRange.init(location: 0, length: content.count))
            self.contentLabel.attributedText = attrStr
        }
    }
    
    func update(isSelected: Bool) {
        self.bigCircleView.showSmallCircleView(isShow: isSelected)
    }
    
    lazy var contentLabel: UILabel = {
        let contentLabel = UILabel()
        contentLabel.translatesAutoresizingMaskIntoConstraints = false
        contentLabel.font = UIFont.systemFont(ofSize: 15)
        contentLabel.isUserInteractionEnabled = false
        contentLabel.backgroundColor = UIColor.clear
        contentLabel.numberOfLines = 0
        return contentLabel
    }()
    
    lazy var bigCircleView: STCircleButton = {
        var view = STCircleButton()
        view.layer.borderColor = STConstant.c3c3c3().cgColor
        view.backgroundColor = STConstant.ffffff()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.layer.cornerRadius = 15
        view.layer.borderWidth = 2
        return view
    }()
    
    private lazy var bgContentView: UIView = {
        var view = UIView()
        view.layer.borderColor = STConstant.f6f6f6().cgColor
        view.backgroundColor = STConstant.ffffff()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.layer.cornerRadius = 10
        view.layer.borderWidth = 1
        return view
    }()
}
