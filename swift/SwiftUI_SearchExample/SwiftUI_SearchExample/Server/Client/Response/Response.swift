import Foundation

/// 请求基本响应结构体
struct Response<T: Codable>: Codable {
    /// 状态码
    var code: Int?
    /// 消息
    var message: String?
    /// 请求数据
    var data: T?

}
