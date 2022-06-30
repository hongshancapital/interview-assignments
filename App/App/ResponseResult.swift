//
//  ResponseResult.swift
//  App
//
//  Created by xiongjin on 2022/6/29.
//

import Foundation

struct ResponseResult: Codable, Identifiable {
    var id = UUID().uuidString
    
    let appletvScreenshotUrls: [String]
    let screenshotUrls: [String]
    let artworkUrl60: String
    let artworkUrl100: String
    let artistViewUrl: String
    let ipadScreenshotUrls: [String]
    let artworkUrl512: String
    let features: [String]
    let supportedDevices: [String]
    let advisories: [String]
    let isGameCenterEnabled: Bool
    let kind: String
    let minimumOsVersion: String
    let languageCodesISO2A: [String]
    let fileSizeBytes: String
    let sellerUrl: String?
    let formattedPrice: String
    let averageUserRatingForCurrentVersion: Double
    let userRatingCountForCurrentVersion: Int
    let trackContentRating: String
    let averageUserRating: Double
    let trackCensoredName: String
    let trackViewUrl: String
    let contentAdvisoryRating: String
    let bundleId: String
    let genreIds: [String]
    let releaseDate: String
    let trackId: Int
    let trackName: String
    let primaryGenreName: String
    let isVppDeviceBasedLicensingEnabled: Bool
    let sellerName: String
    let currentVersionReleaseDate: String
    let releaseNotes: String
    let primaryGenreId: Int
    let currency: String
    let artistId: Int
    let artistName: String
    let genres: [String]
    let price: Double
    let description: String
    let version: String
    let wrapperType: String
    let userRatingCount: Int
    
    enum CodingKeys: String, CodingKey {
        
        case appletvScreenshotUrls
        case screenshotUrls
        case artworkUrl60
        case artworkUrl100
        case artistViewUrl
        case ipadScreenshotUrls
        case artworkUrl512
        case features
        case supportedDevices
        case advisories
        case isGameCenterEnabled
        case kind
        case minimumOsVersion
        case languageCodesISO2A
        case fileSizeBytes
        case sellerUrl
        case formattedPrice
        case averageUserRatingForCurrentVersion
        case userRatingCountForCurrentVersion
        case trackContentRating
        case averageUserRating
        case trackCensoredName
        case trackViewUrl
        case contentAdvisoryRating
        case bundleId
        case genreIds
        case releaseDate
        case trackId
        case trackName
        case primaryGenreName
        case isVppDeviceBasedLicensingEnabled
        case sellerName
        case currentVersionReleaseDate
        case releaseNotes
        case primaryGenreId
        case currency
        case artistId
        case artistName
        case genres
        case price
        case description
        case version
        case wrapperType
        case userRatingCount
    }
}
