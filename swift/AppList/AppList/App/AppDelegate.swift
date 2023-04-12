import SwiftUI

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        AppRunInfo.shared.loadVendor()
        AppRunInfo.shared.loadData()
        AppRunInfo.shared.initCache()
        return true
    }
}
