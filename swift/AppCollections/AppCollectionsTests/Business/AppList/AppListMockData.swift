//
//  AppListMockData.swift
//  AppCollectionsTests
//
//  Created by Guang Lei on 2022/3/12.
//

import Foundation
@testable import AppCollections

enum AppListMockData {
    
    // MARK: - Response data

    static let rspData1 = AppListResponseModel(
        resultCount: 2,
        results: [
            AppListResponseModel.App(
                trackId: 1163852619,
                trackName: "Google Chat",
                description: "Google Chat is an intelligent tool.",
                artworkUrl60: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg")!
            ),
            AppListResponseModel.App(
                trackId: 382617920,
                trackName: "Viber Messenger: Chats & Calls",
                description: "Viber is the FREE, simple, secure messaging and calling app.",
                artworkUrl60: URL(string: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/60x60bb.jpg")!
            )
        ]
    )
    
    static let rspData2 = AppListResponseModel(
        resultCount: 1,
        results: [
            AppListResponseModel.App(
                trackId: 414478124,
                trackName: "WeChat",
                description: "WeChat is more than a messaging and social media app – it is a lifestyle for one billion users across the world.",
                artworkUrl60: URL(string: "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/91/6f/8a/916f8a02-6467-51a7-516e-bad1a86203bc/source/60x60bb.jpg")!
            )
        ]
    )
    
    static let rspEmptyData = AppListResponseModel(
        resultCount: 0,
        results: []
    )
    
    static let rspError = MockNetworkError.unknown
    
    // MARK: - Displayed data
    
    static let favoriteApp = AppModel(
        trackId: 1163852619,
        trackName: "Google Chat",
        description: "Google Chat is an intelligent tool.",
        artworkUrl60: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg")!,
        isFavorite: true
    )
    
    static let unfavoriteApp = AppModel(
        trackId: 1163852619,
        trackName: "Google Chat",
        description: "Google Chat is an intelligent tool.",
        artworkUrl60: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg")!,
        isFavorite: false
    )
}
