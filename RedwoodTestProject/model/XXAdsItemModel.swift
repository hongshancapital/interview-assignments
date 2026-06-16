//
//  XXAdsItemModel.swift
//  RedwoodTestProject
//
//  Created by 徐栋 on 2023/2/8.
//

import Foundation
struct XXAdsItemModel: Identifiable {
    var id = UUID()
    var artworkUrl: String = ""
    var description: String = ""
    var trackName: String = ""
// 未使用数据
//    var features: [String] = []
//    var advisories: [String] = []
//    var supportedDevices: [String] = []
//    var isGameCenterEnabled: Bool = false
//    var screenshotUrls: [String] = []
//    var ipadScreenshotUrls: [String] = []
//    var appvarvScreenshotUrls: [String] = []
//    var artistViewUrl: String = ""
//    var kind: String = ""
//    var releaseNotes: String = ""
//    var artistId: Int = 0
//    var artistName: String = ""
//    var genres: [String] = []
//    var price: Double = 0
//    var bundleId: String = ""
//    var releaseDate: String = ""
//    var genreIds: [String] = []
//    var currentVersionReleaseDate: String = ""
//    var primaryGenreName: String = ""
//    var primaryGenreId: Int = 0
//    var isVppDeviceBasedLicensingEnabled: Bool = true
//    var trackId: String = ""
//    var sellerName: String = ""
//    var currency: String = ""
//    var minimumOsVersion: String = ""
//    var trackCensoredName: String = ""
//    var languageCodesISO2A: [String] = []
//    var fileSizeBytes: String = ""
//    var sellerUrl: String = ""
//    var formattedPrice: String = ""
//    var contentAdvisoryRating: String = ""
//    var averageUserRatingForCurrentVersion: String = ""
//    var userRatingCountForCurrentVersion: String = ""
//    var trackViewUrl: String = ""
//    var trackContentRating: String = ""
//    var version: String = ""
//    var wrapperType: String = ""
//    var userRatingCount: Int = 0
}
extension XXAdsItemModel: Codable {
    enum XXAdsItemModelKeys: String, CodingKey {
        case artworkUrl = "artworkUrl100"
    }
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        description = try container.decode(String.self, forKey: .description)
        trackName = try container.decode(String.self, forKey: .trackName)
        let additionalContainer = try decoder.container(keyedBy: XXAdsItemModelKeys.self)
        artworkUrl = try additionalContainer.decode(String.self, forKey: .artworkUrl)
      }
}
