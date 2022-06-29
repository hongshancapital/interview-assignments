//
// Created by Jeffrey Wei on 2022/6/29.
// Api枚举,请求使用,实现Target协议后调用其sinkResultData函数即可获取解析完的数据
//

import Foundation

enum Api {
    // 获取demo列表
    case getDemoList(pageSize: Int, pageNum: Int)
    // 根据id给demo对象收藏
    case doCollected(id: Int, isCollected: Bool)
}

extension Api: Target {
    var urlString: String {
        switch self {
        case .getDemoList:
            return "api/demo/getDemoList"
        case .doCollected:
            return "/api/demo/doCollected"
        }
    }
    var method: RequestMethod {
        switch self {
        case .getDemoList:
            return .get
        case .doCollected:
            return .post
        }
    }
    var data: [String: LosslessStringConvertible] {
        switch self {
        case .getDemoList(pageSize: let pageSize, pageNum: let pageNum):
            return ["pageSize": pageSize, "pageNum": pageNum]
        case .doCollected(id: let id, isCollected: let isCollected):
            return ["id": id, "isCollected": isCollected]
        }
    }
}