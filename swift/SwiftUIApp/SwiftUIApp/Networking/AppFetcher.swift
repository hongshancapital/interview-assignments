//
//  AppFetcher.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/30.
//

import Foundation


struct AppFetcher: HZApiService{
	func fetchAppList(pageNumber: Int, pageSize: Int) async throws -> AppModelList{
		let response = try await get(.getAppList(pageNumber: pageNumber, pageSize: pageSize), type: AppModelList.self)
		
		return response
	}
}
