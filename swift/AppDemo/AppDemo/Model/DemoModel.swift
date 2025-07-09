//
// Created by Jeffrey Wei on 2022/6/27.
// 实体对象,和数据库数据关联
//

import Foundation

struct DemoModel: Identifiable, Decodable {
    var id: Int
    // 标题
    var artistName: String
    // 描述
    var description: String
    // 图像地址
    var icon: String
    // 是否收藏
    var isCollected: Bool = false
}
