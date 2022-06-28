//
// Created by Jeffrey Wei on 2022/6/28.
// 请求相关的实体struct和Error
//

import Foundation

struct Response<T: Decodable>: Decodable {
    var code: Int
    var message: String?
    var result: T?
}


struct BusinessError {
    enum ErrorType: Int {
        case `default` = 1
    }

    // 错误类型
    let type: ErrorType
    // 错误信息
    let message: String?

    init(code: Int, message: String?) {
        type = ErrorType(rawValue: code) ?? .default
        self.message = message
    }
}

extension BusinessError: LocalizedError {
    public var errorDescription: String? {
        message
    }
}