//
//  Model.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import Foundation

// MARK: - Welcome
struct Welcome: Codable {
    let resultCount: Int
    let results: [Result]
}

// MARK: - Result
struct Result: Codable,Identifiable {
    let id = UUID()
    
    let screenshotUrls, ipadScreenshotUrls: [String]
    let appletvScreenshotUrls: [String]
    let artworkUrl60, artworkUrl512, artworkUrl100: String
    let artistViewURL: String
    let features, supportedDevices: [String]
    let advisories: [String]
    let isGameCenterEnabled: Bool
    let kind, minimumOSVersion, trackCensoredName: String
    let languageCodesISO2A: [String]
    let fileSizeBytes: String
    let sellerURL: String?
    let formattedPrice, contentAdvisoryRating: String
    let averageUserRatingForCurrentVersion: Double
    let userRatingCountForCurrentVersion: Int
    let averageUserRating: Double
    let trackViewURL: String
    let trackContentRating, bundleID: String
    let genreIDS: [String]
    let releaseDate: String
    let trackID: Int
    let trackName, primaryGenreName: String
    let isVppDeviceBasedLicensingEnabled: Bool
    let sellerName: String
    let currentVersionReleaseDate: String
    let releaseNotes: String
    let primaryGenreID: Int
    let currency: String
    let artistID: Int
    let artistName: String
    let genres: [String]
    let price: Int
    let resultDescription, version, wrapperType: String
    let userRatingCount: Int

    enum CodingKeys: String, CodingKey {
        case screenshotUrls, ipadScreenshotUrls, appletvScreenshotUrls, artworkUrl60, artworkUrl512, artworkUrl100
        case artistViewURL = "artistViewUrl"
        case features, supportedDevices, advisories, isGameCenterEnabled, kind
        case minimumOSVersion = "minimumOsVersion"
        case trackCensoredName, languageCodesISO2A, fileSizeBytes
        case sellerURL = "sellerUrl"
        case formattedPrice, contentAdvisoryRating, averageUserRatingForCurrentVersion, userRatingCountForCurrentVersion, averageUserRating
        case trackViewURL = "trackViewUrl"
        case trackContentRating
        case bundleID = "bundleId"
        case genreIDS = "genreIds"
        case releaseDate
        case trackID = "trackId"
        case trackName, primaryGenreName, isVppDeviceBasedLicensingEnabled, sellerName, currentVersionReleaseDate, releaseNotes
        case primaryGenreID = "primaryGenreId"
        case currency
        case artistID = "artistId"
        case artistName, genres, price
        case resultDescription = "description"
        case version, wrapperType, userRatingCount
    }
}
