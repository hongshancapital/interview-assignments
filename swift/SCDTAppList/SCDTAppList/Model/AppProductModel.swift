//
//  AppProductModel.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import SwiftUI

struct AppProductModel: Decodable, Identifiable,Equatable{
    let id : String         //app bundle id
    let trackName: String   //app name
    let description: String //app description
    let icon: URL           //app icon url
    
    var isFavorite = false
    
    enum CodingKeys: String, CodingKey {
        case id = "bundleId"
        case trackName = "trackName"
        case description = "description"
        case icon = "artworkUrl60"
    }
    
    static func == (lhs: Self, rhs: Self) -> Bool {
        return lhs.id == rhs.id
    }
}

#if DEBUG

extension AppProductModel{
    static let preview = Self(
        id: "com.tencent.xin",
        trackName: "WeChat",
        description: "WeChat is more than a messaging and social media app – it is a lifestyle for one billion users across the world. Chat and make calls with friends, read news and use local services in Official Accounts and Mini Programs, play games with friends, enjoy mobile payment features with WeChat Pay, and much more.\n\nWhy do one billion people use WeChat?\n• MORE WAYS TO CHAT: Message friends using text, photo, voice, video, location sharing, and more. Create group chats with up to 500 members.\n• VOICE & VIDEO CALLS: High-quality voice and video calls to anywhere in the world. Make group video calls with up to 9 people.\n• REAL-TIME LOCATION: Not good at explaining directions? Share your real-time location with the tap of a button.\n• MOMENTS: Never forget your favorite moments. Post photos, videos, and more to share with friends on your personal Moments stream.\n• TIME CAPSULE (NEW!): Share glimpses of your day. Record short videos to post in your Time Capsule before they disappear in 24 hours.\n• STICKER GALLERY: Browse thousands of fun, animated stickers to help express yourself in chats, including stickers with your favorite cartoon and movie characters.\n• CUSTOM STICKERS (NEW!): Make chatting more unique with custom stickers and selfie stickers.\n• OFFICIAL ACCOUNTS: Tons of accounts to follow with original content and news for your reading pleasure.\n• MINI PROGRAMS: Countless third-party services all within the WeChat app that don’t require additional installation, saving you precious phone storage and time. \n• TOP STORIES: See the latest articles your friends are reading and discover all kinds of interesting content.\n• GAMES: Have fun and compete with friends in a huge selection of WeChat Mini Games and Tencent Games (available in select regions).\n• WECHAT PAY: Enjoy the convenience of world-leading mobile payment features with WeChat Pay and Wallet (available in select regions).\n• WECHAT OUT: Make calls to mobile phones and landlines around the globe at super low rates (only available in select regions).\n• WERUN: Use Healthkit and Health app data to sync your step count on WeRun, where you can compete against friends with daily step rankings. Enable WeRun in “Settings” > “General” > “Plug-ins”.\n• LANGUAGE SUPPORT: Localized in 20 different languages and can translate friends' messages and Moments posts.\n• BETTER PRIVACY: Giving you the highest level of control over your privacy, WeChat is the only messaging app to be certified by TRUSTe.\n• EASY MODE: Clearer and larger text and buttons for better readability.",
        icon: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/0b/f9/6e/0bf96e4f-75e1-40db-d02e-d32a8fb6475a/AppIcon-0-1x_U007emarketing-0-4-0-sRGB-0-85-220.png/60x60bb.jpg")!
    )
        
}

#endif


