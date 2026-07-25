//
//  LocalServerManager.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import Foundation
import GCDWebServer

/// LocalServer搭建
class LocalServerManager: NSObject {
    
    static let shared = LocalServerManager()
    
    private let webServer = GCDWebServer()
    
    override init() {
        super.init()
        addDefaultHandler()
    }

    func addDefaultHandler() {
        webServer.addDefaultHandler(forMethod: "GET", request: GCDWebServerRequest.self) { request in
            if request.path == kApiSearch {
                if let query = request.query,
                   let keyword = query["keyword"],
                    let index = query["index"],
                    let count = query["count"] {
                    let dataList = buildSearchListData().filter { searchModel in
                        return searchModel.title.hasPrefix(keyword) && keyword.count != 0
                    }
                    let pageList = selectPageList(index: Int(index) ?? 0, count: Int(count) ?? 10, searchList: dataList)
                    return self.buildDataResponse(code: 0, msg: "success", data: pageList.JSONValue())
                }
                return self.buildDataResponse(code: 0, msg: "success", data: [])
            } else {
                return self.buildDataResponse(code: LocalErrorCode.PathNotFound.rawValue, msg: LocalErrorCode.PathNotFound.msgValue, data: nil)
            }
        }
    }
    
    func startLocalService() {
        try? webServer.start(options: [GCDWebServerOption_BindToLocalhost: true,
                                       GCDWebServerOption_Port: 9999,
                                       GCDWebServerOption_AutomaticallySuspendInBackground: true])
    }
    
    func buildDataResponse(code: Int, msg: String, data: Any?) -> GCDWebServerDataResponse? {
        return GCDWebServerDataResponse(jsonObject: ["code": code, "msg": msg, "data": data])
    }

}
