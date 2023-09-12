//
//  APIError.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Foundation
enum APIError: Error {
    case decodingError
    case errorCode(Int)
    case unknown
}

extension APIError: LocalizedError {
    var errorDescription: String? {
        switch self {
        case .decodingError:
            return "Failed to decode the object from the service"
        case let .errorCode(code):
            return "\(code) - Something went wrong"
        case .unknown:
            return "The error is unknown"
        }
    }
}
