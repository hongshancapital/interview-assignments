//
//  LocalConnectManager.swift
//  LocalServerApp
//
//  Created by 梁泽 on 2021/9/30.
//

import Foundation
import GCDWebServer

//MARK: - 本地服务管理类
class LocalConnectManager: NSObject {
    static let shared = LocalConnectManager()
    private  let webServer = GCDWebServer()

    /// 启动localhost服务
    func startLocalServer() {
        registerRouter()
        
        try? webServer
            .start(options: [
                GCDWebServerOption_Port : 1024,
                GCDWebServerOption_AutomaticallySuspendInBackground : false,
                GCDWebServerOption_BindToLocalhost: true
            ])
    }
    
    /// 处理 get 请求，
    private func registerRouter() {
        webServer.addDefaultHandler(forMethod: "GET", request: GCDWebServerRequest.self) { request in
            guard request.path == "/search" else {
                return GCDWebServerDataResponse(jsonObject: ["code": 0, "message": "请检查path"])
            }
                        
            let response = NetworkReponse(code: 1, message: "请求成功", data: request.query?["keyword"] == "Dyson" ? CommodityMock.mockPageOne() : [])
            return GCDWebServerDataResponse(jsonObject: response.responseObj)
        }
    }
   
    
}
