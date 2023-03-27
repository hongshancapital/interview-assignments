//
//  AppModel.swift
//  ItuneApps
//
//  Created by daicanglan on 2023/3/27.
//

import Foundation


struct AppModel: Codable {
    var resultCount: Int
    var results: [AppItem]
}

struct AppItem: Identifiable, Codable {
    var id : Int {
        get { trackId }
    }
    var artworkUrl512: String
    var trackName: String
    var description: String
    var trackId: Int
    
    init(artworkUrl512: String, trackName: String, description: String, trackId: Int) {
        self.artworkUrl512 = artworkUrl512
        self.trackName = trackName
        self.description = description
        self.trackId = trackId
    }
}


//  static examples
extension AppItem {
    
    static let examples: [AppItem] = [
        AppItem(artworkUrl512: "https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/d7/40/ad/d740ad8c-b53c-8efd-deba-168107728874/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/512x512bb.jpg", trackName: "Google Chat0", description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done• Google search functionality, with options to filter for conversations and content that you’ve shared• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export", trackId: 0),
        AppItem(artworkUrl512:"https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/d7/40/ad/d740ad8c-b53c-8efd-deba-168107728874/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/512x512bb.jpg", trackName: "Google Chat1", description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done• Google search functionality, with options to filter for conversations and content that you’ve shared• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export", trackId: 1),
        AppItem(artworkUrl512: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/cd/8f/df/cd8fdfe9-b058-19e3-b7cf-39e5829ab20b/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg", trackName: "Kik Messaging & Chat App0", description: "Get connected. Kik is way more than just messaging. It’s the easiest way to connect with your friends, stay in the loop, and explore – all through chat. No phone numbers, just pick a username.• Choose who to chat with one-on-one and in groups• Share pics, videos, gifs, games, and more• Meet new friends with similar interestsGet on Kik now. Start chatting!", trackId: 2),
        AppItem(artworkUrl512:"https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/cd/8f/df/cd8fdfe9-b058-19e3-b7cf-39e5829ab20b/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg", trackName: "Kik Messaging & Chat App1", description: "Get connected. Kik is way more than just messaging. It’s the easiest way to connect with your friends, stay in the loop, and explore – all through chat. No phone numbers, just pick a username.• Choose who to chat with one-on-one and in groups• Share pics, videos, gifs, games, and more• Meet new friends with similar interestsGet on Kik now. Start chatting!", trackId: 3),
        AppItem(artworkUrl512:"https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/cd/8f/df/cd8fdfe9-b058-19e3-b7cf-39e5829ab20b/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg", trackName: "Kik Messaging & Chat App2", description: "Get connected. Kik is way more than just messaging. It’s the easiest way to connect with your friends, stay in the loop, and explore – all through chat. No phone numbers, just pick a username.• Choose who to chat with one-on-one and in groups• Share pics, videos, gifs, games, and more• Meet new friends with similar interestsGet on Kik now. Start chatting!", trackId: 4),
        AppItem(artworkUrl512:"https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/cd/8f/df/cd8fdfe9-b058-19e3-b7cf-39e5829ab20b/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg", trackName: "Kik Messaging & Chat App2", description: "Get connected. Kik is way more than just messaging. It’s the easiest way to connect with your friends, stay in the loop, and explore – all through chat. No phone numbers, just pick a username.• Choose who to chat with one-on-one and in groups• Share pics, videos, gifs, games, and more• Meet new friends with similar interestsGet on Kik now. Start chatting!", trackId: 5),
        AppItem(artworkUrl512:"https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/cd/8f/df/cd8fdfe9-b058-19e3-b7cf-39e5829ab20b/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg", trackName: "Kik Messaging & Chat App2", description: "Get connected. Kik is way more than just messaging. It’s the easiest way to connect with your friends, stay in the loop, and explore – all through chat. No phone numbers, just pick a username.• Choose who to chat with one-on-one and in groups• Share pics, videos, gifs, games, and more• Meet new friends with similar interestsGet on Kik now. Start chatting!", trackId: 6),
        AppItem(artworkUrl512:"https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/cd/8f/df/cd8fdfe9-b058-19e3-b7cf-39e5829ab20b/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg", trackName: "Kik Messaging & Chat App2", description: "Get connected. Kik is way more than just messaging. It’s the easiest way to connect with your friends, stay in the loop, and explore – all through chat. No phone numbers, just pick a username.• Choose who to chat with one-on-one and in groups• Share pics, videos, gifs, games, and more• Meet new friends with similar interestsGet on Kik now. Start chatting!", trackId: 7),
        AppItem(artworkUrl512:"https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/cd/8f/df/cd8fdfe9-b058-19e3-b7cf-39e5829ab20b/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg", trackName: "Kik Messaging & Chat App2", description: "Get connected. Kik is way more than just messaging. It’s the easiest way to connect with your friends, stay in the loop, and explore – all through chat. No phone numbers, just pick a username.• Choose who to chat with one-on-one and in groups• Share pics, videos, gifs, games, and more• Meet new friends with similar interestsGet on Kik now. Start chatting!", trackId: 8),
    ]
}
