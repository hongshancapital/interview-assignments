//
//  AppInfoModel.swift
//  SwiftHomework
//
//  Created by ljx on 2022/8/14.
//
// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let chatApp = try? newJSONDecoder().decode(ChatApp.self, from: jsonData)

import Foundation

// MARK: - ChatApp
struct AppInfo: Codable,Hashable {
    let resultCount: Int
    let results: [AppDetail]
}

typealias AppList = [AppDetail]

// MARK: - Result
struct AppDetail: Codable,Hashable {
    let supportedDevices: [String]
    let features: [String]
    let screenshotUrls, ipadScreenshotUrls: [String]
    let artworkUrl60, artworkUrl512, artworkUrl100: String
    let artistViewURL: String
    let kind, releaseNotes, resultDescription, bundleID: String
    let genreIDS: [String]
    let sellerName,trackContentRating: String
    let minimumOSVersion, trackCensoredName: String
    let languageCodesISO2A: [String]
    let fileSizeBytes: String
    let formattedPrice, contentAdvisoryRating: String
    let userRatingCountForCurrentVersion: Int
    let trackViewURL: String
    let currency, primaryGenreName: String
    let primaryGenreID: Int
    let artistName: String
    let genres: [String]
    let price, artistID, trackID: Int
    let trackName, version, wrapperType: String
    let userRatingCount: Int

    enum CodingKeys: String, CodingKey {
        case supportedDevices, features, screenshotUrls, ipadScreenshotUrls, artworkUrl60, artworkUrl512, artworkUrl100
        case artistViewURL = "artistViewUrl"
        case kind, releaseNotes
        case resultDescription = "description"
        case bundleID = "bundleId"
        case genreIDS = "genreIds"
        case sellerName, trackContentRating
        case minimumOSVersion = "minimumOsVersion"
        case trackCensoredName
        case languageCodesISO2A, fileSizeBytes
        case formattedPrice, contentAdvisoryRating, userRatingCountForCurrentVersion
        case trackViewURL = "trackViewUrl"
        case currency, primaryGenreName
        case primaryGenreID = "primaryGenreId"
        case artistName, genres, price
        case artistID = "artistId"
        case trackID = "trackId"
        case trackName, version, wrapperType, userRatingCount
    }
}




