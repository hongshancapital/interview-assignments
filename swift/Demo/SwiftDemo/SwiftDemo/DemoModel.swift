//
//  DemoModel.swift
//  SwiftDemo
//
//  Created by 彭军涛 on 2022/7/10.
//

import Foundation

struct DemoModel: Codable {
    var resultCount: Int?
    var results: [ScreenshotUrls]?

}

extension DemoModel {
    struct ScreenshotUrls: Codable {
        var trackCensoredName: String?
        var description: String?
        var artworkUrl60: String?
        var bundleId: String
    }
}
