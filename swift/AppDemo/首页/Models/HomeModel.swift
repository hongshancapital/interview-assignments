//
//  HomeModel.swift
//  AppDemo
//
//  Created by 操喜平 on 2022/8/10.
//

import UIKit


@objcMembers
class HomeModel: NSObject {
        var screenshotUrls: Array<Any>?
        var ipadScreenshotUrls: Array<Any>?
        var appletvScreenshotUrls: Array<Any>?
        var artworkUrl60: String?
        var artworkUrl512: String?
        var artworkUrl100: String?
        var artistViewUrl: String?
        var isGameCenterEnabled: Bool?
        var advisories: Array<Any>?
        var features: Array<Any>?
        var supportedDevices: Array<Any>?
        var kind: String?
        var sellerUrl: String?
        var formattedPrice: String?
        var contentAdvisoryRating: String?
        var averageUserRatingForCurrentVersion: CGFloat?
        var userRatingCountForCurrentVersion: Int?
        var averageUserRating: CGFloat?
        var trackViewUrl: String?
        var trackContentRating: String?
        var minimumOsVersion: String?
        var trackCensoredName: String?
        var languageCodesISO2A: Array<Any>?
        var fileSizeBytes: String?
        var currentVersionReleaseDate: String?
        var releaseNotes: String?
        var artistId: Int?
        var artistName: String?
        var genres: Array<Any>?
        var price: Int?
        var primaryGenreName: String?
        var primaryGenreId: Int?
        var descriptionStr: String?
        var genreIds: Array<String>?
        var isVppDeviceBasedLicensingEnabled: Bool?
        var bundleId: String?
        var releaseDate: String?
        var sellerName: String?
        var trackId: Int?
        var trackName: String?
        var currency: String?
        var version: String?
        var wrapperType: String?
        var userRatingCount: Int?
        var selected: Bool = false
    
    
    init(dict:[String:AnyObject]){
        super.init()
        setValuesForKeys(dict)
    }
    
    override  func setValue(_ value: Any?, forUndefinedKey key: String) {
        if key == "description" {
            self.setValue(value, forKey: "descriptionStr")
        }
    }
}
