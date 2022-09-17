//
//  ApplyListModel.swift
//  interview-assignments
//
//  Created by Pedro Pei on 2022/5/23.
//

import Foundation

struct ApplyListModel: Codable, Hashable {
    let resultCount: Int
    let results: [Results]
    
    struct Results: Codable, Hashable {
        
        let screenshotUrls: [String]
        let ipadScreenshotUrls: [String]
        let appletvScreenshotUrls: [String?]
        let artworkUrl60: String
        let artworkUrl512: String
        let artworkUrl100: String
        let artistViewUrl: String
        let features: [String]
        let supportedDevices: [String]
        let advisories: [String]
        let isGameCenterEnabled: Bool
        let kind: String
        let averageUserRating: Double
        let trackViewUrl: String
        let trackContentRating: String
        let minimumOsVersion: String
        let trackCensoredName: String
        let languageCodesISO2A: [String]
        let fileSizeBytes: String
        let sellerUrl: String?
        let formattedPrice: String
        let contentAdvisoryRating: String
        let averageUserRatingForCurrentVersion: Double
        let userRatingCountForCurrentVersion: Int
        let bundleId: String
        let trackId: Int
        let trackName: String
        let releaseDate: String
        let genreIds: [String]
        let primaryGenreName: String
        let isVppDeviceBasedLicensingEnabled: Bool
        let sellerName: String
        let currentVersionReleaseDate: String
        let releaseNotes: String
        let primaryGenreId: Int
        let currency: String
        let description: String
        let artistId: Int
        let artistName: String
        let genres: [String]
        let price: Double
        let version: String
        let wrapperType: String
        let userRatingCount: Int
    }
}
