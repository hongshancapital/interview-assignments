//
//  XCFirstFloorModel.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit

class XCFirstFloorModel: XCBaseModel {
    
    @objc var titleString : String?
    
    override init() {
        super.init()
        self.xcCellHeigh = 160
        self.titleString = "App"
    }
    
}
