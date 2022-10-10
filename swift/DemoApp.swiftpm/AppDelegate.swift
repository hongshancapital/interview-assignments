//
//  AppDelegate.swift
//  DemoApp
//
//  Created by 黄磊 on 2022/4/11.
//

import Foundation
import UIKit

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        Registry.shared.startRetisteAfterLaunch(application, launchOptions: launchOptions)
        return true
    }
}
