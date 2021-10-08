//
//  ProductSearchApp.swift
//  ProductSearch
//
//  Created by foolbear on 2021/10/1.
//

import SwiftUI
import Swifter

@main
struct ProductSearchApp: App {
    let server: HttpServer = {
        let server = HttpServer()
        server["/dyson/0"] = { _ in .ok(.text(MockHttpResponse.json0)) }
        server["/dyson/1"] = { _ in .ok(.text(MockHttpResponse.json1)) }
        server["/*/*"] = { _ in .ok(.text(MockHttpResponse.json2)) }
        try? server.start()
        return server
    }()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
