//
//  BaseTargetType.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import Moya

protocol BaseTargetType: TargetType {
    var route: Route { get }
    
    var timeoutInterval: TimeInterval { get }
}

extension BaseTargetType {
    var path: String {
        return route.path
    }
    
    var method: Moya.Method {
        return route.method
    }
    
    var headers: [String: String]? {
        return Network.config.constHeaders
    }
    
    var timeoutInterval: TimeInterval {
        return 15
    }
}

enum Route {
    case get(String)
    case post(String)
    case put(String)
    case delete(String)
    case options(String)
    case head(String)
    case patch(String)
    case trace(String)
    case connect(String)
    
    public var path: String {
        switch self {
        case .get(let path): return path
        case .post(let path): return path
        case .put(let path): return path
        case .delete(let path): return path
        case .options(let path): return path
        case .head(let path): return path
        case .patch(let path): return path
        case .trace(let path): return path
        case .connect(let path): return path
        }
    }
    
    public var method: Moya.Method {
        switch self {
        case .get: return .get
        case .post: return .post
        case .put: return .put
        case .delete: return .delete
        case .options: return .options
        case .head: return .head
        case .patch: return .patch
        case .trace: return .trace
        case .connect: return .connect
        }
    }
}

