// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse the JSON, add this file to your project and do:
//
//   let appListModel = try? newJSONDecoder().decode(AppListModel.self, from: jsonData)

import Foundation

// MARK: - AppListModel
struct AppListModel: Codable {
    let screenshotUrls, ipadScreenshotUrls: [String]
    let appletvScreenshotUrls: [String]
    let artworkUrl60, artworkUrl512, artworkUrl100: String
    let artistViewURL: String
    let features, supportedDevices: [String]
    let advisories: [String]
    let isGameCenterEnabled: Bool
    let kind: String
    let averageUserRating: Double
    let minimumOSVersion, trackCensoredName: String
    let languageCodesISO2A: [String]
    let fileSizeBytes: String
//    let sellerURL: String
    let formattedPrice, contentAdvisoryRating: String
    let averageUserRatingForCurrentVersion: Double
    let userRatingCountForCurrentVersion: Int
    let trackViewURL: String
    let trackContentRating, releaseNotes, bundleID, currency: String
    let releaseDate: String
    let genreIDS: [String]
    let primaryGenreName: String
    let primaryGenreID, trackID: Int
    let trackName: String
    let isVppDeviceBasedLicensingEnabled: Bool
    let sellerName: String
    let currentVersionReleaseDate: String
    let version, wrapperType, appListModelDescription: String
    let artistID: Int
    let artistName: String
    let genres: [String]
    let price, userRatingCount: Int

    enum CodingKeys: String, CodingKey {
        case screenshotUrls, ipadScreenshotUrls, appletvScreenshotUrls, artworkUrl60, artworkUrl512, artworkUrl100
        case artistViewURL = "artistViewUrl"
        case features, supportedDevices, advisories, isGameCenterEnabled, kind, averageUserRating
        case minimumOSVersion = "minimumOsVersion"
        case trackCensoredName, languageCodesISO2A, fileSizeBytes
//        case sellerURL = "sellerUrl"
        case formattedPrice, contentAdvisoryRating, averageUserRatingForCurrentVersion, userRatingCountForCurrentVersion
        case trackViewURL = "trackViewUrl"
        case trackContentRating, releaseNotes
        case bundleID = "bundleId"
        case currency, releaseDate
        case genreIDS = "genreIds"
        case primaryGenreName
        case primaryGenreID = "primaryGenreId"
        case trackID = "trackId"
        case trackName, isVppDeviceBasedLicensingEnabled, sellerName, currentVersionReleaseDate, version, wrapperType
        case appListModelDescription = "description"
        case artistID = "artistId"
        case artistName, genres, price, userRatingCount
    }
}

extension AppListModel: Identifiable {
    var id: String {
        bundleID
    }
}
