import Foundation

public protocol Networking {
    
    func request<T: Decodable>(_ urlRequest: URLRequest, response: @escaping (T?) -> Void)
}
