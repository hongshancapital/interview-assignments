import SwiftUI

struct ContentView: View {
    @State var isRoot: Bool = !UserDefaultsConfig.username.isEmpty
    @State var isAnimation: Bool = false
    var body: some View {
        Group {
            if isRoot {
                RootView(isRoot: $isRoot)
            } else {
                LoginView(isRoot: $isRoot)
            }
        }
        .opacity(isAnimation ? 1 : 0)
        .animation(Animation.spring().delay(0.4))
        .onAppear(perform: {
            withAnimation {
                self.isAnimation.toggle()
            }
        })
    }
}
