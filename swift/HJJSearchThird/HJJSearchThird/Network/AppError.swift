//
//  AppError.swift
//  HJJSearchUpgrade
//
//  Created by haojiajia on 2021/1/4.
//

import Foundation

enum AppError: Error, Identifiable {
    var id: String { localizedDescription }
    case serverConnectFail
    case fileError
    case networkingFailed(Error)
}

extension AppError: LocalizedError {
    var localizedDescription: String {
        switch self {
            case .serverConnectFail: return "服务器连接失败"
            case .fileError: return "文件操作错误"
        case .networkingFailed( _): return "网络错误"
        }
    }
}
