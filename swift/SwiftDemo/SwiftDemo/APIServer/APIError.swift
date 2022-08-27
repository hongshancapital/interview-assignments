//
//  APIError.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

public enum APIError: Error {
    case makeURLRqeusetError
    case requestingError(message: String)
    case decodingDataToUTF8StringError
    case noResponseData
}

extension APIError: LocalizedError  {
    
    /// A localized message describing what error occurred.
    public var errorDescription: String? {
        switch self {
        case .makeURLRqeusetError:
            return "make URLRequest error"
        case .requestingError(let message):
            return "requesting error: \(message)"
        case .decodingDataToUTF8StringError:
            return "decoding data to utf8 string error"
        case .noResponseData:
            return "no response data"
        }
    }

    /// A localized message describing the reason for the failure.
    public var failureReason: String? { errorDescription }

    /// A localized message describing how one might recover from the failure.
    public var recoverySuggestion: String? { nil }

    /// A localized message providing "help" text if the user requests help.
    public var helpAnchor: String? { nil }
}
