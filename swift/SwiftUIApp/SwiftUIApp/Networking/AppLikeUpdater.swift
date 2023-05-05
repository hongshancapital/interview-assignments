//
//  AppLikeUpdater.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/30.
//

import Foundation

struct AppLikeUpdater: HZApiService{
	/** key: app.id, value: app.like */
	private(set) var appLikeCollection: [String: Bool] = [:]
	private let kAppLikeStoreKey = "hzAppLikeStoreKey"
	
	init(){
		loadStoreData()
	}
	
	mutating func update(_ isLike: Bool, appId: Int) async {
		await updateAppLike(appId, isLike: isLike)
	}
	
	/** 更新喜爱状态 */
	private mutating func updateAppLike(_ id: Int, isLike: Bool) async{
		appLikeCollection["\(id)"] = isLike
		saveData()
	}
	
	/** 查询是否喜爱过 */
	public func appIsLike(_ id: Int) -> Bool{
		return appLikeCollection["\(id)"] ?? false
	}
	
	private mutating func loadStoreData() {
		guard let storeData = UserDefaults.standard.value(forKey: kAppLikeStoreKey) as? [String: Bool] else{
			return
		}
		appLikeCollection = storeData
	}
	
	private func saveData() {
		UserDefaults.standard.setValue(appLikeCollection, forKey: kAppLikeStoreKey)
	}
}
