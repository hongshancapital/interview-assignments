//
//  HZApiService.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/29.
//

import Foundation

enum HZApiReqError: Error{
	case invalidBaseUrl
	case invalidParameter
	case responseError
	case responseStatusError(statusCode: Int)
	case responseDataDecodeError
}

protocol HZApiService{}

extension HZApiService{
	func get<T: Decodable>(_ api: HZApi, type: T.Type) async throws -> T{
		let query = api.parameters.sorted(by: { $0.key < $1.key })
			.map({ "\($0.key)=\($0.value)" })
			.joined(separator: "&")
		let requestUrl = HZApi.baseUrlString + (query.count > 0 ? ("?" + query) : "")
		guard let url = URL(string: requestUrl) else{
			throw HZApiReqError.invalidBaseUrl
		}
		
		var request = URLRequest(url: url)
		request.httpMethod = "GET"
		request.setValue("application/json", forHTTPHeaderField: "Content-Type")
		let (data, response) = try await URLSession.shared.data(for: request)
		guard let httpResponse = response as? HTTPURLResponse else{
			throw HZApiReqError.responseError
		}
		
		guard (200..<300).contains(httpResponse.statusCode) else{
			throw HZApiReqError.responseStatusError(statusCode: httpResponse.statusCode)
		}
		
		guard let response = try? JSONDecoder().decode(type, from: data) else{
			throw HZApiReqError.responseDataDecodeError
		}
		
		return response
	}
}
