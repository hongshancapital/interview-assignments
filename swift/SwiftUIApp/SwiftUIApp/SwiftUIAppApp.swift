//
//  SwiftUIAppApp.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/28.
//

import SwiftUI

@main
struct SwiftUIAppApp: App {
	private var servier = AppService()
    var body: some Scene {
        WindowGroup {
            ContentView()
				.onAppear{
					Task{
						 try await servier.fetchAppList(pageNumber: 1, pageSize: 20)
					}
				}
        }
    }
}
