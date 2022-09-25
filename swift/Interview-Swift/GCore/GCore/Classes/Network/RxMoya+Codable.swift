//
//  RxMoya+Codable.swift
//  GCore
//
//  Created by lizhao on 2022/9/20.
//
 
import Moya
import Combine
 

private struct ResponseWrapper<T: Decodable>: Decodable {
    let code: Int?
    let message: String?
    let data: T?
}

public enum OptionalDecodeWrapper<T: Decodable>: Decodable {
    case none
    case some(T)
    public var value: T? {
        switch self {
        case .some(let v):
            return v
        case .none:
            return nil
        }
    }

    public init(from decoder: Decoder) throws {
        do {
            let container = try decoder.singleValueContainer()
            let value = try container.decode(T.self)
            self = .some(value)
        } catch {
            #if DEBUG
            ConsoleLog.error("<JSONDecoder> Error", context: error)
            #endif
            self = .none
        }
    }
}

 

public let MoyaProviderDefaultNetRequestQueue = DispatchQueue(label: "\(NetworkConfiguration.baseURL.absoluteString)", qos: .default, attributes: .concurrent, autoreleaseFrequency: .inherit, target: nil)

public extension MoyaProvider {
    private func modelWrapperPublisher<T: Decodable>(_ token: Target) -> AnyPublisher<ResponseWrapper<T>, Error> {
        self.requestPublisher(token)
            .map { $0.data }
            .decode(type: ResponseWrapper<T>.self, decoder: JSONDecoder())
            .eraseToAnyPublisher()
    }
    
    func modelRequest<T: Decodable>(_ token: Target, model: T.Type = T.self) -> AnyPublisher<T, Error> {
        return self.modelWrapperPublisher(token)
            .compactMap { $0.data }
            .eraseToAnyPublisher()
    }
    
    func arrayRequest<T: Decodable>(_ token: Target, model: T.Type = T.self, key: String) -> AnyPublisher<[T], Error> {
        return self.modelRequest(token, model: [String: OptionalDecodeWrapper<[T]>].self)
            .map{ $0[key]?.value ?? [] }
            .eraseToAnyPublisher()
    }
}


