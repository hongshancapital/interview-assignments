//
//  API.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import UIKit

struct API {}

extension API {
    enum Environment: Equatable {
        case appStore
        case preview
        case develop

        var type: String {
            switch self {
            case .appStore: return "AppStore"
            case .preview: return "Preview"
            case .develop: return "Develop"
            }
        }

        init(type: String) {
            switch type {
            case "AppStore":
                self = .appStore
            case "Preview":
                self = .preview
            case "Develop":
                self = .develop
            default:
                self = .appStore
            }
        }
    }
}

// MARK: HttpHeaders

extension API.Environment {
    enum HttpHeadersManager {
        static var allHeaders: [String: String] = [
            "Accept": "application/json",
            "x-platform": "ios",
            "x-app-version-code": versionName
        ]

        static var mobileModel: String {
            return UIDevice.current.model
        }

        static var mobileVersion: String {
            return UIDevice.current.systemVersion
        }

        static var versionCode: String {
            return (Bundle.main.infoDictionary?["CFBundleVersion"] as? String) ?? "0"
        }

        static var versionName: String {
            return (Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String) ?? "1.0.0"
        }
    }
}

