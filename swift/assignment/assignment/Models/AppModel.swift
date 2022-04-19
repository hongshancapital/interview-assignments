//
//  AppModel.swift
//  homework
//
//  Created by 干饭人肝不完DDL on 2022/4/11.
//

import Foundation

struct AppModel: Codable,Hashable {
    let screenshotUrls, ipadScreenshotUrls: [String?]
    let appletvScreenshotUrls: [String?]
    let artworkUrl60, artworkUrl512, artworkUrl100: String?
    let artistViewUrl: String?
    let features, supportedDevices: [String?]
    let advisories: [String?]
    let isGameCenterEnabled: Bool?
    let kind, minimumOsVersion: String?
    let trackCensoredName: String
    let languageCodesISO2A: [String?]
    let fileSizeBytes: String?
    let sellerUrl: String?
    let formattedPrice, contentAdvisoryRating: String?
    let averageUserRatingForCurrentVersion: Double?
    let userRatingCountForCurrentVersion: Int?
    let averageUserRating: Double?
    let trackViewUrl: String?
    let trackContentRating, bundleId: String?
    let primaryGenreId, trackId: Int?
    let trackName: String?
    let releaseDate: String?
    let primaryGenreName: String?
    let genreIds: [String?]
    let isVppDeviceBasedLicensingEnabled: Bool?
    let currentVersionReleaseDate: String?
    let sellerName, releaseNotes, currency: String?
    let description: String
    let artistId: Int?
    let artistName: String?
    let genres: [String?]
    let price: Double?
    let version, wrapperType: String?
    let userRatingCount: Int?
}


struct ResultsModel: Codable {
    let resultCount: Int
    let results: [AppModel]
}



/*
 
 {
     "screenshotUrls": [
         "https://is4-ssl.mzstatic.com/image/thumb/Purple115/v4/2e/ac/17/2eac1702-f302-b3ea-a380-8accfc2823e3/070e60c9-15ba-41fc-bc18-daffb5936c9d_iOS_5.5_01.png/392x696bb.png",
         "https://is3-ssl.mzstatic.com/image/thumb/Purple125/v4/25/13/e8/2513e884-4a6a-59ae-4608-7dc21a33d4e9/5b5a4931-ea9c-47f0-8c8c-a74f806ac201_iOS_5.5_02.png/392x696bb.png",
         "https://is2-ssl.mzstatic.com/image/thumb/PurpleSource115/v4/55/a0/a1/55a0a1ba-6f1d-a6b6-54a7-51a083b5d98f/92d383e6-75ab-43a8-9cc7-9c626d1d4055_iOS_5.5_03.png/392x696bb.png",
         "https://is2-ssl.mzstatic.com/image/thumb/PurpleSource115/v4/0a/c7/0b/0ac70b4d-1770-39be-c0cf-b5e10413ce58/12a77707-8290-472a-9c2b-4a6c667a21c8_iOS_5.5_04.png/392x696bb.png",
         "https://is4-ssl.mzstatic.com/image/thumb/PurpleSource125/v4/c9/e1/2f/c9e12f56-27d4-45a1-6ed1-3e9271ea62e2/d08fab42-dd63-4967-91a7-4c1c86bae509_iOS_5.5_05.png/392x696bb.png",
         "https://is5-ssl.mzstatic.com/image/thumb/PurpleSource115/v4/21/1d/95/211d95c4-673d-bcf6-4dfe-d2c03a85feb1/55e17e16-468f-4969-a43c-81ec7be27c76_iOS_5.5_06.png/392x696bb.png"
     ],
     "ipadScreenshotUrls": [
         "https://is1-ssl.mzstatic.com/image/thumb/Purple115/v4/8a/55/e2/8a55e24a-5edc-e0a1-8f75-63128964c2d2/43a6007f-d914-48c5-9ac3-fdede7f97986_01.png/576x768bb.png",
         "https://is2-ssl.mzstatic.com/image/thumb/Purple115/v4/e9/ed/80/e9ed80ac-eab1-7181-9a27-88cf44ca3f9e/49aec92f-40cc-4536-b02d-538a2c95b199_02.png/576x768bb.png",
         "https://is5-ssl.mzstatic.com/image/thumb/Purple125/v4/95/f6/78/95f67852-53c8-a423-c49c-f49effb1d246/0c6aa7f1-9972-4e0e-a867-faf6580ef842_03.png/576x768bb.png",
         "https://is2-ssl.mzstatic.com/image/thumb/Purple115/v4/3c/f4/17/3cf41757-7dcc-2ad7-0cdb-a902f164662b/339782fe-a7f0-47f3-b64b-b6980ffc211d_04.png/576x768bb.png",
         "https://is4-ssl.mzstatic.com/image/thumb/Purple125/v4/e9/2a/97/e92a9722-e87a-4f01-4e0c-b8eee368a156/eca9b524-0dbf-48bd-b1a1-6e84004087eb_05.png/576x768bb.png",
         "https://is4-ssl.mzstatic.com/image/thumb/Purple115/v4/63/87/17/638717fe-c02c-b5e5-3cae-5d885ba64786/daac442f-f5cf-4578-9ecf-838b540bd85d_06.png/576x768bb.png"
     ],
     "appletvScreenshotUrls": [],
     "artworkUrl60": "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/60x60bb.jpg",
     "artworkUrl512": "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/512x512bb.jpg",
     "artworkUrl100": "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/100x100bb.jpg",
     "artistViewUrl": "https://apps.apple.com/us/developer/google-llc/id281956209?uo=4",
     "features": ["iosUniversal"],
     "supportedDevices": ["iPhone5s-iPhone5s", "iPadAir-iPadAir", "iPadAirCellular-iPadAirCellular", "iPadMiniRetina-iPadMiniRetina", "iPadMiniRetinaCellular-iPadMiniRetinaCellular", "iPhone6-iPhone6", "iPhone6Plus-iPhone6Plus", "iPadAir2-iPadAir2", "iPadAir2Cellular-iPadAir2Cellular", "iPadMini3-iPadMini3", "iPadMini3Cellular-iPadMini3Cellular", "iPodTouchSixthGen-iPodTouchSixthGen", "iPhone6s-iPhone6s", "iPhone6sPlus-iPhone6sPlus", "iPadMini4-iPadMini4", "iPadMini4Cellular-iPadMini4Cellular", "iPadPro-iPadPro", "iPadProCellular-iPadProCellular", "iPadPro97-iPadPro97", "iPadPro97Cellular-iPadPro97Cellular", "iPhoneSE-iPhoneSE", "iPhone7-iPhone7", "iPhone7Plus-iPhone7Plus", "iPad611-iPad611", "iPad612-iPad612", "iPad71-iPad71", "iPad72-iPad72", "iPad73-iPad73", "iPad74-iPad74", "iPhone8-iPhone8", "iPhone8Plus-iPhone8Plus", "iPhoneX-iPhoneX", "iPad75-iPad75", "iPad76-iPad76", "iPhoneXS-iPhoneXS", "iPhoneXSMax-iPhoneXSMax", "iPhoneXR-iPhoneXR", "iPad812-iPad812", "iPad834-iPad834", "iPad856-iPad856", "iPad878-iPad878", "iPadMini5-iPadMini5", "iPadMini5Cellular-iPadMini5Cellular", "iPadAir3-iPadAir3", "iPadAir3Cellular-iPadAir3Cellular", "iPodTouchSeventhGen-iPodTouchSeventhGen", "iPhone11-iPhone11", "iPhone11Pro-iPhone11Pro", "iPadSeventhGen-iPadSeventhGen", "iPadSeventhGenCellular-iPadSeventhGenCellular", "iPhone11ProMax-iPhone11ProMax", "iPhoneSESecondGen-iPhoneSESecondGen", "iPadProSecondGen-iPadProSecondGen", "iPadProSecondGenCellular-iPadProSecondGenCellular", "iPadProFourthGen-iPadProFourthGen", "iPadProFourthGenCellular-iPadProFourthGenCellular", "iPhone12Mini-iPhone12Mini", "iPhone12-iPhone12", "iPhone12Pro-iPhone12Pro", "iPhone12ProMax-iPhone12ProMax", "iPadAir4-iPadAir4", "iPadAir4Cellular-iPadAir4Cellular", "iPadEighthGen-iPadEighthGen", "iPadEighthGenCellular-iPadEighthGenCellular", "iPadProThirdGen-iPadProThirdGen", "iPadProThirdGenCellular-iPadProThirdGenCellular", "iPadProFifthGen-iPadProFifthGen", "iPadProFifthGenCellular-iPadProFifthGenCellular", "iPhone13Pro-iPhone13Pro", "iPhone13ProMax-iPhone13ProMax", "iPhone13Mini-iPhone13Mini", "iPhone13-iPhone13", "iPadMiniSixthGen-iPadMiniSixthGen", "iPadMiniSixthGenCellular-iPadMiniSixthGenCellular", "iPadNinthGen-iPadNinthGen", "iPadNinthGenCellular-iPadNinthGenCellular"],
     "advisories": [],
     "isGameCenterEnabled": false,
     "kind": "software",
     "minimumOsVersion": "12.0",
     "trackCensoredName": "Google Chat",
     "languageCodesISO2A": ["AF", "AR", "EU", "BN", "BS", "BG", "CA", "HR", "CS", "DA", "NL", "EN", "ET", "FI", "FR", "GL", "DE", "EL", "GU", "HE", "HI", "HU", "IS", "ID", "IT", "JA", "KN", "KO", "LV", "LT", "MS", "ML", "MR", "NB", "FA", "PL", "PT", "RO", "RU", "SR", "ZH", "SK", "SL", "ES", "SW", "SV", "TA", "TE", "TH", "ZH", "TR", "UK", "UR", "VI", "ZU"],
     "fileSizeBytes": "235096064",
     "sellerUrl": "https://workspace.google.com/products/chat/",
     "formattedPrice": "Free",
     "contentAdvisoryRating": "4+",
     "averageUserRatingForCurrentVersion": 4.6136900000000000687805368215776979923248291015625,
     "userRatingCountForCurrentVersion": 40738,
     "averageUserRating": 4.6136900000000000687805368215776979923248291015625,
     "trackViewUrl": "https://apps.apple.com/us/app/google-chat/id1163852619?uo=4",
     "trackContentRating": "4+",
     "bundleId": "com.google.Dynamite",
     "primaryGenreId": 6000,
     "trackId": 1163852619,
     "trackName": "Google Chat",
     "releaseDate": "2018-02-28T13:37:21Z",
     "primaryGenreName": "Business",
     "genreIds": ["6000", "6007"],
     "isVppDeviceBasedLicensingEnabled": true,
     "currentVersionReleaseDate": "2022-02-07T17:36:14Z",
     "sellerName": "Google LLC",
     "releaseNotes": "Google Chat, part of Google Workspace, is now available to anyone with a Gmail address, for free! Use Chat to collaborate seamlessly either one-on-one, or in topic-based group conversations. Get things done and create content easily with Google Docs, Sheets, and Slides, and keep everyone up to date with shared files and tasks. Easily find past conversations or files with Google’s powerful search, and let bots and smart suggestions help you connect with meeting participants and get more done, faster.\n\nYou will now also see that Chat rooms are now called spaces",
     "currency": "USD",
     "description": "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export",
     "artistId": 281956209,
     "artistName": "Google LLC",
     "genres": ["Business", "Productivity"],
     "price": 0.00,
     "version": "1.0.142",
     "wrapperType": "software",
     "userRatingCount": 40738
 }
 
 */

func load<T:Decodable>(_ filename:String)->T {
    let data:Data
    
    guard let file = Bundle.main.url(forResource:filename,withExtension: nil)
    else {
        fatalError("Couldn't find \(filename) in main bundle.")
    }
    
    do {
        data = try Data(contentsOf :file)
    }catch{
        fatalError("Couldn't parse \(filename) from main bundle:\n\(error)")
    }
    
    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
    }
}
