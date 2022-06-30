//
//  AppModel.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/28.
//

import Foundation
import SwiftUI

struct AppModelList: Decodable{
	var resultCount: Int = 0
	var results: [AppModel] = []
}

//  
class AppModel: Decodable, Identifiable, ObservableObject{
	var id: Int = 0
	/** 应用名称 */
	var appName: String = "app name"
	/** 开发者名称 */
	var appAgent: String = "app agent"
	/** 应用地址 */
	var appUrl: String = "https://appUrl"
	/** 100x100 appIcon */
	var appIcon: String = "https://appIconUrl"
	/** 应用描述 */
	var appDes: String = "app description"
	
	/** 是否标记喜爱 */
	var like: Bool = false
	
	private enum CodingKeys: String, CodingKey{
		case id = "trackId"
		case appName = "trackName"
		case appAgent = "sellerName"
		case appUrl = "trackViewUrl"
		case appIcon = "artworkUrl100"
		case appDes = "description"
	}
	
	static func likeIconName(_ like: Bool = false) -> String{
		return like ? "heart.fill" : "heart"
	}
	static func likeIconColor(_ like: Bool = false) -> Color{
		return like ? Color("appLikeColor.fill") : Color(uiColor: .lightGray)
	}
	
	static func likeIconScale(_ like: Bool = false) -> CGFloat{
		return like ? 1.2 : 1
	}
}
