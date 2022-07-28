//
//  BoolExt.swift
//  Assignment
//
//  Created by shinolr on 2022/7/28.
//

import Foundation

@propertyWrapper
struct DefaultFalse {
  var wrappedValue = false
}

extension DefaultFalse: Equatable {
  static func == (lhs: Self, rhs: Self) -> Bool {
    lhs.wrappedValue == rhs.wrappedValue
  }
}

extension DefaultFalse: Decodable {
  init(from decoder: Decoder) throws {
    let container = try decoder.singleValueContainer()
    wrappedValue = try container.decode(Bool.self)
  }
}

extension KeyedDecodingContainer {
  func decode(
    _ type: DefaultFalse.Type,
    forKey key: Key
  ) throws -> DefaultFalse {
    try decodeIfPresent(type, forKey: key) ?? .init()
  }
}
