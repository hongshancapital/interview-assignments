//
//  Client.swift
//  Assignment
//
//  Created by shinolr on 2022/7/27.
//

import Foundation

protocol Client {
  var host: String { get }
  func fetchResult<R>(with request: R) async -> Result<R.Response, GeneralError> where R : Request
}

enum GeneralError: Error {
  case networkFailure(Int)
  case invalidResponse
  case invalidData
  case nestedError(Error)
}

struct URLSessionClient: Client {
  let host = "-- real host --"
  
  func fetchResult<R>(with request: R) async -> Result<R.Response, GeneralError> where R : Request {
    let url = URL(string: host.appending(request.path))!
    var urlRequest = URLRequest(url: url)
    urlRequest.httpMethod = request.method.rawValue
    
    let data: Data
    let response: URLResponse
    
    do {
      (data, response) = try await URLSession.shared.data(for: urlRequest)
    } catch {
      return .failure(.nestedError(error))
    }
    
    guard let response = response as? HTTPURLResponse else {
      return .failure(.invalidResponse)
    }
    
    guard 200 ..< 300 ~= response.statusCode else {
      return .failure(.networkFailure(response.statusCode))
    }
    
    do {
      return .success(try data.decoded())
    } catch {
      return .failure(.nestedError(error))
    }
  }
}

// MARK: - Mock

struct MockDataClient: Client {
  private init() {}
  
  static var shared = MockDataClient()
  
  let host = ""
  
  func fetchResult<R>(with request: R) async -> Result<R.Response, GeneralError> where R : Request {
    let path = Bundle.main.url(forResource: "Apps", withExtension: "json")!
    
    let data: Data
    do {
      data = try Data(contentsOf: path)
    } catch {
      return .failure(.invalidData)
    }
    
    do {
      return .success(try data.decoded())
    } catch {
      return .failure(.nestedError(error))
    }
  }
}
