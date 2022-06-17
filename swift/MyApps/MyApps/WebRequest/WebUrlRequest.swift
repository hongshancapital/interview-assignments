//
//  WebUrlRequest.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import Foundation
import Combine

protocol WebUrlRequest {
    var session: URLSession { get }    
    var bgQueue: DispatchQueue { get }
}

extension WebUrlRequest {
    func call<Value>(api: APICall, httpCodes: HTTPCodes = .success) -> AnyPublisher<Value, Error>
    where Value: Decodable {
        do {
            let request = try api.urlRequest()
            return session
                .dataTaskPublisher(for: request)
                .requestJSON(httpCodes: httpCodes, dataKeyPath:api.dataKeyPath)
        } catch let error {
            return Fail<Value, Error>(error: error).eraseToAnyPublisher()
        }
    }
}


private extension Publisher where Output == URLSession.DataTaskPublisher.Output {
    func requestJSON<Value>(httpCodes: HTTPCodes, dataKeyPath:String? = nil) -> AnyPublisher<Value, Error> where Value: Decodable {
        
        return tryMap {
            assert(!Thread.isMainThread)
            guard let code = ($0.response as? HTTPURLResponse)?.statusCode else {
                throw APIError.unexpectedResponse
            }
            guard httpCodes.contains(code) else {
                throw APIError.httpCode(code)
            }
            if let keyPath = dataKeyPath {
                let data = try JSONSerialization.jsonObject(with: $0.data) as? [AnyHashable:Any]
                guard let result = data?[keyPath] else {
                    return Data()
                }
                
                do {
                    return try JSONSerialization.data(withJSONObject: result)
                } catch  {
                    throw APIError.unexpectedResponse
                }
                
            } else {
                return $0.data
            }
        }.extractUnderlyingError()
        .decode(type: Value.self, decoder: JSONDecoder())
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
}

private extension Publisher {
    func extractUnderlyingError() -> Publishers.MapError<Self, Failure> {
        mapError {
            ($0.underlyingError as? Failure) ?? $0
        }
    }
}

private extension Error {
    var underlyingError: Error? {
        let nsError = self as NSError
        if nsError.domain == NSURLErrorDomain && nsError.code == -1009 {
            // "no internet connection"
            return self
        }
        return nsError.userInfo[NSUnderlyingErrorKey] as? Error
    }
}
