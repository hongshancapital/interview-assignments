//
//  AppError.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//

import Foundation

enum AppError: Error, Identifiable {
    var id: String { localizedDescription }

    case networkingFailed(Error)
    case dataStorageError
}

extension AppError: LocalizedError {
    var localizedDescription: String {
        switch self {
        case .networkingFailed(let error): return error.localizedDescription
        case .dataStorageError: return "Cache Data Error"
        }
    }
}
