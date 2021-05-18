import UIKit
/// The screen size
struct Theme {
    /// The width of the screen
    static var screenWidth: CGFloat {
        return UIScreen.main.bounds.size.width
    }
    
    /// The screen height
    static var screenHeight: CGFloat {
        return UIScreen.main.bounds.size.height
    }
    
    /// Screen resolution
    static var screenScale: CGFloat {
        return UIScreen.main.scale
    }
    
    /// 1px
    static var onePixels: CGFloat {
        return 1 / screenScale
    }

    /// Status bar height
    static var statusBarHeight: CGFloat {
        if #available(iOS 13.0, *) {
            let statusBarManager = UIApplication.shared.windows.first?.windowScene?.statusBarManager
            return statusBarManager?.statusBarFrame.size.height ?? 22
        }else{
            return UIApplication.shared.statusBarFrame.size.height
        }
        
    }
    
    /// Navigation bar height
    static var navBarHeight: CGFloat {
        return 44
    }
    
    /// Total height of navigation bar (status bar + navigation bar)
    static var navTotalHeight: CGFloat {
        return statusBarHeight + navBarHeight
    }
    
    /// Screen safety zone margins
    static var safeAreaInsets: UIEdgeInsets {
        if #available(iOS 13.0, *) {
            return UIApplication.shared.windows.filter {$0.isKeyWindow}.first?.safeAreaInsets ?? .zero
        }
        return UIEdgeInsets.zero
    }
    
    /// Virtual Home button height
    static var safeAreaBottom: CGFloat {
        if #available(iOS 11.0, *) {
            return safeAreaInsets.bottom
        }
        return 0
    }
}

/// Device type
extension Theme{
    /// Whether it is the
    static var iPad: Bool {
        if UIDevice.current.userInterfaceIdiom == .pad {
            return true
        }
        return false
    }
    
    /// Whether or not it is an emulator
    static var isSimulator: Bool {
        var isSim = false
        #if arch(i386) || arch(x86_64)
        isSim = true
        #endif
        return isSim
    }
}

/// Delay the
/// - Parameters:
///   - delay: Delay time (seconds)
///   - closure: Closures that defer execution
public func delay(_ delay: Double, closure: @escaping () -> Void) {
    DispatchQueue.main.asyncAfter(deadline: .now() + delay) {
        closure()
    }
}
