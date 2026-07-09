//
//  NetworkManager.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/19.
//

import Foundation
import Network

let SoftwareAPI = "https://itunes.apple.com/search?entity=software&limit={limit}&term=chat"
let AlbumAPI = "https://itunes.apple.com/search?term=album&limit={limit}"

protocol NetworkManagerProtocol {
    /// 是否连接网络
    var isConnected: Bool { get }

    /// get方法，失败会抛出 ServerException 异常
    func get(url: String) async -> (Data?, Error?)
}

class NetworkManager: NetworkManagerProtocol {
    let monitor = NWPathMonitor()

    init() {
        self.monitor.start(queue: .global())
    }

    func get(url: String) async -> (Data?, Error?) {
        do {
            let result = try await URLSession.shared.data(for: .init(url: URL(string: url)!))
            return (result.0, nil)
        } catch {
            print(error)
            return (nil, error)
        }
    }
    
    var isConnected: Bool {
        switch monitor.currentPath.status {
        case .satisfied:
            return true
        case .unsatisfied:
            return false
        case .requiresConnection:
            return false
        default:
            return false
        }
    }
}
