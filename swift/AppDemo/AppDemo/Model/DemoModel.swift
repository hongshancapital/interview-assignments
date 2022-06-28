//
// Created by Jeffrey Wei on 2022/6/27.
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

    init(id: Int, artistName: String, description: String, icon: String) {
        self.id = id
        self.artistName = artistName
        self.description = description
        self.icon = icon
    }
}
