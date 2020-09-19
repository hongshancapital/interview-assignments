import SwiftUI

// MARK: Content Offset

struct ContentOffsetKey: PreferenceKey {
    static var defaultValue: CGPoint? = nil

    static func reduce(value: inout CGPoint?, nextValue: () -> CGPoint?) {
        value = value ?? nextValue()
    }
}

extension View {
    func contentOffset(_ offset: CGPoint) -> some View {
        preference(key: ContentOffsetKey.self, value: offset)
    }
}

// MARK: Content Size

struct ContentSizeKey: PreferenceKey {
    static var defaultValue: CGSize? = nil

    static func reduce(value: inout CGSize?, nextValue: () -> CGSize?) {
        value = value ?? nextValue()
    }
}

extension View {
    func contentSize(_ size: CGSize) -> some View {
        preference(key: ContentSizeKey.self, value: size)
    }
}

// MARK: Safe Area

struct SafeAreaInsetsKey: PreferenceKey {
    static var defaultValue: EdgeInsets? = nil

    static func reduce(value: inout EdgeInsets?, nextValue: () -> EdgeInsets?) {
        value = value ?? nextValue()
    }
}

extension View {
    func safeAreaInsets(_ insets: EdgeInsets) -> some View {
        preference(key: SafeAreaInsetsKey.self, value: insets)
    }
}

// MARK: View Size

struct ViewSizeKey: PreferenceKey {
    static var defaultValue: CGSize? = nil

    static func reduce(value: inout CGSize?, nextValue: () -> CGSize?) {
        value = value ?? nextValue()
    }
}

extension View {
    func viewSize(_ size: CGSize) -> some View {
        preference(key: ViewSizeKey.self, value: size)
    }
}

struct Preferences_Previews: PreviewProvider {
    static var previews: some View {
        /*@START_MENU_TOKEN@*/Text("Hello, World!")/*@END_MENU_TOKEN@*/
    }
}
