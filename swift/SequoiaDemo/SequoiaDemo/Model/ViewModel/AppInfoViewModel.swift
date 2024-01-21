//
//  AppInfoViewModel.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/5/3.
//

import Foundation

/*
 展示每行App信息的ViewModel
 */
class AppInfoViewModel: Identifiable, Hashable{
    
    init(with appInfo: AppInfo) {
        self.appId = "\(appInfo.trackId)"
        self.appIconUrl = appInfo.artworkUrl512
        self.appName = appInfo.trackName
        self.description = appInfo.description
        self.isLiked = false
        updateLikeState()
    }
    
    func toggleLikeState() {
        self.isLiked = !self.isLiked
        saveLikeState()
    }
    
    /*
     Hashable
     */
    var id: String {
        get {
            return self.appId
        }
    }
    
    let appId: String
    let appName: String
    let appIconUrl: String
    let description: String
    var isLiked: Bool
    
    /*
     Hashable
     */
    func hash(into hasher: inout Hasher) {
        hasher.combine(appId)
    }
    
    static func == (lhs: AppInfoViewModel, rhs: AppInfoViewModel) -> Bool {
        lhs.appId == rhs.appId
    }
    
    
    /*
     Helpers
     */
    
    let storageKeyPrefix = "AppInfoViewModel.likeState"
    
    private func updateLikeState() {
        guard !self.appId.isEmpty else {
            return
        }
        let key = "\(storageKeyPrefix).\(appId)"
        if PersistenHelper.instance.loadBool(with: key) == true {
            isLiked = true
        }
    }
    
    private func saveLikeState() {
        guard !self.appId.isEmpty else {
            return
        }
        let key = "\(storageKeyPrefix).\(appId)"
        PersistenHelper.instance.save(val: self.isLiked, key: key)
        
    }
}
