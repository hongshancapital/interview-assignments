//
//  ServiceManager.swift
//  HRAssinment
//
//  Created by Henry Zhang on 2021/10/21.
//

import Foundation
import GCDWebServer

/// 本地服务管理
class LocalServiceManager: NSObject {
    static let shared = LocalServiceManager()
    private  let webServer = GCDWebServer()

    /// 启动服务
    func startServer() {
        registerRouter()
        
        try? webServer
            .start(options: [
                GCDWebServerOption_Port : 10086,
                GCDWebServerOption_AutomaticallySuspendInBackground : false,
                GCDWebServerOption_BindToLocalhost: true
            ])
    }
    
    /// 注册返回值
    private func registerRouter() {
        // get返回
        webServer.addDefaultHandler(forMethod: "GET", request: GCDWebServerRequest.self) { request in
            guard request.path == "/search" else {
                return GCDWebServerDataResponse(jsonObject: ["code": 0, "message": "请检查path"])
            }
                        
            let response = RequestResponse(code: 1, message: "请求成功", data: request.query?["keyword"] == "Dyson" ? CommodityMock.mockPageOne() : [])
            return GCDWebServerDataResponse(jsonObject: response.responseObj)
        }
        
        //兼容一下post请求
        webServer.addDefaultHandler(forMethod: "POST", request: GCDWebServerRequest.self) { request in
            guard request.path == "/search" else {
                return GCDWebServerDataResponse(jsonObject: ["code": 0, "message": "请检查path"])
            }
                        
            let response = RequestResponse(code: 1, message: "请求成功", data: request.query?["keyword"] == "Dyson" ? CommodityMock.mockPageOne() : [])
            return GCDWebServerDataResponse(jsonObject: response.responseObj)
        }
    }
}
