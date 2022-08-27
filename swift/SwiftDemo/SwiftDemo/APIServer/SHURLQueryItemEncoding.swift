//
//  SHURLQueryItemEncoding.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

public struct SHURLQueryItemEncoding {
    public enum BoolEncoding {
        case numeric
        case literal
        
        func encode(value: Bool) -> String {
            switch self {
            case .numeric:
                return value ? "1" : "0"
            case .literal:
                return value ? "true" : "false"
            }
        }
    }
    
    public enum ArrayEncoding {
        case brackets
        case noBrackets
        case indexInBrackets
        
        func encode(key: String, at index: Int) -> String {
            switch self {
            case .brackets:
                return "\(key)[]"
            case .noBrackets:
                return key
            case .indexInBrackets:
                return "\(key)[\(index)]"
            }
        }
    }
    public static var `default`: SHURLQueryItemEncoding { SHURLQueryItemEncoding() }

    
    public var boolEncoding: BoolEncoding
    public var arrayEncoding: ArrayEncoding
    
    public init(boolEncoding: BoolEncoding = .numeric,
                arrayEncoding: ArrayEncoding = .brackets) {
        self.boolEncoding = boolEncoding
        self.arrayEncoding = arrayEncoding
    }
    
    public func encode(parameters: [String: Any]?) -> [URLQueryItem] {
        guard let parameters = parameters else {
            return []
        }
        var items: [URLQueryItem] = []
        for key in parameters.keys.sorted(by: <) {
            if let value = parameters[key] {
                items += queryItems(key: key, value: value)
            }
        }
        return items
    }

    public func queryItems(key: String, value: Any) -> [URLQueryItem] {
        var items: [URLQueryItem] = []
        switch value {
        case let dictionary as [String: Any]:
            for  (k, v) in dictionary {
                items += queryItems(key: "\(key)[\(k)]", value: v)
            }
        case let array as [Any]:
            for  (i , v) in array.enumerated() {
                items += queryItems(key: arrayEncoding.encode(key: key, at: i), value: v)
            }
        case let bool as Bool:
            items.append(URLQueryItem(name: key, value: boolEncoding.encode(value: bool)))
        case let number as Int:
            items.append(URLQueryItem(name: key, value: "\(number)"))
        case let number as Float:
            items.append(URLQueryItem(name: key, value: "\(number)"))
        case let number as Double:
            items.append(URLQueryItem(name: key, value: "\(number)"))
        case let string as String:
            items.append(URLQueryItem(name: key, value: string))
        default: items.append(URLQueryItem(name: key, value: "\(value)"))
        }
        return items
    }
}
