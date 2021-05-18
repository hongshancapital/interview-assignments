import SwiftUI

@main
struct SwiftUILoginDemoApp: App {
    var body: some Scene {
        WindowGroup {
            let contentView = ContentView()
            let onboard = UserSettings()
            contentView.environmentObject(onboard)
        }
    }
}

/// Setting
class UserSettings: ObservableObject {
    @Published var loggedIn : Bool = false
}
