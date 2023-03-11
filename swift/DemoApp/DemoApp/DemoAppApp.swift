//
//  DemoAppApp.swift
//  DemoApp
//
//  Created by dev on 2023/3/10.
//

import SwiftUI

@main
struct DemoAppApp {
    static func main() {
        if #available(iOS 14.0, *) {
            MyView.main()
        } else {
            UIApplicationMain(
                CommandLine.argc,
                CommandLine.unsafeArgv,
                nil,
                NSStringFromClass(AppDelegate.self))
        }
    }

}



class AppDelegate: UIResponder, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        return true
    }

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
    }
}


class SceneDelegate: UIResponder, UIWindowSceneDelegate {

    var window: UIWindow?

    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
       
        if #available(iOS 14.0, *) { } else { //iOS13 以下的代码
            let contentView = ContentView()
            if let windowScene = scene as? UIWindowScene {
                let window = UIWindow(windowScene: windowScene)
                window.rootViewController = UIHostingController(rootView: contentView)
                self.window = window
                window.makeKeyAndVisible()
            }
        }
        
       

       
    }

    func sceneDidDisconnect(_ scene: UIScene) {
        //
    }

    func sceneDidBecomeActive(_ scene: UIScene) {
        //
    }

    func sceneWillResignActive(_ scene: UIScene) {
       //
    }

    func sceneWillEnterForeground(_ scene: UIScene) {
       //
    }

    func sceneDidEnterBackground(_ scene: UIScene) {
        //
    }
}

@available(iOS 14.0, *)
struct MyView: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    @Environment(\.scenePhase) var scenePhase
    @StateObject private var modelData = ModelData()
//在iOS 14之后，Apple又给SwiftUI提供了更优雅的API来显示和控制Scene。所以控制应用展示可以这样：
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(modelData)
        }.onChange(of: scenePhase) {   newScenePhase in
            switch newScenePhase {
            case .active:
              print("应用启动了")
            case .inactive:
              print("应用休眠了")
            case .background:
              print("应用在后台展示")
            @unknown default:
              print("default")
            }
        }
    }
}
