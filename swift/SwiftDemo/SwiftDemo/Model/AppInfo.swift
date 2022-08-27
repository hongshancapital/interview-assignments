//
//  AppInfo.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

public struct AppInfo: Identifiable, Codable {
    public var id: Int
    public var name: String?
    public var description: String?
    public var artworkURL60: URL?

    public init(id: Int, name: String? = nil, description: String? = nil, artworkURL60: URL? = nil) {
        self.id = id
        self.name = name
        self.description = description
        self.artworkURL60 = artworkURL60
    }
}

extension AppInfo {
    public init(response: SearchAppResponse.SearchResult) {
        let artworkUrl60: URL? = response.artworkUrl60 != nil ? URL(string: response.artworkUrl60!) : nil
        self.init(id: response.trackId, name: response.trackName, description: response.description, artworkURL60: artworkUrl60)
    }
}
