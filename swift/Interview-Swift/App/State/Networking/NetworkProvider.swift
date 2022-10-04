//
//  NetworkProvider.swift
//  App
//
//  Created by lizhao on 2022/9/20.
//

import Moya
import GCore
import Alamofire


final class NetworkErrorHUG: ErrorHUGType {
    func show(error: Error) {
        let nsError = error as NSError
        ConsoleLog.error("Error Domain=\(nsError.domain) Code=\(nsError.code)\n",
            context: nsError.userInfo.map({ "\t\($0.key): \($0.value)" }).joined(separator: "\n"))
 
        switch nsError.code {
        case 400, 404, 401:
            ConsoleLog.error(error)
        default:
            ConsoleLog.debug(error)
        }
    }
}

struct NetworkProvider {
    private static let defaultAlamofireManager: Alamofire.Session = {
        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForRequest = 15
        configuration.timeoutIntervalForResource = 15
        configuration.requestCachePolicy = .reloadIgnoringCacheData
        
        let manager = Alamofire.Session(configuration: configuration)
 
        return manager
    }()
    
    static private(set) var extraHeader = buildExtraHeader(customerId: nil)

    static func updateHeader(customerId: ModelSQLID?) {
        extraHeader = buildExtraHeader(customerId: customerId)
    }

    static private func buildExtraHeader(customerId: ModelSQLID?) -> [String: String] {
        var header = [String: String]()
        header["Accept"] = "application/json, text/plain,image/webp, */*"
        #if DEBUG
        header["X-Beta"] = "true"
        #endif
        header["X-Client"] = "iOS/1.2.3"
        return header
    }
    
    static func build<T: TargetType>(_ type: T.Type = T.self, plugins: [PluginType]) -> MoyaProvider<T> {
        return MoyaProvider<T>(endpointClosure: defaultEndpointMapping,
            stubClosure: MoyaProvider.neverStub,
            callbackQueue: DispatchQueue.global(qos: DispatchQoS.QoSClass.default),
            session: defaultAlamofireManager,
            plugins: plugins)
    }
    
    static func build<T: TargetType>(_ type: T.Type = T.self) -> MoyaProvider<T> {
        let plugins: [PluginType] = [
            ErrorHandlerPlugin(errorHUG: NetworkErrorHUG())
        ]
        return NetworkProvider.build(type, plugins: plugins)
    }
    
    
    private static func defaultEndpointMapping<Target: TargetType>(for target: Target) -> Endpoint {
        let url = URL(target: target)
        ConsoleLog.verbose("<HTTP> request", context: url)
        let header = (target.headers ?? [:]).merging(extraHeader, uniquingKeysWith: { (_, new) in new })
        return Endpoint(
            url: url.absoluteString,
            sampleResponseClosure: { .networkResponse(200, target.sampleData) },
            method: target.method,
            task: target.task,
            httpHeaderFields: header
        )
    }
}
