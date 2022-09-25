//
//  AppError.swift
//  App
//
//  Created by lizhao on 2022/9/24.
//

import Foundation

enum AppError: Error, Identifiable {
    var id: String { localizedDescription }
    
    case networkingFailed(Error)
}

extension AppError: LocalizedError {
    var localizedDescription: String {
        switch self {
        case .networkingFailed(let e):
            return e.localizedDescription
        }
    }
}
