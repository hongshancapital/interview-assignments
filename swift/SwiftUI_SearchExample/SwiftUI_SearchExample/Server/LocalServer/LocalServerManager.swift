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
                if let query = request.query, query["keyword"] == "Dyson" {
                    return self.buildDataResponse(code: 0, msg: "success", data: buildSearchListData().JSONValue())
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
