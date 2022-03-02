//
//  CellData.swift
//  Example
//
//  Created by 聂高涛 on 2022/3/2.
//

import UIKit

// 数据模型
struct CellData: Identifiable, Hashable {
    var id = UUID()
    var url = ""
    var title = ""
    var subtitle = ""
    var isSelected = false
    var networkImage : UIImage? = nil
}
