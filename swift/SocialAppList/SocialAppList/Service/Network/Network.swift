//
//  Network.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import Moya
import Alamofire
import Combine

final class Network {
    static var config = Config()
    
    private var provider: MoyaProvider<MultiTarget>?
    
    private static var globalErrorCallback: ((_ error: Network.Error) -> Void)?
    
    private let networkPlugins: [PluginType]
    
    init() {
        // 全局网络错误处理回调
        let networkErrorPlugin = NetworkErrorHandlerPlugin { result, target in
            if let error = Network.Error.mappingFromMoya(result, target) {
                Network.globalErrorCallback?(error)
            }
        }
        
        // 接口超时时间设置
        let timeout = NetworkTimeoutPlugin()
        
        networkPlugins = [networkErrorPlugin, timeout]
    }
    
    private func defaultProvider(stubClosure: @escaping MoyaProvider<MultiTarget>.StubClosure = MoyaProvider.neverStub) -> MoyaProvider<MultiTarget>
    {
        if let provider = provider { return provider }
        let session = Session(configuration: defaultConfiguration(), startRequestsImmediately: false)
        provider = .init(stubClosure: stubClosure, session: session, plugins: networkPlugins)
        return provider!
    }
    
    private func defaultConfiguration() -> URLSessionConfiguration {
        let config = URLSessionConfiguration.default
        var defaultHTTPHeaders = HTTPHeaders.default.dictionary
        if var userAgent = defaultHTTPHeaders["User-Agent"] {
            userAgent += " MobileModel/\(API.Environment.HttpHeadersManager.mobileModel.replacingOccurrences(of: " ", with: "_"))"
            defaultHTTPHeaders["User-Agent"] = userAgent
        }
        config.httpAdditionalHeaders = defaultHTTPHeaders
        config.timeoutIntervalForRequest = Network.config.timeoutInterval
        return config
    }
    
    static func networkErrorHandler(_ handler: @escaping (_ error: Error) -> Void) {
        globalErrorCallback = handler
    }
    
    static func config(_ config: (Config) -> Void) {
        config(Network.config)
    }
    
    // MARK: network request
    
    func request(_ target: BaseTargetType,
                 stubClosure: @escaping MoyaProvider<MultiTarget>.StubClosure = MoyaProvider.neverStub)
    -> AnyPublisher<Response, MoyaError>
    {
        return defaultProvider(stubClosure: stubClosure).requestPublisher(MultiTarget(target))
    }
    
    func requestWithProgress(_ target: BaseTargetType,
                             stubClosure: @escaping MoyaProvider<MultiTarget>.StubClosure = MoyaProvider.neverStub)
    -> AnyPublisher<ProgressResponse, MoyaError>
    {
        
        return defaultProvider(stubClosure: stubClosure).requestWithProgressPublisher(MultiTarget(target))
    }
}

extension Network {
    final class Config {
        // 默认所有接口超时时间 15秒
        private(set) var timeoutInterval: TimeInterval = 15
        
        private(set) var environment: API.Environment
                
        var constHeaders: [String: String] {
            return API.Environment.HttpHeadersManager.allHeaders
        }
        
        init() {
            environment = API.Environment.appStore
        }
        
        func resetCurrentDefaultEnvironment(_ env: API.Environment) {
            environment = env
        }
        
        var defaultEnvironment: API.Environment? {
            set {
                if let envi = newValue {
                    UserDefaults.standard.set(envi.type, forKey: UserDefaultsKeys.defaultEnvironmentType)
                    resetCurrentDefaultEnvironment(envi)
                } else {
                    UserDefaults.standard.set(nil, forKey: UserDefaultsKeys.defaultEnvironmentType)
                }
                UserDefaults.standard.synchronize()
            }
            get {
                if let type = UserDefaults.standard.string(forKey: UserDefaultsKeys.defaultEnvironmentType) {
                    return API.Environment(type: type)
                }
                return nil
            }
        }
        
        enum UserDefaultsKeys {
            static let defaultEnvironmentType = "SystemManager.default.API.Environment.Type"
        }
    }
}

