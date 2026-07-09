//
//  MokeResponseData.swift
//  ApplistTests
//
//  Created by santcool on 2023/1/30.
//

import Foundation
@testable import Applist

enum AppListMockData {
    static let response1 = ResponseModel(resultCount: 0, results: [])
    
    static let response2 = ResponseModel(resultCount: 3, results: [
        AppModel(trackId: 1163852619, trackCensoredName: "Google Chat", description: "Google Chat is an intelligent and secure communication", artworkUrl100: "https://is1-ssl.mzstatic.com/image/thumb/Purple113/v4/be/c9/cc/bec9cc7b-a857-26e3-a0b7-0e21eabf3197/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/100x100bb.jpg"),
        AppModel(trackId: 382617920, trackCensoredName: "Viber Messenger: Chats & Calls", description: "Viber is a secure, private, fun messaging and calling app", artworkUrl100: "https://is3-ssl.mzstatic.com/image/thumb/Purple116/v4/1b/18/d4/1b18d4df-f2e9-9d16-037c-dc8253a12bc7/AppIcon-0-0-1x_U007emarketing-0-7-0-0-85-220.png/100x100bb.jpg"),
        AppModel(trackId: 414478124, trackCensoredName: "WeChat", description: "WeChat is more than a messaging and social media app", artworkUrl100: "https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/0b/f9/6e/0bf96e4f-75e1-40db-d02e-d32a8fb6475a/AppIcon-0-1x_U007emarketing-0-4-0-sRGB-0-85-220.png/100x100bb.jpg")
    ])
    
    static let likedApp = AppModel(trackId: 414478124, trackCensoredName: "WeChat", description: "WeChat is more than a messaging and social media app", artworkUrl100: "https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/0b/f9/6e/0bf96e4f-75e1-40db-d02e-d32a8fb6475a/AppIcon-0-1x_U007emarketing-0-4-0-sRGB-0-85-220.png/100x100bb.jpg")
    
}
