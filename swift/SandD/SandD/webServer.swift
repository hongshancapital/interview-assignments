//
//  webServer.swift
//  SandD
//
//  Created by qiu on 2021/1/19.
//

import Foundation
// import GCDWebServer

func initWebServer() {

    let webServer = GCDWebServer()
    
    let data: Data
    let filename = "productGroupsData.json"
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
    else {
        fatalError("Couldn't find \(filename) in main bundle.")
    }

    do {
        data = try Data(contentsOf: file)
    } catch {
        fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
    }

    
    webServer.addHandler(forMethod: "GET", path: "/search", request: GCDWebServerRequest.self,
                         processBlock: { (request) -> GCDWebServerResponse? in
                            let key = request.query?["q"]
                            if key == "Dyson" {
                                return GCDWebServerDataResponse(data: data, contentType: "application/json")
                            }
                            return GCDWebServerDataResponse(html: "[]")
                         })
        
    webServer.start(withPort: 8080, bonjourName: "GCD Web Server")
    
    print("Visit \(String(describing: webServer.serverURL)) in your web browser")
}
