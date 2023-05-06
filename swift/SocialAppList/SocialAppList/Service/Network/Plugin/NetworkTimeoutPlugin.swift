//
//  NetworkTimeoutPlugin.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import Moya

final class NetworkTimeoutPlugin: PluginType {
    func prepare(_ request: URLRequest, target: TargetType) -> URLRequest {
        if let target = (target as? MultiTarget)?.target as? BaseTargetType {
            var mutableRequest = request
            mutableRequest.timeoutInterval = target.timeoutInterval
            return mutableRequest
        }
        return request
    }
}
