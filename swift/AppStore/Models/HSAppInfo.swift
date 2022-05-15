//
//  HSAppInfo.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import Foundation

@objcMembers class HSAppInfo: NSObject, ObservableObject {
    var artworkUrl60: String = ""
    var trackID: Int = 0
    var trackName: String = ""
    var infoDescription: String = ""
    @Published var favorate: Bool = false

    static func modelCustomPropertyMapper() -> [String: Any]? {
        return ["infoDescription": "description"]
    }
}
