//
//  HomeCell.swift
//  SwiftDemo
//
//  Created by AYX on 2022/3/11.
//

import UIKit
import Kingfisher

class HomeCell: BaseTableViewCell {
    @IBOutlet weak var imgView: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var subTitleLabel: UILabel!
    @IBOutlet weak var collectionButton: UIButton!
    var onButtonCompleted: ((_ isFlag: Bool?) -> Void)?
    
    var model: HomeModel? {
        didSet {
            self.loadData()
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        selectionStyle = .none
        imgView.layer.borderColor = R.color.imgborderColor()?.cgColor
    }
    
    private func loadData() {
        imgView.kf.indicatorType = .activity
        if let url = model?.artworkImageURL {
            imgView.kf.setImage(with: ImageResource(downloadURL: url))
        } else {
            
        }
        titleLabel.text = model?.trackCensoredName
        subTitleLabel.text = model?.description
    }

    @IBAction func collectionBtnAction(_ sender: UIButton) {
        onButtonCompleted?(sender.isSelected)
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        
    }
    
}
