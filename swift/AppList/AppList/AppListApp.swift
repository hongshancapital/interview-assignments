//
//  AppListApp.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import SwiftUI

@main
struct AppListApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    var body: some Scene {
        WindowGroup {
            HomeView()
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        // Trigger network authorization
        URLSession.shared.dataTask(with: URL(string: "https://www.baidu.com")!) { _, _, _ in }
        
        // Prepare mocking data to sandbox
        if let docPath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first {
            
            let filePath = docPath.appending("/data.json")
            let fileManager = FileManager.default
            let fileExists = fileManager.fileExists(atPath: filePath)
            if !fileExists {
                let bundleFilePath = Bundle.main.path(forResource: "data.json", ofType: nil) ?? ""
                try? fileManager.copyItem(at: URL(fileURLWithPath: bundleFilePath),
                                          to: URL(fileURLWithPath: filePath))
            }
        }
        
        return true
    }

}

