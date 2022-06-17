//
//  Api.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import Foundation

protocol APICall {
    var url: String { get }
    var method: String { get }
    var headers: [String: String]? { get }
    var dataKeyPath: String? { get }
}

enum APIError: Swift.Error {
    case invalidURL
    case httpCode(HTTPCode)
    case unexpectedResponse
}

extension APIError: LocalizedError {
    var errorDescription: String? {
        switch self {
        case .invalidURL: return "Invalid URL"
        case let .httpCode(code): return "Unexpected HTTP code: \(code)"
        case .unexpectedResponse: return "Unexpected response"
        }
    }
}

extension APICall {
    func urlRequest() throws -> URLRequest {
        guard let url = URL(string: url) else {
            throw APIError.invalidURL
        }
        var request = URLRequest(url: url)
        request.httpMethod = method
        request.allHTTPHeaderFields = headers        
        return request
    }
}

typealias HTTPCode = Int
typealias HTTPCodes = Range<HTTPCode>

extension HTTPCodes {
    static let success = 200 ..< 300
}
