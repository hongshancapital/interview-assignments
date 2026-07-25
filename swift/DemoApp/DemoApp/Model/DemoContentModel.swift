//
//  DemoContentModel.swift
//  DemoApp
//
//  Created by Meteor 丶Shower on 2022/5/18.
//

import Foundation
import SwiftUI

struct ContentModel: Codable {
    var resultCount: Int
    
    var results: [ContentResult]
}

struct ContentResult: Codable, Identifiable {
    var id: Int = 0
    var selected: Bool = false
    var artworkUrl60: String!
    var artworkUrl512: String!
    var artworkUrl100: String!
    var artistViewUrl: String!
    var screenshotUrls: [String] = [""]
    var ipadScreenshotUrls: [String] = [""]
    var appletvScreenshotUrls: [String] = [""]
    var features: [String] = [""]
    var supportedDevices: [String] = [""]
    var advisories: [String] = [""]
    var isGameCenterEnabled: Bool = false
    var kind: String!
    var trackViewUrl: String!
    var minimumOsVersion: String!
    var trackCensoredName: String!
    var languageCodesISO2A: [String] = [""]
    var fileSizeBytes: String!
    var sellerUrl: String!
    var formattedPrice: String!
    var contentAdvisoryRating: String!
    var averageUserRatingForCurrentVersion: CGFloat = 0.0
    var userRatingCountForCurrentVersion: Int = 0
    var averageUserRating: CGFloat = 0.0
    var trackContentRating: String!
    var releaseDate: String!
    var bundleId: String!
    var genreIds: [String] = [""]
    var trackId: Int = 0
    var trackName: String!
    var primaryGenreName: String!
    var isVppDeviceBasedLicensingEnabled: Bool = false
    var primaryGenreId: Int = 0
    var sellerName: String!
    var currentVersionReleaseDate: String!
    var releaseNotes: String!
    var currency: String!
    var description: String!
    var artistId: Int = 0
    var artistName: String!
    var genres: [String] = [""]
    var price: Int = 0
    var version: String!
    var wrapperType: String!
    var userRatingCount: Int = 0
}

