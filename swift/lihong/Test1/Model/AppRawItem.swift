//
//  AppRawItem.swift
//  Test1
//
//  Created by Hong Li on 2022/10/19.
//

import Foundation

///
/// Struct to map JSON response
///
struct AppRawItem: Codable {
    var isGameCenterEnabled: Bool
    var advisories: [String]
    var supportedDevices: [String]
    var features: [String]
    var screenshotUrls: [String]
    var ipadScreenshotUrls: [String]
    var appletvScreenshotUrls: [String]
    var artworkUrl60: String
    var artworkUrl512: String
    var artworkUrl100: String
    var artistViewUrl: String
    var kind: String
    var currency: String
    var description: String
    var genreIds: [String]
    var releaseDate: String
    var isVppDeviceBasedLicensingEnabled: Bool
    var sellerName: String
    var trackId: Int64
    var trackName: String
    var bundleId: String
    var primaryGenreName: String
    var primaryGenreId: Int
    var currentVersionReleaseDate: String
    var releaseNotes: String
    var minimumOsVersion: String
    var trackCensoredName: String
    var languageCodesISO2A: [String]
    var fileSizeBytes: String
    var sellerUrl: String?  // There are only 44 of 50 instances in sample response, this property could be nil
    var formattedPrice: String
    var contentAdvisoryRating: String
    var averageUserRatingForCurrentVersion: Double
    var userRatingCountForCurrentVersion: Int64
    var averageUserRating: Double
    var trackViewUrl: String
    var trackContentRating: String
    var version: String
    var wrapperType: String
    var price: Double
    var artistId: Int64
    var artistName: String
    var genres: [String]
    var userRatingCount: Int64
}
