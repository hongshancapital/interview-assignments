//
//  DataExt.swift
//  Assignment
//
//  Created by shinolr on 2022/7/27.
//

import Foundation

extension Data {
  func decoded<T: Decodable>(_ decoder: JSONDecoder = .init()) throws -> T {
    try decoder.decode(T.self, from: self)
  }
}
