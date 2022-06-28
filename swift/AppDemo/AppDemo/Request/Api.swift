//
// Created by Jeffrey Wei on 2022/6/29.
// Api枚举,请求使用,实现Target协议后调用其sinkResultData函数即可获取解析完的数据
//

import Foundation

enum Api {
    case getDemoList(pageSize: Int, pageNum: Int)
}

extension Api: Target {
    var urlString: String {
        switch self {
        case .getDemoList:
            return "api/demo/getDemoList"
        }
    }
    var method: RequestMethod {
        switch self {
        case .getDemoList:
            return .get
        }
    }
    var data: [String: LosslessStringConvertible] {
        switch self {
        case .getDemoList(pageSize: let pageSize, pageNum: let pageNum):
            return ["pageSize": pageSize, "pageNum": pageNum]
        }
    }
}