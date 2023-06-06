import SwiftUI

@main
struct AppListApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            CardListPage()
        }
    }
}

