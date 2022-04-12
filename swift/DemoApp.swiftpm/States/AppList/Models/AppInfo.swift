//
//  AppInfo.swift
//  
//
//  Created by 黄磊 on 2022/4/10.
//

import Foundation

struct AppInfo: Codable, Hashable {
    
    let advisories: [String]

    let appletvScreenshotUrls: [String]

    let artistId: Int64

    let artistName: String

    let artistViewUrl: String

    let artworkUrl60: String

    let artworkUrl100: String

    let artworkUrl512: String

    let averageUserRating: Float

    let averageUserRatingForCurrentVersion: Float

    let bundleId: String

    let contentAdvisoryRating: String // "4+"

    let currency: String

    let currentVersionReleaseDate: Date // "2022-04-04T15:51:50Z"

    let description: String

    let features: [String]

    let fileSizeBytes: String // "240609280"

    let formattedPrice: String

    let genreIds: [String]; // ["6000", "6007"] (2)

    let genres: [String]

    let ipadScreenshotUrls: [String]

    let isGameCenterEnabled: Bool

    let isVppDeviceBasedLicensingEnabled: Bool

    let kind: String

    let languageCodesISO2A: [String]

    let minimumOsVersion: String // "12.0"

    let price: Float

    let primaryGenreId: Int64

    let primaryGenreName: String

    let releaseDate: Date; // "2018-02-28T13:37:21Z"

    let releaseNotes: String

    let screenshotUrls: [String]

    let sellerName: String

    let sellerUrl: String?

    let supportedDevices: [String]

    let trackCensoredName: String

    let trackContentRating: String // "4+"

    let trackId: Int64

    let trackName: String

    let trackViewUrl: String

    let userRatingCount: Int

    let userRatingCountForCurrentVersion: Int

    let version: String // "1.0.150"

    let wrapperType: String
}

extension AppInfo {
    static var exampleOne: Self {
        MockAppList.shared.appList[0]
    }
    
    static var exampleFour: Self {
        MockAppList.shared.appList[3]
    }
}
