//
//  APIServer.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

extension Encodable {
    public func toData() -> Data? { return try? JSONEncoder().encode(self) }

    public func toDictionary() -> [String: Any] {
        return (try? JSONSerialization.jsonObject(with: JSONEncoder().encode(self))) as? [String: Any] ?? [:]
    }
}

public struct HTTPMethod: RawRepresentable, Equatable, Hashable {
    public static let get = HTTPMethod(rawValue: "GET")
    public static let post = HTTPMethod(rawValue: "POST")
    
    public let rawValue: String
    
    public init(rawValue: String) {
        self.rawValue = rawValue
    }
}

public protocol APIServerParameters: Codable { }
extension String: APIServerParameters { }

public struct APIEmptyParameters: APIServerParameters { }

public protocol APIServerResponse: Codable { }
extension Data: APIServerResponse { }
extension String: APIServerResponse { }

public protocol APIAction {
    associatedtype Parameters: APIServerParameters
    associatedtype Response: APIServerResponse
    
    var method: HTTPMethod { get }
    var host: String { get }
    var uri: String { get }
    var url: String { get }
    var httpHeaders: [String: String]? { get }
    var timeoutInterval: TimeInterval { get }
    
    var parameters: Parameters? { get }
}

public extension APIAction {
    var method: HTTPMethod { .get }
    var uri: String { "/" }
    var url: String { host + uri }
    var httpHeaders: [String: String]? { ["Content-Type": "application/json"] }
    var timeoutInterval: TimeInterval { 20 }
    var parameters: APIEmptyParameters? { nil }
}

public let AS = APIServer.shared

public class APIServer {
    public static let shared = APIServer()
    
    private var debug = false
    
    public func debug(_ value: Bool = true) -> Self {
        debug = value
        return self
    }
}

extension APIServer {
    public static func makeRequest<Action: APIAction>(action: Action) -> URLRequest? {
        guard let url: URL = URL(string: action.url), var urlComponents = URLComponents(url: url, resolvingAgainstBaseURL: true) else {
            return nil
        }
        if action.method == .get {
            urlComponents.queryItems = SHURLQueryItemEncoding.default.encode(parameters: action.parameters?.toDictionary())
        }
        //        if let headers = action.httpHeaders {
        //            defaultHttpHeader.merge(headers) { current, new in
        //                new
        //            }
        //        }
        let u = urlComponents.url!
        var request = URLRequest(url: u)
        request.httpMethod = action.method.rawValue
        request.allHTTPHeaderFields = action.httpHeaders
        request.timeoutInterval = action.timeoutInterval
        if action.method == .post {
            request.httpBody =  try? JSONEncoder().encode(action.parameters)
        }
        return request
    }
}

extension APIServer {
    @available(macOS 12.0, iOS 15.0, watchOS 8.0, tvOS 15.0, *)
    public func requestAsync<Action: APIAction>(action: Action) async throws -> Action.Response {
        let enableDEBUG = debug
        guard let request = Self.makeRequest(action: action) else {
            throw APIError.makeURLRqeusetError
        }

        do {
            let (data, urlResponse) = try await URLSession.shared.data(for: request, delegate: nil)
            if enableDEBUG {
                Self.debugInfo(action: action, data: data, urlResponse: urlResponse)
            }
            if Action.Response.self == Data.self {
                return data as! Action.Response
            } else if Action.Response.self == String.self {
                if let jsonString = String(data: data, encoding: .utf8) {
                    return jsonString as! Action.Response
                } else {
                    throw APIError.decodingDataToUTF8StringError
                }
            } else {
                let model = try JSONDecoder().decode(Action.Response.self, from: data)
                return model
            }
        } catch let error {
            throw APIError.requestingError(message: error.localizedDescription)
        }
    }
}

extension APIServer {
    fileprivate static func debugInfo<Action: APIAction>(action: Action, data: Data?, urlResponse: URLResponse?) {
        print("### APIServer DEBUG info start ###")
        print("urlResponse:")
        if let urlResponse = urlResponse {
            print(urlResponse)
        }
        print("parameters:")
        print(action.parameters?.toDictionary() ?? [String: Any]())
        print("response data:")
        if let data = data {
            print(String(data: data, encoding: .utf8) ?? "data is nil")
        } else {
            print("data is nil")
        }
        print("### APIServer DEBUG info end ###")
    }
}
