//
//  SearchAppAction.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

extension APIAction {
    public var itunesHost: String { "https://itunes.apple.com" }
}

//https://itunes.apple.com/search?entity=software&limit=50&term=chat
public struct SearchAppAction: APIAction {
    public struct SearchAppParameters: APIServerParameters {
        public var entity: String
        public var term: String
        public var limit: Int
    }
    public typealias Parameters = SearchAppParameters
    public typealias Response = SearchAppResponse
    
    public var host: String { itunesHost }
    public var uri: String { "/search" }
    public var parameters: Parameters?
    
    public init(_ parameters: Parameters?) {
        self.parameters = parameters
    }
}

public struct SearchAppResponse: APIServerResponse {
    public struct SearchResult: Codable {
        public var trackId: Int
        public var trackName: String?
        public var description: String?
        public var artworkUrl60: String?
    }
    var resultCount: Int?
    var results: [SearchResult]?
}
