//
//  Request.swift
//  Assignment
//
//  Created by shinolr on 2022/7/27.
//

import Foundation

enum HTTPMethod: String {
    case GET
    case POST
}

protocol Request {
  associatedtype Parameter: Encodable
  associatedtype Response: Decodable
  
  var path: String { get }
  var method: HTTPMethod { get }
  var parameter: Parameter { get }
}

struct MockRequest: Request {
  static let initialLoad = MockRequest(parameter: .init())

  typealias Parameter = MockParameter
  typealias Response = Goods
  
  let path = ""
  let method = HTTPMethod.GET
  var parameter: MockParameter
}


struct MockParameter: Encodable {
  var limit = 10
  var page = 0
  
  var offset: Int { (page + 1) * limit }
}
