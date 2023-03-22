//
//  ApiRequest.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import Foundation

import Moya
import Alamofire

public class ValoranRequest: NSObject {
    let provider = MoyaProvider<ValoranServiceApi>()
    let isDebug: Bool
    @objc public init(with isDebug: Bool) {
        self.isDebug = isDebug
        super.init()
    }
    @objc public func request(
        url: URL,
        method: String,
        header: [String: String]?,
        parameters: [String: Any]?,
        success: @escaping ((String) -> Void),
        failure: @escaping ((NSError) -> Void)
    ) {
        var provider: MoyaProvider<ValoranServiceApi>!
        var api: ValoranServiceApi!
        if method.lowercased() == "post" {
            api = .post(url, header, parameters)
        } else if method.lowercased() == "get"  {
            api = .get(url, header, parameters)
        } else if method.lowercased() == "put"  {
            api = .put(url, header, parameters)
        } else if method.lowercased() == "delete"  {
            api = .delete(url, header, parameters)
        }

        provider.request(api) { result in
            switch result {
            case .success(let response):
                if let json = String(data: response.data, encoding: .utf8) {
                    success(json)
                } else {
                    let error = NSError(domain: "com.error.valoranServiceApi.request", code: -10086, userInfo: [NSLocalizedDescriptionKey: "json format error"])
                    failure(error)
                }
            case .failure(let error):
                failure(error as NSError)
            }
        }
    }
}

public enum ValoranServiceApi {
    case get(URL, [String: String]?, [String: Any]?)
    case post(URL, [String: String]?, [String: Any]?)
    case put(URL, [String: String]?, [String: Any]?)
    case delete(URL, [String: String]?, [String: Any]?)
}

extension ValoranServiceApi: TargetType {
    public var baseURL: URL {
        switch self {
        case let .get(url, _, _),
             let .post(url, _, _),
             let .put(url, _, _),
             let .delete(url, _, _):
            return url
        }
    }

    public var sampleData: Data {
        return Data()
    }

    public var task: Task {
        switch self {
        case let .get(_, _, parameters):
            return .requestParameters(parameters: parameters ?? [:], encoding: URLEncoding.queryString)
        case let .post(_, _, parameters):
            let jsonData = try? JSONSerialization.data(withJSONObject: parameters, options: [])
            return .requestData(jsonData ?? Data())
        case let .delete(_, _, parameters):
            return .requestParameters(parameters: parameters ?? [:], encoding: URLEncoding.queryString)
        case let .put(_, _, parameters):
            let jsonData = try? JSONSerialization.data(withJSONObject: parameters, options: [])
            return .requestData(jsonData ?? Data())
        }
    }

    public var validationType: ValidationType {
        return .successAndRedirectCodes
    }

    public var path: String {
        return ""
    }

    public var method: Moya.Method {
        switch self {
        case .get:
            return .get
        case .post:
            return .post
        case .delete:
            return .delete
        case .put:
            return .put
        }
    }

    public var headers: [String: String]? {
        switch self {
        case let .get(_, header, _),
             let .post(_, header, _),
             let .delete(_, header, _),
             let .put(_, header, _):
            return header
        }
    }
}
