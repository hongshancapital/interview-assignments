//
//  AppError.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Foundation

enum AppError: Error, Identifiable {
    var id: String { localizedDescription }

    case networkingFailed(Error)
    case fileError
}

extension AppError: LocalizedError {
    var localizedDescription: String {
        switch self {
        case .networkingFailed(let error): return error.localizedDescription
        case .fileError: return "文件操作错误"
        }
    }
}
