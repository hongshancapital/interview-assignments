//
//  AppViewModel.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/29.
//

import Foundation

final class AppViewModel: ObservableObject{
	@Published var appList: [AppModel] = []
	@Published var hasMoreData: Bool = true
	@Published var isFecthing = false
	
	private(set) var pageNumber: Int = 1
	private(set) var pageSize: Int = 20
	private(set) var maxDataCount = 60
	
	private var fetcher = AppFetcher()
	private var updater = AppLikeUpdater()
	
	@MainActor
	func getAppList() async throws{
		if isFecthing{
			return
		}
		pageNumber = 1
		isFecthing = true
		let result = try await fetcher.fetchAppList(pageNumber: pageNumber, pageSize: pageSize).results
			.map({ app -> AppModel in
				let newApp = app
				newApp.like = updater.appIsLike(app.id)
				return newApp
			})
		appList = result
		hasMoreData = appList.count < maxDataCount
		isFecthing = false
	}
	
	@MainActor
	func getMoreAppList() async throws{
		if isFecthing || !hasMoreData{
			return
		}
		isFecthing = true
		pageNumber += 1
		let result = try await fetcher.fetchAppList(pageNumber: pageNumber, pageSize: pageSize).results
			.suffix(from: appList.count)
			.map({ app -> AppModel in
				let newApp = app
				newApp.like = updater.appIsLike(app.id)
				return newApp
			})
		let listToAppend = result
		appList.append(contentsOf: listToAppend)
		hasMoreData = appList.count < maxDataCount
		isFecthing = false
	}
	
	
	/** 更新喜爱状态 */
	public func updateAppLike(_ id: Int, isLike: Bool) async{
		await updater.update(isLike, appId: id)
		appList = appList.map { app -> AppModel in
			if app.id == id{
				let newApp = app
				newApp.like = isLike
				return newApp
			}
			return app
		}
		
	}
	
	/** 查询是否喜爱过 */
	public func appIsLike(_ id: Int) -> Bool{
		return updater.appIsLike(id)
	}
}
