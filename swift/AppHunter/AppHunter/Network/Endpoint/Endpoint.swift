//
//  Endpoint.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Foundation
enum AFHttpprotocol: String {
    case HTTPS = "https"
    case HTTP = "http"
}

protocol Endpoint {
    // HTTP or HTTPS
    var scheme: AFHttpprotocol { get }

    // host 例如 www.example.com 注意不带/
    var baseUrl: String { get }

    // 接口path 例如 "/a/list"
    var path: String { get }

    // 业务参数
    var parameters: [URLQueryItem]? { get }

    // 请求方法 注意保持和下层工具的rawValue保持一致，例如alamofire 或者urlsession
    var method: String { get }
}

extension Endpoint {
    var scheme: AFHttpprotocol {
        switch self {
        default:
            return .HTTPS
        }
    }

    var baseUrl: String {
        switch self {
        default:
            return "api.lil.software"
        }
    }

    var parameters: [URLQueryItem]? {
        return RequestParametersHelper.commonParamters()
    }

    var method: String {
        return "GET"
    }
}
