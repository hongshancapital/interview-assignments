//
//  BaseTarget.swift
//  GCore
//
//  Created by lizhao on 2022/9/20.
//

import Moya

public struct NetworkConfiguration {
    public static let baseURL = URL(string: "https://mock.presstime.cn")!
    
    static let headers: [String: String]? = nil
    static let canShowErrorHUG: Bool = true
    
    public static func buildBaseURL(path: String) -> URL? {
        return URL(string: "\(baseURL.absoluteString)\(path.starts(with: "/") ? "" : "/")\(path)")
    }
}


public protocol BaseTargetType: TargetType, ErrorHandlerTargetType {
    
}

extension BaseTargetType {

    public var baseURL: URL {
        return NetworkConfiguration.baseURL
    }

    public var headers: [String: String]? {
        return NetworkConfiguration.headers
    }

    public var canShowErrorHUG: Bool {
        return NetworkConfiguration.canShowErrorHUG
    }

    public var sampleData: Data {
        return Data()
    }

    public func canDisplayHUGForError(_ error: Error) -> Bool {
        return true
    }

    public func toRequest(enddingPoint: MoyaProvider<Self>.EndpointClosure = MoyaProvider.defaultEndpointMapping) -> URLRequest? {
        return try? enddingPoint(self).urlRequest()
    }

}
