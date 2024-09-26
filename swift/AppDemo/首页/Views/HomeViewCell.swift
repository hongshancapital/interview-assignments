//
//  HomeViewCell.swift
//  AppDemo
//
//  Created by 操喜平 on 2022/8/10.
//

import UIKit
typealias clickBlock = (Bool)->()



class HomeViewCell: UITableViewCell {
    
    @IBOutlet weak var titleLab: UILabel!
    
    @IBOutlet weak var subTitleLab: UILabel!
    
    @IBOutlet weak var iconView: UIImageView!
    
    @IBOutlet weak var fatherView: UIView!
    @IBOutlet weak var iconBtn: UIButton!
    var btnClickBlock: clickBlock!

    
    override func awakeFromNib() {
        super.awakeFromNib()
        fatherView.clipsToBounds = true
        fatherView.layer.cornerRadius = 10
        iconBtn.setImage(UIImage(named: "loveFull_ic"), for:.selected)
        iconBtn.setImage(UIImage(named: "loveLess_ic"), for:.normal)
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    @IBAction func click(_ sender: Any) {
        self.iconBtn.isSelected = !self.iconBtn.isSelected
        btnClickBlock(self.iconBtn.isSelected)
    }
}
