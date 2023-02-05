//
// Homework
// AppError.swift
//
// Created by wuyikai on 2022/4/29.
// 
// 

import Foundation

enum AppError: Error {
    case fileError
}

extension AppError: LocalizedError {
    var errorDescription: String? {
        switch self {
        case .fileError: return "文件操作错误"
        }
    }
}
