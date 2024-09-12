import Foundation

public typealias Parameters = [String: Any]
public typealias HTTPHeaders = [String: String]

public protocol HTTPRequest {
    var baseURL: URL? { get }
    var path: String { get }
    var method: HTTPMethod { get }
    var parameters: Parameters? { get }
    var headers: HTTPHeaders? { get }
}

public extension HTTPRequest {
    var baseURL: URL? { return nil }
    var method: HTTPMethod { return .get }
    var parameters: Parameters? { return nil }
    var headers: HTTPHeaders? { return nil }
}
