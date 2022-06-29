//
// Created by Jeffrey Wei on 2022/6/28.
// 请求相关的实体struct和Error
//

import Foundation

///
/// 后台返回的数据结构
///
struct Response<T: Decodable>: Decodable {
    // code码,为0则为正常响应,其它为失败,后台的兜底失败码为1
    var code: Int
    // 错误信息
    var message: String?
    // 业务数据
    var result: T?
}


struct BusinessError {
    enum ErrorType: Int {
        case `default` = 1
    }

    // 错误类型
    let type: ErrorType?
    // 错误信息
    let message: String?

    init(code: Int, message: String?) {
        type = ErrorType(rawValue: code)
        self.message = message
    }
}

extension BusinessError: LocalizedError {
    public var errorDescription: String? {
        message
    }
}