//
//  Network+Error.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import Moya

extension Network {
    enum Error: Swift.Error {
        case network(BaseTargetType, Moya.Response?, NSError?)
        
        case servers(BaseTargetType, Moya.Response?, NSError?)
        
        case businessLogic(BaseTargetType, Moya.Response, Int, String?)
        
        case develop(BaseTargetType, Moya.Response?, NSError?, message: String?)
    }
}

extension Network.Error {
    static func mappingFromMoya(_ result: Result<Moya.Response, Moya.MoyaError>, _ target: TargetType) -> Network.Error? {
        let realTarget: BaseTargetType?
        
        if let target = target as? MultiTarget {
            realTarget = target.target as? BaseTargetType
        } else {
            realTarget = target as? BaseTargetType
        }
                
        guard let target = realTarget else { return nil }
        
        let result = result.flatMap { response -> Result<(Moya.Response, Int, String?), Moya.MoyaError> in
            do {
                let json = try response.filterSuccessfulStatusAndRedirectCodes().mapJSON() as? [String: Any]
                
                guard let code = json?["code"] as? Int else {
                    throw MoyaError.jsonMapping(response)
                }
                
                let msg = json?["message"] as? String
                return Result.success((response, code, msg))
                
            } catch let error as MoyaError {
                return Result.failure(error)
                
            } catch {
                return Result.failure(MoyaError.underlying(error, nil))
            }
        }
        
        switch result {
        case let .success((response, code, message)):
            if code == 0 || code == 200 { return nil }
            
            return .businessLogic(target, response, code, message)
            
        case let .failure(moyaError):
            switch moyaError {
            case .requestMapping, .parameterEncoding, .objectMapping, .encodableMapping:
                return .develop(target, nil, moyaError as NSError, message: moyaError.errorDescription)
            default:
                let error = moyaError.errorUserInfo[NSUnderlyingErrorKey] as? NSError
                let response = moyaError.response
                
                guard let networkError = error, networkError.domain == NSURLErrorDomain else {
                    return .servers(target, response, error)
                }
                
                return .network(target, response, networkError)
            }
        }
    }
}

