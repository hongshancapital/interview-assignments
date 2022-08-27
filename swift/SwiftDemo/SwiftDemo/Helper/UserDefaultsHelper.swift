//
//  UserDefaultsHelper.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation
import Combine

extension Bundle {
    static var baseBundleIdentifier: String {
        let bundle = Bundle.main
        let packageType = bundle.object(forInfoDictionaryKey: "CFBundlePackageType") as? String
        let baseBundleIdentifier = bundle.bundleIdentifier!
        if packageType == "XPC!" {
            let components = baseBundleIdentifier.components(separatedBy: ".")
            return components[0..<components.count-1].joined(separator: ".")
        }
        
        return baseBundleIdentifier
    }
    
    static var sharedContainerIdentifier: String {
        let bundleIdentifier = baseBundleIdentifier
        return "group." + bundleIdentifier
    }
}

extension UserDefaults {
    public static var suiteName: String { Bundle.sharedContainerIdentifier }
    public static let group: UserDefaults = UserDefaults(suiteName: suiteName)!
}


enum UserDefaultsKey: String {
    case likedApps
}

@available(iOS 13.0, *)
@propertyWrapper
struct CombineUserStorge<T: Codable> {
    private let container: UserDefaults
    private let key: String
    private let defaultValue: T
#if canImport(Combine)
    public var projectedValue: CurrentValueSubject<T, Never>
#endif
    public var wrappedValue: T {
        get {
            guard let data = container.data(forKey: key) else { return defaultValue }
            return (try? JSONDecoder().decode(T.self, from: data)) ?? defaultValue
        }
        set {
            container.set(try? JSONEncoder().encode(newValue) , forKey: key)
            container.synchronize()
#if canImport(Combine)
            projectedValue.send(newValue)
#endif
        }
    }
    
    init(wrappedValue: T, key: UserDefaultsKey, container: UserDefaults) {
        self.container = container
        self.key = key.rawValue
        self.defaultValue = wrappedValue
#if canImport(Combine)
        var savedValue: T = wrappedValue
        if let data = container.data(forKey: key.rawValue) {
            savedValue = (try? JSONDecoder().decode(T.self, from: data)) ?? wrappedValue
        }
        projectedValue =  .init(savedValue)
#endif
    }
}
