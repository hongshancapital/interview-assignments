//
//  AppError.swift
//  Demo
//
//  Created by Kai on 2022/2/22.
//

import Foundation

enum AppError: Error, Identifiable {
    var id: String {localizedDescription}
    case loadListFailed(Error)
    case changeLikeWrong
}

extension AppError: LocalizedError {
    var localizedDescription: String {
        switch self {
        case .loadListFailed(let error): return error.localizedDescription
        case .changeLikeWrong: return "失败"
        }
    }
}
