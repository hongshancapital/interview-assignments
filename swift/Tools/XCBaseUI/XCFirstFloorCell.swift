//
//  XCFirstFloorCell.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit
import Masonry

class XCFirstFloorCell: XCBaseTableViewCell {

    var xcFirstFloorModel : XCFirstFloorModel?
    
    lazy var titleLabel : UILabel = {
        let label = UILabel.init()
        label.backgroundColor = UIColor.clear
        label.textColor = UIColor.black
        label.font = UIFont.systemFont(ofSize: 36, weight: .bold)
        label.textAlignment = NSTextAlignment.left
        label.numberOfLines = 0
        return label
    }()
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.backgroundColor = UIColor(red: 244/255, green: 243/255, blue: 247/255, alpha: 0.5)
        
        contentView.addSubview(titleLabel)

        titleLabel.mas_makeConstraints { (make) in
            make?.top.mas_equalTo()(self.mas_top)?.offset()(110)
            make?.left.mas_equalTo()(self.mas_left)?.offset()(16)
            make?.width.mas_equalTo()(200)
            make?.height.mas_equalTo()(45)
        }
    }
    
    func refreshCellUI (){
        self.titleLabel.text = self.xcFirstFloorModel?.titleString
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
