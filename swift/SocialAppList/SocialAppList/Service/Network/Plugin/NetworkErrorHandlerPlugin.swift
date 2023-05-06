//
//  NetworkErrorHandlerPlugin.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import Moya

class NetworkErrorHandlerPlugin: PluginType {
    typealias HandlerClosure = (_ result: Result<Moya.Response, Moya.MoyaError>, _ target: TargetType) -> Void

    var handlerClosure: HandlerClosure

    init(handler: @escaping HandlerClosure) {
        handlerClosure = handler
    }

    func didReceive(_ result: Result<Moya.Response, Moya.MoyaError>, target: TargetType) {
        handlerClosure(result, target)
    }
}
