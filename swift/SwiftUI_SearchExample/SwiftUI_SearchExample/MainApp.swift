import SwiftUI

@main
struct MainApp: App {
    
    init() {
        /// 启动本地服务
        LocalServerManager.shared.startLocalService()
    }
    
    var body: some Scene {
        WindowGroup {
            SearchListView()
        }
    }
}
