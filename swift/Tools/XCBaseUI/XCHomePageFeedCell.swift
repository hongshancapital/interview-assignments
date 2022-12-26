//
//  XCHomePageFeedCell.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit
import SDWebImage
import Masonry

class XCHomePageFeedCell: XCBaseTableViewCell {

    var xcHomePageFeedModel : XCHomePageFeedModel?
    
    /// 定义block
    typealias heartBlock = () -> Void

    /// reslutBlock变量
    var heartRefreshBlock : heartBlock?
    
    lazy var xcBackgroundView : UIView = {
        let view = UIView.init()
        view.backgroundColor = UIColor.white
        view.layer.cornerRadius = 10
        view.clipsToBounds = true
        return view
    }()
    
    lazy var iconImageView : UIImageView = {
        let imageView = UIImageView.init()
        imageView.layer.cornerRadius = 10
        imageView.clipsToBounds = true
        return imageView
    }()
    
    lazy var titleLabel : UILabel = {
        let label = UILabel.init()
        label.backgroundColor = UIColor.clear
        label.textColor = UIColor.black
        label.font = UIFont.systemFont(ofSize: 16)
        label.textAlignment = NSTextAlignment.left
        label.numberOfLines = 0
        return label
    }()
    
    lazy var subTitleLabel : UILabel = {
        let label = UILabel.init()
        label.backgroundColor = UIColor.clear
        label.textColor = UIColor.black
        label.font = UIFont.systemFont(ofSize: 13)
        label.textAlignment = NSTextAlignment.left
        label.numberOfLines = 2
        return label
    }()
    
    lazy var heartButton : UIButton = {
        let button = UIButton.init()
        button.backgroundColor = UIColor.clear
        button.setImage(UIImage(named: "heart_icon"), for: .normal)
        button.setImage(UIImage(named: "heart_icon"), for: .highlighted)
        button.addTarget(self, action: #selector(xc_heartButtonEvent), for: .touchUpInside)
        return button
    }()
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.backgroundColor = UIColor(red: 244/255, green: 243/255, blue: 247/255, alpha: 0.5)
        
        contentView.addSubview(xcBackgroundView)
        xcBackgroundView.addSubview(heartButton)
        xcBackgroundView.addSubview(iconImageView)
        xcBackgroundView.addSubview(titleLabel)
        xcBackgroundView.addSubview(subTitleLabel)
        
        xcBackgroundView.mas_makeConstraints { (make) in
            make?.top.mas_equalTo()(self.mas_top)?.offset()(4)
            make?.left.mas_equalTo()(self.mas_left)?.offset()(12)
            make?.bottom.mas_equalTo()(self.mas_bottom)?.offset()(-4)
            make?.right.mas_equalTo()(self.mas_right)?.offset()(-12)
        }
        
        iconImageView.mas_makeConstraints { (make) in
            make?.top.mas_equalTo()(self.xcBackgroundView.mas_top)?.offset()(10)
            make?.left.mas_equalTo()(self.xcBackgroundView.mas_left)?.offset()(10)
            make?.bottom.mas_equalTo()(self.xcBackgroundView.mas_bottom)?.offset()(-10)
            make?.width.mas_equalTo()(self.iconImageView.mas_height)
        }
        
        heartButton.mas_makeConstraints { (make) in
            make?.right.mas_equalTo()(self.xcBackgroundView.mas_right)?.offset()(-10)
            make?.centerY.offset()
            make?.width.mas_equalTo()(25)
            make?.height.mas_equalTo()(25)
        }
        
        titleLabel.mas_makeConstraints { (make) in
            make?.left.mas_equalTo()(self.iconImageView.mas_right)?.offset()(10)
            make?.top.mas_equalTo()(self.xcBackgroundView.mas_top)?.offset()(10)
            make?.right.mas_equalTo()(self.heartButton.mas_left)?.offset()(-10)
            make?.height.mas_equalTo()(16)
        }
        
        subTitleLabel.mas_makeConstraints { (make) in
            make?.top.mas_equalTo()(self.titleLabel.mas_bottom)?.offset()(5)
            make?.left.mas_equalTo()(self.iconImageView.mas_right)?.offset()(10)
            make?.right.mas_equalTo()(self.heartButton.mas_left)?.offset()(-10)
            make?.height.mas_equalTo()(34)
        }
        
    }
    
    func refreshCellUI (){
        self.iconImageView.sd_setImage(with: URL(string: (self.xcHomePageFeedModel?.artworkUrl100)!)) { image, Error, Type, URL in
            
        }
        self.titleLabel.text = self.xcHomePageFeedModel?.trackName
        self.subTitleLabel.text = self.xcHomePageFeedModel?.xc_description
        if xcHomePageFeedModel?.xc_buttonStyle == .DEFAULT {
            heartButton.setImage(UIImage(named: "heart_icon"), for: .normal)
            heartButton.setImage(UIImage(named: "heart_icon"), for: .highlighted)
        } else if xcHomePageFeedModel?.xc_buttonStyle == .HIGH_LIGHT {
            heartButton.setImage(UIImage(named: "xc_read_heart"), for: .normal)
            heartButton.setImage(UIImage(named: "xc_read_heart"), for: .highlighted)
        }
    }
    
    /// 按钮点击事件
    @objc func xc_heartButtonEvent(){
        if self.heartRefreshBlock != nil {
            if xcHomePageFeedModel?.xc_buttonStyle == .DEFAULT {
                xcHomePageFeedModel?.xc_buttonStyle = .HIGH_LIGHT
                self.heartRefreshBlock!()
            } else if xcHomePageFeedModel?.xc_buttonStyle == .HIGH_LIGHT {
                xcHomePageFeedModel?.xc_buttonStyle = .DEFAULT
                self.heartRefreshBlock!()
            }
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
