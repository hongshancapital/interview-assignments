//
//  LHExtension.swift
//  LHAppListDemo
//
//  Created by lzh on 2022/3/29.
//

import SwiftUI

extension Color {
   init(hex: Int, alpha: Double = 1) {
       let components = (
           R: Double((hex >> 16) & 0xff) / 255,
           G: Double((hex >> 08) & 0xff) / 255,
           B: Double((hex >> 00) & 0xff) / 255
       )
       self.init(
           .sRGB,
           red: components.R,
           green: components.G,
           blue: components.B,
           opacity: alpha
       )
   }
}

extension UIApplication {
    var keyWindow: UIWindow? {
        connectedScenes
            .compactMap {
                $0 as? UIWindowScene
            }
            .flatMap {
                $0.windows
            }
            .first {
                $0.isKeyWindow
            }
    }
}

private struct SafeAreaInsetsKey: EnvironmentKey {
    static var defaultValue: EdgeInsets {
        UIApplication.shared.keyWindow?.safeAreaInsets.swiftUiInsets ?? EdgeInsets()
    }
}

extension EnvironmentValues {
    var safeAreaInsets: EdgeInsets {
        self[SafeAreaInsetsKey.self]
    }
}

private extension UIEdgeInsets {
    var swiftUiInsets: EdgeInsets {
        EdgeInsets(top: top, leading: left, bottom: bottom, trailing: right)
    }
}
