//
//  Application.swift
//  scdt-chn
//
//  Created by Zhao Sam on 2022/8/5.
//

import Foundation

let APPLICATIONKEY = "USERDEFAULT_KEY_APPLICATION"
let APPLICATIONKEYIDF = "KEY_APP_ID_%d"

struct Application: Hashable, Identifiable {
    public var id: Int
    public var name: String?
    public var iconUrl: String?
    public var description: String?
    public var isFavorite: Bool = false
    private let userDefaults = UserDefaults.standard
    
    init(id: Int, name: String, iconUrl: String, description: String) {
        self.id = id
        self.name = name
        self.iconUrl = iconUrl
        self.description = description
        
        let dict = userDefaults.dictionary(forKey: APPLICATIONKEY) as? [String:Bool]
        let formatKey = String(format: APPLICATIONKEYIDF, id)
        if let dict = dict, let fav = dict[formatKey] {
            self.isFavorite = fav
        }
    }
    
    init(from applicationModel: ApplicationModel) {
        self.init(id: applicationModel.trackId, name: applicationModel.trackCensoredName, iconUrl: applicationModel.artworkUrl60, description: applicationModel.description)
    }
}
