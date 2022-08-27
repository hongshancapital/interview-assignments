//
//  AppError.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

enum AppError: Error, Identifiable {
    var id: String { localizedDescription }
    case error(Error)
}

extension Error {
    func asAppError() -> AppError {
        self as? AppError ?? AppError.error(self)
    }
}


extension AppError {
    var localizedDescription: String {
        switch self {
        case .error(let error): return "\(error.localizedDescription)"
        }
    }
}
