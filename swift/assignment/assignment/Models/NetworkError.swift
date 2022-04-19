//
//  NetworkError.swift
//  assignment
//
//  Created by 干饭人肝不完DDL on 2022/4/19.
//

import Foundation


enum NetWorkError: Error, CustomStringConvertible {
    case URLError
    case DecodeError
    case ResponseError(error: Error)
    case unknown
    
    var description: String {
        switch self {
        case .URLError:
            return "Invalid URL"
        case .ResponseError(let error):
            return "Net Error: \(error.localizedDescription)"
        case .DecodeError:
            return "Cannot decode"
        case .unknown:
            return "Unknown Error"
        }
    }
    
}
