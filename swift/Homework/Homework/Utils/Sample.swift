//
// Homework
// Sample.swift
//
// Created by wuyikai on 2022/4/30.
// 
// 

#if DEBUG

import Foundation

extension AppInfo {
    static let sampleData: [AppInfo] = [
        AppInfo(title: "WeChat", description: "WeChat is more than a messaging and social media app – it is a lifestyle for one billion users across the world", icon: "https://is1-ssl.mzstatic.com/image/thumb/Purple112/v4/ca/6c/ea/ca6cea6e-e2d0-ac20-2834-ad85956ad79f/source/60x60bb.jpg"),
        AppInfo(title: "WeChat", description: "WeChat is more than a messaging and social media app – it is a lifestyle for one billion users across the world", icon: "https://is1-ssl.mzstatic.com/image/thumb/Purple112/v4/ca/6c/ea/ca6cea6e-e2d0-ac20-2834-ad85956ad79f/source/60x60bb.jpg", isFavorite: true)
    ]
}

extension String {
    static let sampleImageURL: String = "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/16/1d/92/161d928a-fb4a-afe7-7cb7-e725e253dc7b/source/100x100bb.jpg"
}

#endif
