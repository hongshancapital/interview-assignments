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
    var data: T?
}


struct BusinessError {
    enum ErrorType: Int {
        // 后台的错误码,简易搭建的后台,目前所有的抛错统一为code是1,暂无其它错误码
        case `default` = 1
    }

    // 错误类型
    let type: ErrorType?
    // 后台的错误描述,简易搭建的后台,目前所有的业务抛错message统一为'后台异常,请稍后重试'
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