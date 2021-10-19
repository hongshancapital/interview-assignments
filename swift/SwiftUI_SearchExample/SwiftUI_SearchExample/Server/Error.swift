import Foundation

/// 错误的集合 简易例子
enum LocalErrorCode: Int {
    case PathNotFound           = -1        // 未发现路径
    case BadRequestURL          = -2        // URL错误
    case ResponseEmpty          = -3        // 数据为空
    case JSONParseFailure       = -4        // JSON解析失败
    case Unknow                 = -999      // 未知错误
    
    var msgValue: String {
        switch self {
        case .PathNotFound:
            return "未发现路径"
        case .BadRequestURL:
            return "URL错误"
        case .ResponseEmpty:
            return "数据为空"
        case .JSONParseFailure:
            return "JSON解析失败"
        case .Unknow:
            return "未知错误"
        }
    }
    
}

