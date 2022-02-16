//
//  DataService.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import Foundation

class DataService {
    
    static let shared = DataService()
    
}

/// MARK: - APIs
extension DataService: AppsAPI {
    
    func fetchApps(params: Params) async -> ([AppModel]?, NetworkResponse) {
        let apiUrl = "https://itunes.apple.com/search?entity=software&limit=50&term=chat"
        guard let url = URL.init(string: apiUrl) else {
            return (nil, NetworkResponse(code: .serverError, message: "错误的接口地址", data: nil))
        }
        let request = URLRequest.init(url: url)
        var response: (Data, URLResponse)?
        do {
            response = try await URLSession.shared.data(for: request)
        } catch {
            print(error)
        }
        guard let response = response else {
            return (nil, NetworkResponse(code: .serverError, message: "接口请求出错", data: nil))
        }
        guard let jsonObj = try? JSONSerialization.jsonObject(with: response.0) as? [String: Any] else {
            return (nil, NetworkResponse(code: .serverError, message: "解析数据出错", data: nil))
        }
        guard let results = jsonObj["results"] as? [[String: Any]] else {
            return (nil, NetworkResponse(code: .serverError, message: "未获取到results字段", data: jsonObj))
        }
        let apps = AppModel.toArray(data:results)
        let sub = subApps(apps, params.pageNum, params.pageSize)
        resetAppsLike(apps)
        return (sub, NetworkResponse(code: .success, message: nil, data: jsonObj))
    }
    
    func likeApp(params: Params) async -> NetworkResponse {
        guard let targetId = params["id"] as? String, let like = params["like"] as? Bool else {
            return NetworkResponse(code: .paramsError, message: "参数错误", data: nil)
        }
        likeCache[targetId] = like
        return NetworkResponse(code: .success, message: nil, data: nil)
    }
    
    private func subApps(_ apps: [AppModel]?, _ pageNum: Int, _ pageSize: Int) -> [AppModel]? {
        guard let apps = apps else {
            return nil
        }
        guard pageNum > 0, pageSize > 0 else {
            return nil
        }
        let totalPageNum = Int(ceil(Double(apps.count) / Double(pageSize)))
        guard pageNum <= totalPageNum else {
            return nil
        }
        let sub = apps[(pageNum - 1) * pageSize..<min(pageNum * pageSize, apps.count)]
        return Array(sub)
    }
    
    private func resetAppsLike(_ apps: [AppModel]?) {
        guard let apps = apps else {
            return
        }
        for app in apps {
            guard likeCache[app.id ?? ""] == true else {
                continue
            }
            app.like = true
        }
    }
    
    private struct AppsAPIKeys {
        static var apps = "apps"
    }
    
    var likeCache: [String: Bool] {
        get {
            return objc_getAssociatedObject(self, &AppsAPIKeys.apps) as? [String: Bool] ?? [String: Bool]()
        }
        set {
            objc_setAssociatedObject(self, &AppsAPIKeys.apps, newValue, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }
}
