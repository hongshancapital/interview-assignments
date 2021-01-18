//
//  AppDelegate.swift
//  scdt
//
//  Created by qiupeng on 2021/1/18.
//

import UIKit

import GCDWebServer

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    let server = GCDWebServer.init()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.

        let test_data = [
            SearchListSectionModel.init(id: "1", sectionName: "Vacuum", list: [
                SearchListSectionModel.ItemModel.init(id: "2", name: "V11", inStock: true, price: "$599.99"),
                SearchListSectionModel.ItemModel.init(id: "1", name: "V10", inStock: false, price: "$399.99"),
            ]),
            SearchListSectionModel.init(id: "2", sectionName: "Hair Dryer", list: [
                SearchListSectionModel.ItemModel.init(id: "1", name: "Supersonic", inStock: true, price: "$399.99"),
            ]),
        ]

        let static_data = [
            "Dyson": test_data
        ]


        server.addDefaultHandler(forMethod: "GET", request: GCDWebServerRequest.self) { (req) -> GCDWebServerResponse? in
            var data: [SearchListSectionModel] = []
            let key = req.url.lastPathComponent
            data = static_data[key] ?? []
            do {
                let coder = JSONEncoder.init()
                let data = try coder.encode(data)
                return GCDWebServerDataResponse.init(data: data, contentType: "application/json")
            } catch {
                return GCDWebServerErrorResponse.init(text: "server error")
            }
        }

        server.start(withPort: 8080, bonjourName: "hello")


        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

