//
//  HZApi.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/29.
//

import Foundation

enum HZApi{
	static let baseUrlString = "https://itunes.apple.com/search"
	
	// entity=software&limit=50&term=chat
	case getAppList(pageNumber: Int, pageSize: Int)
	
	var parameters: [String: Any]{
		switch self {
		case .getAppList(let pageNumber, let pageSize):
			return [
					"entity": "software",
					"term": "chat",
					"limit": pageNumber * pageSize
					]
		}
	}
}
