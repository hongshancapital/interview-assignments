//
//  XCHomePageFeedModel.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit
import MJExtension

/// 按钮状态
/// DEFAULT（灰色）
/// DEFAULT（红色）
enum HeartButtonType : String {
    case DEFAULT = "DEFAULT"
    case HIGH_LIGHT = "HIGH_LIGHT"
}

/// 首页Feel流Model
class XCHomePageFeedModel: XCBaseModel {
    // 头像
    @objc var artworkUrl100 : String?
    // 主标题
    @objc var trackName : String?
    // 副标题
    @objc var xc_description : String?
    // 按钮状态
    var xc_buttonStyle : HeartButtonType?
    
    override init() {
        super.init()
        self.xcCellHeigh = 80
        // 由于是本地假数据，所以才用了默认为灰色（如果是真实场景可以有以下两种方案）
        // (1)每次以后端数据为准，每次点击请求后端，更改对应楼层（产品id）收藏状态
        // (2)本地添加数据库系统，用于存储展示（本地假数据）
        xc_buttonStyle = .DEFAULT
    }
    
    // 字段名称和系统保留名称冲突
    override class func mj_replacedKeyFromPropertyName() -> [AnyHashable : Any]! {
        return ["xc_description": "description"]
    }
}
