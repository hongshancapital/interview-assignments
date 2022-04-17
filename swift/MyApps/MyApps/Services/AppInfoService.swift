//
//  AppInfoServiceProtocol.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import Foundation
import Combine

protocol AppInfoServiceProtocol {
    
    /// get app list
    /// - Parameters:
    ///   - pageIndex: page index
    ///   - count: max count of apps
    func fetchMyApps(pageIndex:UInt, count:UInt) -> AnyPublisher<[AppInfo],Error>
    
    
    /// update collected state
    /// - Parameters:
    ///   - state: target value,  update to true or false
    ///   - app: the app need to be updated
    func updateAppCollectedSate(_ state: Bool, app:AppInfo) -> AnyPublisher<Bool,Error>
}


class AppInfoService: AppInfoServiceProtocol, WebUrlRequest {
    var session: URLSession {
        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForRequest = 60
        configuration.timeoutIntervalForResource = 120
        configuration.waitsForConnectivity = true
        configuration.httpMaximumConnectionsPerHost = 5
        configuration.requestCachePolicy = .returnCacheDataElseLoad
        configuration.urlCache = .shared
        return URLSession(configuration: configuration)
    }
    
    var bgQueue: DispatchQueue = DispatchQueue(label: "my_app_reuqest_queue")
    
    
    func fetchMyApps(pageIndex: UInt, count: UInt) -> AnyPublisher<[AppInfo], Error> {
        
        return call(api: API.appList)
    }
    
    func updateAppCollectedSate(_ state: Bool, app:AppInfo) -> AnyPublisher<Bool,Error> {
        return call(api: API.updateAppCollectedState(app))
    }
    
}

extension AppInfoService {
    
    enum API: APICall {
        var dataKeyPath: String? {
            switch self {
            case .appList:
                return "results"
            default:
                return nil
            }
        }
        
        case appList
        case updateAppCollectedState(AppInfo)
        
        var url: String {
            switch self {
            case .appList:
                return "https://raw.githubusercontent.com/leung-c/lch.github.io/main/my_apps.json"
            case .updateAppCollectedState(_):
                return ""
            }
        }
        
        var method: String {
            return "GET"
        }
        
        var headers: [String : String]? {
            return ["Accept": "application/json"]
        }
        
    }
}
