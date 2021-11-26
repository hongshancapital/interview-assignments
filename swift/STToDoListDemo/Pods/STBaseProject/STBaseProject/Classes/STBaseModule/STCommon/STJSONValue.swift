//
//  STJSONValue.swift
//  STBaseProject
//
//  Created by song on 2020/5/26.
//  Copyright © 2020 ST. All rights reserved.
//

import Foundation

public enum STJSONValue: Decodable {
    
    case int(Int)
    case bool(Bool)
    case double(Double)
    case string(String)
    case array([STJSONValue])
    case object([String: STJSONValue])

    public init(from decoder: Decoder) throws {
        let container = try decoder.singleValueContainer()
        self = try ((try? container.decode(String.self)).map(STJSONValue.string))
            .or((try? container.decode(Int.self)).map(STJSONValue.int))
            .or((try? container.decode(Double.self)).map(STJSONValue.double))
            .or((try? container.decode(Bool.self)).map(STJSONValue.bool))
            .or((try? container.decode([String: STJSONValue].self)).map(STJSONValue.object))
            .or((try? container.decode([STJSONValue].self)).map(STJSONValue.array))
            .resolve(with: DecodingError.typeMismatch(STJSONValue.self, DecodingError.Context(codingPath: container.codingPath, debugDescription: "Not a JSON")))
    }
}

extension Optional {
    func or(_ other: Optional) -> Optional {
        switch self {
        case .none: return other
        case .some: return self
        }
    }
    
    func resolve(with error: @autoclosure () -> Error) throws -> Wrapped {
        switch self {
        case .none: throw error()
        case .some(let wrapped): return wrapped
        }
    }
}
