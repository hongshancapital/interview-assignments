//
//  AppError.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/7.
//

import Foundation

enum AppError: Error, Identifiable {
    
    var id: String { errorDescription }
    case networkFaild(Error)
}

extension AppError {
    var errorDescription: String {
        switch self {
        case .networkFaild(let error): return error.localizedDescription
        }
    }
}
