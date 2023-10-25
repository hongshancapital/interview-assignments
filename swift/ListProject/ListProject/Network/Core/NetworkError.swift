//
//  NetworkError.swift
//  Network
//
//  Created by shencong on 2022/6/10.
//

import Foundation
import Metal

enum NetworkError: Error, CustomStringConvertible {

    case URLError
    case DecodeError
    case EncodeError
    case notNetwork
    case ResponseError(error: Error)
    case unknown

    var description: String {
        switch self {
        case .URLError:
            return "无效URL"
        case .ResponseError(let error):
            return "网络错误：\(error.localizedDescription)"
        case .DecodeError:
            return "解码错误"
        case .EncodeError:
            return "编码错误"
        case .notNetwork:
            return "无网络连接"
        case .unknown:
            return "未知错误"
        }
    }
}
