//
//  AppError.swift
//  AppList
//
//  Created by haojiajia on 2022/7/9.
//

import Foundation

enum AppError: Error, Identifiable {
    var id: String { localizedDescription }
    case networkingFailed(Error)
}

extension AppError: LocalizedError {
    var localizedDescription: String {
        switch self {
        case .networkingFailed(let error): return error.localizedDescription
        }
    }
}

