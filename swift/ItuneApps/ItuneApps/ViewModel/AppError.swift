//
//  AppError.swift
//  ItuneApps
//
//  Created by daicanglan on 2023/3/27.
//

import Foundation
import SwiftUI
import Combine

enum AppError: Error, Identifiable {
    var id: String { desc }
    case networkFailed(_ error: Error)
}


extension AppError: LocalizedError {
    var desc: String {
        switch self {
        case .networkFailed(let err):
            let error = err as NSError
            if error.domain == NSURLErrorDomain {
                switch error.code {
                case NSURLErrorTimedOut:
                    return "超时"
                case NSURLErrorBadURL:
                    return "格式错误的URL阻止了URL请求的启动"
                case NSURLErrorCancelled:
                    return "请求被取消"
                case NSURLErrorCannotFindHost:
                    return "无法解析URL的主机名"
                default:
                    return "Unkonwn Error"
                }
            } else {
                return self.localizedDescription
            }
        }
    }
}
