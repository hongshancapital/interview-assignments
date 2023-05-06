//
//  NetResponse.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import KakaJSON
import Moya
import Combine

extension MoyaError {
    var message: String {
        return (errorUserInfo[NSLocalizedDescriptionKey] as? String) ?? ""
    }
    
    var code: Int {
        return (errorUserInfo[NSUnderlyingErrorKey] as? NSError)?.code ?? -1
    }
}

extension Error {
    var message: String {
        return (self as? MoyaError)?.message ?? ""
    }
    
    var code: Int {
        return (self as? MoyaError)?.code ?? -1
    }
}

struct NetResponse<T: Convertible>: Convertible {
    init() {}

    var data: T?
    var message: String?
    var code: Int?

    func kj_modelKey(from property: Property) -> ModelPropertyKey {
        switch property.name {
        case "message": return ["message", "msg"]
        default: return property.name
        }
    }
}

@available(macOS 10.15, iOS 13.0, tvOS 13.0, watchOS 6.0, *)
extension AnyPublisher where Output == Response, Failure == MoyaError {
    func mapObjectStatus<T: Convertible>(type: T.Type = T.self) -> AnyPublisher<(Int, Bool, String, T?), MoyaError> {
        return mapRawData(type: type)
            .map { response in
                let code = response.code ?? -1
                let message = response.message ?? ""
                let data = response.data
                return (code, code == 0 || code == 200, message, data)
            }
            .eraseToAnyPublisher()
    }
    
    func mapStatus() -> AnyPublisher<(Int, Bool, String), MoyaError> {
        return mapRawData(type: Empty.self)
            .map { response -> (Int, Bool, String) in
                let code = Int(response.code ?? -1)
                return (code, code == 0 || code == 200, response.message ?? "")
            }
            .eraseToAnyPublisher()
    }
    
    func mapLogicStatus() -> AnyPublisher<(Bool, String), MoyaError> {
        return mapRawData(type: Empty.self)
            .map { response -> (Bool, String) in
                (response.code == 0 || response.code == 200, response.message ?? "")
            }
            .eraseToAnyPublisher()
    }

    func mapObject<T: Convertible>(type: T.Type = T.self) -> AnyPublisher<T, MoyaError> {
        return mapRawData(type: type)
            .tryMap { response -> T in
                if let data = response.data, let code = response.code, code == 0 || code == 200 {
                    return data
                }

                let code = response.code ?? -1
                let message = response.message ?? ("服务器连接有问题,请重试 " + " \(code)")
                throw NSError(domain: NSURLErrorDomain, code: code, userInfo: [NSLocalizedDescriptionKey: message])
            }
            .mapError { ($0 as? MoyaError) ?? .underlying($0, nil) }
            .eraseToAnyPublisher()
    }

    private func mapRawData<T: Convertible>(type: T.Type = T.self) -> AnyPublisher<NetResponse<T>, MoyaError> {
        return tryMap { moyaResponse -> NetResponse<T> in
            if moyaResponse.statusCode != 200 {
                throw NSError(
                    domain: NSURLErrorDomain,
                    code: moyaResponse.statusCode,
                    userInfo: [NSLocalizedDescriptionKey: "服务器连接有问题,请重试 " + " \(moyaResponse.statusCode)"]
                )
            }

            let json = (try? moyaResponse.mapJSON() as? [String: Any]) ?? [String: Any]()
            #warning("可能有数据解密逻辑")
            let response = json.kj.model(NetResponse<T>.self)
            return response
        }
        .mapError { ($0 as? MoyaError) ?? .underlying($0, nil) }
        .eraseToAnyPublisher()
    }
}

struct Empty: Convertible {}
